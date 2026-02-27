package com.gracefinance.gracefinanceapp.web.rest.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.CategorieAsserts.assertCategorieUpdatableFieldsEquals;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefinance.gracefinanceapp.IntegrationTest;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.principal.EntiteFinanciereRepository;
import com.gracefinance.gracefinanceapp.repository.referentiel.CategorieRepository;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.CategorieDTO;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.CategorieMapper;
import com.gracefinance.gracefinanceapp.web.rest.referentiel.CategorieResource;
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
 * Integration tests for the {@link CategorieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class CategorieResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    private static final String ENTITY_API_URL = "/api/categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private CategorieMapper categorieMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Categorie categorie;

    private Categorie insertedCategorie;

    @Autowired
    private EntiteFinanciereRepository entiteFinanciereRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorie createEntity(EntityManager em) {
        Categorie categorie = new Categorie()
            .nom(DEFAULT_NOM)
            .code(DEFAULT_CODE)
            .typeCategorie(DEFAULT_TYPE_CATEGORIE)
            .description(DEFAULT_DESCRIPTION)
            .actif(DEFAULT_ACTIF);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createEntity()).block();
        categorie.setEntiteFinanciere(entiteFinanciere);
        return categorie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorie createUpdatedEntity(EntityManager em) {
        Categorie updatedCategorie = new Categorie()
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .typeCategorie(UPDATED_TYPE_CATEGORIE)
            .description(UPDATED_DESCRIPTION)
            .actif(UPDATED_ACTIF);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createUpdatedEntity()).block();
        updatedCategorie.setEntiteFinanciere(entiteFinanciere);
        return updatedCategorie;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Categorie.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        EntiteFinanciereResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        categorie = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedCategorie != null) {
            categorieRepository.delete(insertedCategorie).block();
            insertedCategorie = null;
        }
        deleteEntities(em);
    }

    @Test
    void createCategorie() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
        var returnedCategorieDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(CategorieDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Categorie in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCategorie = categorieMapper.toEntity(returnedCategorieDTO);
        assertCategorieUpdatableFieldsEquals(returnedCategorie, getPersistedCategorie(returnedCategorie));

        insertedCategorie = returnedCategorie;
    }

    @Test
    void createCategorieWithExistingId() throws Exception {
        // Create the Categorie with an existing ID
        categorie.setId(1L);
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        categorie.setNom(null);

        // Create the Categorie, which fails.
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        categorie.setCode(null);

        // Create the Categorie, which fails.
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeCategorieIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        categorie.setTypeCategorie(null);

        // Create the Categorie, which fails.
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkActifIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        categorie.setActif(null);

        // Create the Categorie, which fails.
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCategories() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList
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
            .value(hasItem(categorie.getId().intValue()))
            .jsonPath("$.[*].nom")
            .value(hasItem(DEFAULT_NOM))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].typeCategorie")
            .value(hasItem(DEFAULT_TYPE_CATEGORIE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].actif")
            .value(hasItem(DEFAULT_ACTIF));
    }

    @Test
    void getCategorie() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get the categorie
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, categorie.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(categorie.getId().intValue()))
            .jsonPath("$.nom")
            .value(is(DEFAULT_NOM))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.typeCategorie")
            .value(is(DEFAULT_TYPE_CATEGORIE))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.actif")
            .value(is(DEFAULT_ACTIF));
    }

    @Test
    void getCategoriesByIdFiltering() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        Long id = categorie.getId();

        defaultCategorieFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCategorieFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCategorieFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllCategoriesByNomIsEqualToSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where nom equals to
        defaultCategorieFiltering("nom.equals=" + DEFAULT_NOM, "nom.equals=" + UPDATED_NOM);
    }

    @Test
    void getAllCategoriesByNomIsInShouldWork() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where nom in
        defaultCategorieFiltering("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM, "nom.in=" + UPDATED_NOM);
    }

    @Test
    void getAllCategoriesByNomIsNullOrNotNull() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where nom is not null
        defaultCategorieFiltering("nom.specified=true", "nom.specified=false");
    }

    @Test
    void getAllCategoriesByNomContainsSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where nom contains
        defaultCategorieFiltering("nom.contains=" + DEFAULT_NOM, "nom.contains=" + UPDATED_NOM);
    }

    @Test
    void getAllCategoriesByNomNotContainsSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where nom does not contain
        defaultCategorieFiltering("nom.doesNotContain=" + UPDATED_NOM, "nom.doesNotContain=" + DEFAULT_NOM);
    }

    @Test
    void getAllCategoriesByCodeIsEqualToSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where code equals to
        defaultCategorieFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    void getAllCategoriesByCodeIsInShouldWork() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where code in
        defaultCategorieFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    void getAllCategoriesByCodeIsNullOrNotNull() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where code is not null
        defaultCategorieFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    void getAllCategoriesByCodeContainsSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where code contains
        defaultCategorieFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    void getAllCategoriesByCodeNotContainsSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where code does not contain
        defaultCategorieFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    void getAllCategoriesByTypeCategorieIsEqualToSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where typeCategorie equals to
        defaultCategorieFiltering("typeCategorie.equals=" + DEFAULT_TYPE_CATEGORIE, "typeCategorie.equals=" + UPDATED_TYPE_CATEGORIE);
    }

    @Test
    void getAllCategoriesByTypeCategorieIsInShouldWork() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where typeCategorie in
        defaultCategorieFiltering(
            "typeCategorie.in=" + DEFAULT_TYPE_CATEGORIE + "," + UPDATED_TYPE_CATEGORIE,
            "typeCategorie.in=" + UPDATED_TYPE_CATEGORIE
        );
    }

    @Test
    void getAllCategoriesByTypeCategorieIsNullOrNotNull() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where typeCategorie is not null
        defaultCategorieFiltering("typeCategorie.specified=true", "typeCategorie.specified=false");
    }

    @Test
    void getAllCategoriesByTypeCategorieContainsSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where typeCategorie contains
        defaultCategorieFiltering("typeCategorie.contains=" + DEFAULT_TYPE_CATEGORIE, "typeCategorie.contains=" + UPDATED_TYPE_CATEGORIE);
    }

    @Test
    void getAllCategoriesByTypeCategorieNotContainsSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where typeCategorie does not contain
        defaultCategorieFiltering(
            "typeCategorie.doesNotContain=" + UPDATED_TYPE_CATEGORIE,
            "typeCategorie.doesNotContain=" + DEFAULT_TYPE_CATEGORIE
        );
    }

    @Test
    void getAllCategoriesByDescriptionIsEqualToSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where description equals to
        defaultCategorieFiltering("description.equals=" + DEFAULT_DESCRIPTION, "description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    void getAllCategoriesByDescriptionIsInShouldWork() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where description in
        defaultCategorieFiltering(
            "description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION,
            "description.in=" + UPDATED_DESCRIPTION
        );
    }

    @Test
    void getAllCategoriesByDescriptionIsNullOrNotNull() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where description is not null
        defaultCategorieFiltering("description.specified=true", "description.specified=false");
    }

    @Test
    void getAllCategoriesByDescriptionContainsSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where description contains
        defaultCategorieFiltering("description.contains=" + DEFAULT_DESCRIPTION, "description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    void getAllCategoriesByDescriptionNotContainsSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where description does not contain
        defaultCategorieFiltering("description.doesNotContain=" + UPDATED_DESCRIPTION, "description.doesNotContain=" + DEFAULT_DESCRIPTION);
    }

    @Test
    void getAllCategoriesByActifIsEqualToSomething() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where actif equals to
        defaultCategorieFiltering("actif.equals=" + DEFAULT_ACTIF, "actif.equals=" + UPDATED_ACTIF);
    }

    @Test
    void getAllCategoriesByActifIsInShouldWork() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where actif in
        defaultCategorieFiltering("actif.in=" + DEFAULT_ACTIF + "," + UPDATED_ACTIF, "actif.in=" + UPDATED_ACTIF);
    }

    @Test
    void getAllCategoriesByActifIsNullOrNotNull() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        // Get all the categorieList where actif is not null
        defaultCategorieFiltering("actif.specified=true", "actif.specified=false");
    }

    @Test
    void getAllCategoriesByEntiteFinanciereIsEqualToSomething() {
        EntiteFinanciere entiteFinanciere = EntiteFinanciereResourceIT.createEntity();
        entiteFinanciereRepository.save(entiteFinanciere).block();
        Long entiteFinanciereId = entiteFinanciere.getId();
        categorie.setEntiteFinanciereId(entiteFinanciereId);
        insertedCategorie = categorieRepository.save(categorie).block();
        // Get all the categorieList where entiteFinanciere equals to entiteFinanciereId
        defaultCategorieShouldBeFound("entiteFinanciereId.equals=" + entiteFinanciereId);

        // Get all the categorieList where entiteFinanciere equals to (entiteFinanciereId + 1)
        defaultCategorieShouldNotBeFound("entiteFinanciereId.equals=" + (entiteFinanciereId + 1));
    }

    private void defaultCategorieFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultCategorieShouldBeFound(shouldBeFound);
        defaultCategorieShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategorieShouldBeFound(String filter) {
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
            .value(hasItem(categorie.getId().intValue()))
            .jsonPath("$.[*].nom")
            .value(hasItem(DEFAULT_NOM))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].typeCategorie")
            .value(hasItem(DEFAULT_TYPE_CATEGORIE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
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
    private void defaultCategorieShouldNotBeFound(String filter) {
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
    void getNonExistingCategorie() {
        // Get the categorie
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingCategorie() throws Exception {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorie
        Categorie updatedCategorie = categorieRepository.findById(categorie.getId()).block();
        updatedCategorie
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .typeCategorie(UPDATED_TYPE_CATEGORIE)
            .description(UPDATED_DESCRIPTION)
            .actif(UPDATED_ACTIF);
        CategorieDTO categorieDTO = categorieMapper.toDto(updatedCategorie);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, categorieDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCategorieToMatchAllProperties(updatedCategorie);
    }

    @Test
    void putNonExistingCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, categorieDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCategorieWithPatch() throws Exception {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorie using partial update
        Categorie partialUpdatedCategorie = new Categorie();
        partialUpdatedCategorie.setId(categorie.getId());

        partialUpdatedCategorie.code(UPDATED_CODE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCategorie.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedCategorie))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Categorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategorieUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCategorie, categorie),
            getPersistedCategorie(categorie)
        );
    }

    @Test
    void fullUpdateCategorieWithPatch() throws Exception {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorie using partial update
        Categorie partialUpdatedCategorie = new Categorie();
        partialUpdatedCategorie.setId(categorie.getId());

        partialUpdatedCategorie
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .typeCategorie(UPDATED_TYPE_CATEGORIE)
            .description(UPDATED_DESCRIPTION)
            .actif(UPDATED_ACTIF);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCategorie.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedCategorie))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Categorie in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategorieUpdatableFieldsEquals(partialUpdatedCategorie, getPersistedCategorie(partialUpdatedCategorie));
    }

    @Test
    void patchNonExistingCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, categorieDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCategorie() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorie.setId(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(categorieDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Categorie in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCategorie() {
        // Initialize the database
        insertedCategorie = categorieRepository.save(categorie).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the categorie
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, categorie.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return categorieRepository.count().block();
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

    protected Categorie getPersistedCategorie(Categorie categorie) {
        return categorieRepository.findById(categorie.getId()).block();
    }

    protected void assertPersistedCategorieToMatchAllProperties(Categorie expectedCategorie) {
        // Test fails because reactive api returns an empty object instead of null
        // assertCategorieAllPropertiesEquals(expectedCategorie, getPersistedCategorie(expectedCategorie));
        assertCategorieUpdatableFieldsEquals(expectedCategorie, getPersistedCategorie(expectedCategorie));
    }

    protected void assertPersistedCategorieToMatchUpdatableProperties(Categorie expectedCategorie) {
        // Test fails because reactive api returns an empty object instead of null
        // assertCategorieAllUpdatablePropertiesEquals(expectedCategorie, getPersistedCategorie(expectedCategorie));
        assertCategorieUpdatableFieldsEquals(expectedCategorie, getPersistedCategorie(expectedCategorie));
    }
}
