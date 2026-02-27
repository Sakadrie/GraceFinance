package com.gracefinance.gracefinanceapp.web.rest.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.CaisseAsserts.*;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefinance.gracefinanceapp.IntegrationTest;
import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.principal.CaisseRepository;
import com.gracefinance.gracefinanceapp.repository.principal.EntiteFinanciereRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.CaisseDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.CaisseMapper;
import com.gracefinance.gracefinanceapp.web.rest.principal.CaisseResource;
import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link CaisseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
public class CaisseResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DEVISE = "AAAAAAAAAA";
    private static final String UPDATED_DEVISE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SOLDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SOLDE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SOLDE = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    private static final String ENTITY_API_URL = "/api/caisses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CaisseRepository caisseRepository;

    @Autowired
    private CaisseMapper caisseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Caisse caisse;

    private Caisse insertedCaisse;

    @Autowired
    private EntiteFinanciereRepository entiteFinanciereRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caisse createEntity(EntityManager em) {
        Caisse caisse = new Caisse()
            .nom(DEFAULT_NOM)
            .code(DEFAULT_CODE)
            .type(DEFAULT_TYPE)
            .devise(DEFAULT_DEVISE)
            .solde(DEFAULT_SOLDE)
            .actif(DEFAULT_ACTIF);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createEntity()).block();
        caisse.setEntiteFinanciere(entiteFinanciere);
        return caisse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caisse createUpdatedEntity(EntityManager em) {
        Caisse updatedCaisse = new Caisse()
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .devise(UPDATED_DEVISE)
            .solde(UPDATED_SOLDE)
            .actif(UPDATED_ACTIF);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createUpdatedEntity()).block();
        updatedCaisse.setEntiteFinanciere(entiteFinanciere);
        return updatedCaisse;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Caisse.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        EntiteFinanciereResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        caisse = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedCaisse != null) {
            caisseRepository.delete(insertedCaisse).block();
            insertedCaisse = null;
        }
        deleteEntities(em);
    }

    @Test
    void createCaisse() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Caisse
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);
        var returnedCaisseDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(CaisseDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Caisse in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCaisse = caisseMapper.toEntity(returnedCaisseDTO);
        assertCaisseUpdatableFieldsEquals(returnedCaisse, getPersistedCaisse(returnedCaisse));

        insertedCaisse = returnedCaisse;
    }

    @Test
    void createCaisseWithExistingId() throws Exception {
        // Create the Caisse with an existing ID
        caisse.setId(1L);
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Caisse in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        caisse.setNom(null);

        // Create the Caisse, which fails.
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        caisse.setCode(null);

        // Create the Caisse, which fails.
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        caisse.setType(null);

        // Create the Caisse, which fails.
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDeviseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        caisse.setDevise(null);

        // Create the Caisse, which fails.
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkSoldeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        caisse.setSolde(null);

        // Create the Caisse, which fails.
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkActifIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        caisse.setActif(null);

        // Create the Caisse, which fails.
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCaisses() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(caisse.getId().intValue()))
            .jsonPath("$.[*].nom")
            .value(hasItem(DEFAULT_NOM))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE))
            .jsonPath("$.[*].devise")
            .value(hasItem(DEFAULT_DEVISE))
            .jsonPath("$.[*].solde")
            .value(hasItem(sameNumber(DEFAULT_SOLDE)))
            .jsonPath("$.[*].actif")
            .value(hasItem(DEFAULT_ACTIF));
    }

    @Test
    void getCaisse() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get the caisse
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, caisse.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(caisse.getId().intValue()))
            .jsonPath("$.nom")
            .value(is(DEFAULT_NOM))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE))
            .jsonPath("$.devise")
            .value(is(DEFAULT_DEVISE))
            .jsonPath("$.solde")
            .value(is(sameNumber(DEFAULT_SOLDE)))
            .jsonPath("$.actif")
            .value(is(DEFAULT_ACTIF));
    }

    @Test
    void getCaissesByIdFiltering() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        Long id = caisse.getId();

        defaultCaisseFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCaisseFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCaisseFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllCaissesByNomIsEqualToSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where nom equals to
        defaultCaisseFiltering("nom.equals=" + DEFAULT_NOM, "nom.equals=" + UPDATED_NOM);
    }

    @Test
    void getAllCaissesByNomIsInShouldWork() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where nom in
        defaultCaisseFiltering("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM, "nom.in=" + UPDATED_NOM);
    }

    @Test
    void getAllCaissesByNomIsNullOrNotNull() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where nom is not null
        defaultCaisseFiltering("nom.specified=true", "nom.specified=false");
    }

    @Test
    void getAllCaissesByNomContainsSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where nom contains
        defaultCaisseFiltering("nom.contains=" + DEFAULT_NOM, "nom.contains=" + UPDATED_NOM);
    }

    @Test
    void getAllCaissesByNomNotContainsSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where nom does not contain
        defaultCaisseFiltering("nom.doesNotContain=" + UPDATED_NOM, "nom.doesNotContain=" + DEFAULT_NOM);
    }

    @Test
    void getAllCaissesByCodeIsEqualToSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where code equals to
        defaultCaisseFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    void getAllCaissesByCodeIsInShouldWork() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where code in
        defaultCaisseFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    void getAllCaissesByCodeIsNullOrNotNull() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where code is not null
        defaultCaisseFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    void getAllCaissesByCodeContainsSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where code contains
        defaultCaisseFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    void getAllCaissesByCodeNotContainsSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where code does not contain
        defaultCaisseFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    void getAllCaissesByTypeIsEqualToSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where type equals to
        defaultCaisseFiltering("type.equals=" + DEFAULT_TYPE, "type.equals=" + UPDATED_TYPE);
    }

    @Test
    void getAllCaissesByTypeIsInShouldWork() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where type in
        defaultCaisseFiltering("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE, "type.in=" + UPDATED_TYPE);
    }

    @Test
    void getAllCaissesByTypeIsNullOrNotNull() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where type is not null
        defaultCaisseFiltering("type.specified=true", "type.specified=false");
    }

    @Test
    void getAllCaissesByTypeContainsSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where type contains
        defaultCaisseFiltering("type.contains=" + DEFAULT_TYPE, "type.contains=" + UPDATED_TYPE);
    }

    @Test
    void getAllCaissesByTypeNotContainsSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where type does not contain
        defaultCaisseFiltering("type.doesNotContain=" + UPDATED_TYPE, "type.doesNotContain=" + DEFAULT_TYPE);
    }

    @Test
    void getAllCaissesByDeviseIsEqualToSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where devise equals to
        defaultCaisseFiltering("devise.equals=" + DEFAULT_DEVISE, "devise.equals=" + UPDATED_DEVISE);
    }

    @Test
    void getAllCaissesByDeviseIsInShouldWork() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where devise in
        defaultCaisseFiltering("devise.in=" + DEFAULT_DEVISE + "," + UPDATED_DEVISE, "devise.in=" + UPDATED_DEVISE);
    }

    @Test
    void getAllCaissesByDeviseIsNullOrNotNull() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where devise is not null
        defaultCaisseFiltering("devise.specified=true", "devise.specified=false");
    }

    @Test
    void getAllCaissesByDeviseContainsSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where devise contains
        defaultCaisseFiltering("devise.contains=" + DEFAULT_DEVISE, "devise.contains=" + UPDATED_DEVISE);
    }

    @Test
    void getAllCaissesByDeviseNotContainsSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where devise does not contain
        defaultCaisseFiltering("devise.doesNotContain=" + UPDATED_DEVISE, "devise.doesNotContain=" + DEFAULT_DEVISE);
    }

    @Test
    void getAllCaissesBySoldeIsEqualToSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where solde equals to
        defaultCaisseFiltering("solde.equals=" + DEFAULT_SOLDE, "solde.equals=" + UPDATED_SOLDE);
    }

    @Test
    void getAllCaissesBySoldeIsInShouldWork() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where solde in
        defaultCaisseFiltering("solde.in=" + DEFAULT_SOLDE + "," + UPDATED_SOLDE, "solde.in=" + UPDATED_SOLDE);
    }

    @Test
    void getAllCaissesBySoldeIsNullOrNotNull() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where solde is not null
        defaultCaisseFiltering("solde.specified=true", "solde.specified=false");
    }

    @Test
    void getAllCaissesBySoldeIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where solde is greater than or equal to
        defaultCaisseFiltering("solde.greaterThanOrEqual=" + DEFAULT_SOLDE, "solde.greaterThanOrEqual=" + UPDATED_SOLDE);
    }

    @Test
    void getAllCaissesBySoldeIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where solde is less than or equal to
        defaultCaisseFiltering("solde.lessThanOrEqual=" + DEFAULT_SOLDE, "solde.lessThanOrEqual=" + SMALLER_SOLDE);
    }

    @Test
    void getAllCaissesBySoldeIsLessThanSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where solde is less than
        defaultCaisseFiltering("solde.lessThan=" + UPDATED_SOLDE, "solde.lessThan=" + DEFAULT_SOLDE);
    }

    @Test
    void getAllCaissesBySoldeIsGreaterThanSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where solde is greater than
        defaultCaisseFiltering("solde.greaterThan=" + SMALLER_SOLDE, "solde.greaterThan=" + DEFAULT_SOLDE);
    }

    @Test
    void getAllCaissesByActifIsEqualToSomething() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where actif equals to
        defaultCaisseFiltering("actif.equals=" + DEFAULT_ACTIF, "actif.equals=" + UPDATED_ACTIF);
    }

    @Test
    void getAllCaissesByActifIsInShouldWork() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where actif in
        defaultCaisseFiltering("actif.in=" + DEFAULT_ACTIF + "," + UPDATED_ACTIF, "actif.in=" + UPDATED_ACTIF);
    }

    @Test
    void getAllCaissesByActifIsNullOrNotNull() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        // Get all the caisseList where actif is not null
        defaultCaisseFiltering("actif.specified=true", "actif.specified=false");
    }

    @Test
    void getAllCaissesByEntiteFinanciereIsEqualToSomething() {
        EntiteFinanciere entiteFinanciere = EntiteFinanciereResourceIT.createEntity();
        entiteFinanciereRepository.save(entiteFinanciere).block();
        Long entiteFinanciereId = entiteFinanciere.getId();
        caisse.setEntiteFinanciereId(entiteFinanciereId);
        insertedCaisse = caisseRepository.save(caisse).block();
        // Get all the caisseList where entiteFinanciere equals to entiteFinanciereId
        defaultCaisseShouldBeFound("entiteFinanciereId.equals=" + entiteFinanciereId);

        // Get all the caisseList where entiteFinanciere equals to (entiteFinanciereId + 1)
        defaultCaisseShouldNotBeFound("entiteFinanciereId.equals=" + (entiteFinanciereId + 1));
    }

    private void defaultCaisseFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultCaisseShouldBeFound(shouldBeFound);
        defaultCaisseShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCaisseShouldBeFound(String filter) {
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc&" + filter)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(caisse.getId().intValue()))
            .jsonPath("$.[*].nom")
            .value(hasItem(DEFAULT_NOM))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE))
            .jsonPath("$.[*].devise")
            .value(hasItem(DEFAULT_DEVISE))
            .jsonPath("$.[*].solde")
            .value(hasItem(sameNumber(DEFAULT_SOLDE)))
            .jsonPath("$.[*].actif")
            .value(hasItem(DEFAULT_ACTIF));

        // Check, that the count call also returns 1
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "/count?sort=id,desc&" + filter)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$")
            .value(is(1));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCaisseShouldNotBeFound(String filter) {
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc&" + filter)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$")
            .isArray()
            .jsonPath("$")
            .isEmpty();

        // Check, that the count call also returns 0
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "/count?sort=id,desc&" + filter)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$")
            .value(is(0));
    }

    @Test
    void getNonExistingCaisse() {
        // Get the caisse
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingCaisse() throws Exception {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the caisse
        Caisse updatedCaisse = caisseRepository.findById(caisse.getId()).block();
        updatedCaisse
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .devise(UPDATED_DEVISE)
            .solde(UPDATED_SOLDE)
            .actif(UPDATED_ACTIF);
        CaisseDTO caisseDTO = caisseMapper.toDto(updatedCaisse);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, caisseDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Caisse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCaisseToMatchAllProperties(updatedCaisse);
    }

    @Test
    void putNonExistingCaisse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caisse.setId(longCount.incrementAndGet());

        // Create the Caisse
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, caisseDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Caisse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCaisse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caisse.setId(longCount.incrementAndGet());

        // Create the Caisse
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Caisse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCaisse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caisse.setId(longCount.incrementAndGet());

        // Create the Caisse
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Caisse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCaisseWithPatch() throws Exception {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the caisse using partial update
        Caisse partialUpdatedCaisse = new Caisse();
        partialUpdatedCaisse.setId(caisse.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCaisse.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedCaisse))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Caisse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCaisseUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCaisse, caisse), getPersistedCaisse(caisse));
    }

    @Test
    void fullUpdateCaisseWithPatch() throws Exception {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the caisse using partial update
        Caisse partialUpdatedCaisse = new Caisse();
        partialUpdatedCaisse.setId(caisse.getId());

        partialUpdatedCaisse
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .devise(UPDATED_DEVISE)
            .solde(UPDATED_SOLDE)
            .actif(UPDATED_ACTIF);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCaisse.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedCaisse))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Caisse in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCaisseUpdatableFieldsEquals(partialUpdatedCaisse, getPersistedCaisse(partialUpdatedCaisse));
    }

    @Test
    void patchNonExistingCaisse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caisse.setId(longCount.incrementAndGet());

        // Create the Caisse
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, caisseDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Caisse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCaisse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caisse.setId(longCount.incrementAndGet());

        // Create the Caisse
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Caisse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCaisse() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        caisse.setId(longCount.incrementAndGet());

        // Create the Caisse
        CaisseDTO caisseDTO = caisseMapper.toDto(caisse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(caisseDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Caisse in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCaisse() {
        // Initialize the database
        insertedCaisse = caisseRepository.save(caisse).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the caisse
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, caisse.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return caisseRepository.count().block();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Caisse getPersistedCaisse(Caisse caisse) {
        return caisseRepository.findById(caisse.getId()).block();
    }

    protected void assertPersistedCaisseToMatchAllProperties(Caisse expectedCaisse) {
        // Test fails because reactive api returns an empty object instead of null
        // assertCaisseAllPropertiesEquals(expectedCaisse, getPersistedCaisse(expectedCaisse));
        assertCaisseUpdatableFieldsEquals(expectedCaisse, getPersistedCaisse(expectedCaisse));
    }

    protected void assertPersistedCaisseToMatchUpdatableProperties(Caisse expectedCaisse) {
        // Test fails because reactive api returns an empty object instead of null
        // assertCaisseAllUpdatablePropertiesEquals(expectedCaisse, getPersistedCaisse(expectedCaisse));
        assertCaisseUpdatableFieldsEquals(expectedCaisse, getPersistedCaisse(expectedCaisse));
    }
}
