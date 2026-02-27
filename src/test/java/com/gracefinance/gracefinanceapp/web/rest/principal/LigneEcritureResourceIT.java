package com.gracefinance.gracefinanceapp.web.rest.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.LigneEcritureAsserts.assertLigneEcritureUpdatableFieldsEquals;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefinance.gracefinanceapp.IntegrationTest;
import com.gracefinance.gracefinanceapp.domain.principal.CompteComptable;
import com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable;
import com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.principal.CompteComptableRepository;
import com.gracefinance.gracefinanceapp.repository.principal.EcritureComptableRepository;
import com.gracefinance.gracefinanceapp.repository.principal.LigneEcritureRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.LigneEcritureDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.LigneEcritureMapper;
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
 * Integration tests for the {@link LigneEcritureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class LigneEcritureResourceIT {

    private static final BigDecimal DEFAULT_MONTANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT = new BigDecimal(2);
    private static final BigDecimal SMALLER_MONTANT = new BigDecimal(1 - 1);

    private static final String DEFAULT_SENS = "AAAAAAAAAA";
    private static final String UPDATED_SENS = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ligne-ecritures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LigneEcritureRepository ligneEcritureRepository;

    @Autowired
    private LigneEcritureMapper ligneEcritureMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private LigneEcriture ligneEcriture;

    private LigneEcriture insertedLigneEcriture;

    @Autowired
    private EcritureComptableRepository ecritureComptableRepository;

    @Autowired
    private CompteComptableRepository compteComptableRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneEcriture createEntity(EntityManager em) {
        LigneEcriture ligneEcriture = new LigneEcriture().montant(DEFAULT_MONTANT).sens(DEFAULT_SENS).libelle(DEFAULT_LIBELLE);
        // Add required entity
        EcritureComptable ecritureComptable;
        ecritureComptable = em.insert(EcritureComptableResourceIT.createEntity()).block();
        ligneEcriture.setEcriture(ecritureComptable);
        // Add required entity
        CompteComptable compteComptable;
        compteComptable = em.insert(CompteComptableResourceIT.createEntity()).block();
        ligneEcriture.setCompte(compteComptable);
        return ligneEcriture;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneEcriture createUpdatedEntity(EntityManager em) {
        LigneEcriture updatedLigneEcriture = new LigneEcriture().montant(UPDATED_MONTANT).sens(UPDATED_SENS).libelle(UPDATED_LIBELLE);
        // Add required entity
        EcritureComptable ecritureComptable;
        ecritureComptable = em.insert(EcritureComptableResourceIT.createUpdatedEntity()).block();
        updatedLigneEcriture.setEcriture(ecritureComptable);
        // Add required entity
        CompteComptable compteComptable;
        compteComptable = em.insert(CompteComptableResourceIT.createUpdatedEntity()).block();
        updatedLigneEcriture.setCompte(compteComptable);
        return updatedLigneEcriture;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(LigneEcriture.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        EcritureComptableResourceIT.deleteEntities(em);
        CompteComptableResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        ligneEcriture = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedLigneEcriture != null) {
            ligneEcritureRepository.delete(insertedLigneEcriture).block();
            insertedLigneEcriture = null;
        }
        deleteEntities(em);
    }

    @Test
    void createLigneEcriture() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the LigneEcriture
        LigneEcritureDTO ligneEcritureDTO = ligneEcritureMapper.toDto(ligneEcriture);
        var returnedLigneEcritureDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ligneEcritureDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(LigneEcritureDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the LigneEcriture in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLigneEcriture = ligneEcritureMapper.toEntity(returnedLigneEcritureDTO);
        assertLigneEcritureUpdatableFieldsEquals(returnedLigneEcriture, getPersistedLigneEcriture(returnedLigneEcriture));

        insertedLigneEcriture = returnedLigneEcriture;
    }

    @Test
    void createLigneEcritureWithExistingId() throws Exception {
        // Create the LigneEcriture with an existing ID
        ligneEcriture.setId(1L);
        LigneEcritureDTO ligneEcritureDTO = ligneEcritureMapper.toDto(ligneEcriture);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ligneEcritureDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the LigneEcriture in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkMontantIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ligneEcriture.setMontant(null);

        // Create the LigneEcriture, which fails.
        LigneEcritureDTO ligneEcritureDTO = ligneEcritureMapper.toDto(ligneEcriture);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ligneEcritureDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkSensIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ligneEcriture.setSens(null);

        // Create the LigneEcriture, which fails.
        LigneEcritureDTO ligneEcritureDTO = ligneEcritureMapper.toDto(ligneEcriture);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ligneEcritureDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllLigneEcritures() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList
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
            .value(hasItem(ligneEcriture.getId().intValue()))
            .jsonPath("$.[*].montant")
            .value(hasItem(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.[*].sens")
            .value(hasItem(DEFAULT_SENS))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE));
    }

    @Test
    void getLigneEcriture() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get the ligneEcriture
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, ligneEcriture.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(ligneEcriture.getId().intValue()))
            .jsonPath("$.montant")
            .value(is(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.sens")
            .value(is(DEFAULT_SENS))
            .jsonPath("$.libelle")
            .value(is(DEFAULT_LIBELLE));
    }

    @Test
    void getLigneEcrituresByIdFiltering() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        Long id = ligneEcriture.getId();

        defaultLigneEcritureFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultLigneEcritureFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultLigneEcritureFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllLigneEcrituresByMontantIsEqualToSomething() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where montant equals to
        defaultLigneEcritureFiltering("montant.equals=" + DEFAULT_MONTANT, "montant.equals=" + UPDATED_MONTANT);
    }

    @Test
    void getAllLigneEcrituresByMontantIsInShouldWork() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where montant in
        defaultLigneEcritureFiltering("montant.in=" + DEFAULT_MONTANT + "," + UPDATED_MONTANT, "montant.in=" + UPDATED_MONTANT);
    }

    @Test
    void getAllLigneEcrituresByMontantIsNullOrNotNull() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where montant is not null
        defaultLigneEcritureFiltering("montant.specified=true", "montant.specified=false");
    }

    @Test
    void getAllLigneEcrituresByMontantIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where montant is greater than or equal to
        defaultLigneEcritureFiltering("montant.greaterThanOrEqual=" + DEFAULT_MONTANT, "montant.greaterThanOrEqual=" + UPDATED_MONTANT);
    }

    @Test
    void getAllLigneEcrituresByMontantIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where montant is less than or equal to
        defaultLigneEcritureFiltering("montant.lessThanOrEqual=" + DEFAULT_MONTANT, "montant.lessThanOrEqual=" + SMALLER_MONTANT);
    }

    @Test
    void getAllLigneEcrituresByMontantIsLessThanSomething() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where montant is less than
        defaultLigneEcritureFiltering("montant.lessThan=" + UPDATED_MONTANT, "montant.lessThan=" + DEFAULT_MONTANT);
    }

    @Test
    void getAllLigneEcrituresByMontantIsGreaterThanSomething() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where montant is greater than
        defaultLigneEcritureFiltering("montant.greaterThan=" + SMALLER_MONTANT, "montant.greaterThan=" + DEFAULT_MONTANT);
    }

    @Test
    void getAllLigneEcrituresBySensIsEqualToSomething() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where sens equals to
        defaultLigneEcritureFiltering("sens.equals=" + DEFAULT_SENS, "sens.equals=" + UPDATED_SENS);
    }

    @Test
    void getAllLigneEcrituresBySensIsInShouldWork() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where sens in
        defaultLigneEcritureFiltering("sens.in=" + DEFAULT_SENS + "," + UPDATED_SENS, "sens.in=" + UPDATED_SENS);
    }

    @Test
    void getAllLigneEcrituresBySensIsNullOrNotNull() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where sens is not null
        defaultLigneEcritureFiltering("sens.specified=true", "sens.specified=false");
    }

    @Test
    void getAllLigneEcrituresBySensContainsSomething() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where sens contains
        defaultLigneEcritureFiltering("sens.contains=" + DEFAULT_SENS, "sens.contains=" + UPDATED_SENS);
    }

    @Test
    void getAllLigneEcrituresBySensNotContainsSomething() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where sens does not contain
        defaultLigneEcritureFiltering("sens.doesNotContain=" + UPDATED_SENS, "sens.doesNotContain=" + DEFAULT_SENS);
    }

    @Test
    void getAllLigneEcrituresByLibelleIsEqualToSomething() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where libelle equals to
        defaultLigneEcritureFiltering("libelle.equals=" + DEFAULT_LIBELLE, "libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    void getAllLigneEcrituresByLibelleIsInShouldWork() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where libelle in
        defaultLigneEcritureFiltering("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE, "libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    void getAllLigneEcrituresByLibelleIsNullOrNotNull() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where libelle is not null
        defaultLigneEcritureFiltering("libelle.specified=true", "libelle.specified=false");
    }

    @Test
    void getAllLigneEcrituresByLibelleContainsSomething() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where libelle contains
        defaultLigneEcritureFiltering("libelle.contains=" + DEFAULT_LIBELLE, "libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    void getAllLigneEcrituresByLibelleNotContainsSomething() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        // Get all the ligneEcritureList where libelle does not contain
        defaultLigneEcritureFiltering("libelle.doesNotContain=" + UPDATED_LIBELLE, "libelle.doesNotContain=" + DEFAULT_LIBELLE);
    }

    @Test
    void getAllLigneEcrituresByEcritureIsEqualToSomething() {
        EcritureComptable ecriture = EcritureComptableResourceIT.createEntity();
        ecritureComptableRepository.save(ecriture).block();
        Long ecritureId = ecriture.getId();
        ligneEcriture.setEcritureId(ecritureId);
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();
        // Get all the ligneEcritureList where ecriture equals to ecritureId
        defaultLigneEcritureShouldBeFound("ecritureId.equals=" + ecritureId);

        // Get all the ligneEcritureList where ecriture equals to (ecritureId + 1)
        defaultLigneEcritureShouldNotBeFound("ecritureId.equals=" + (ecritureId + 1));
    }

    @Test
    void getAllLigneEcrituresByCompteIsEqualToSomething() {
        CompteComptable compte = CompteComptableResourceIT.createEntity();
        compteComptableRepository.save(compte).block();
        Long compteId = compte.getId();
        ligneEcriture.setCompteId(compteId);
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();
        // Get all the ligneEcritureList where compte equals to compteId
        defaultLigneEcritureShouldBeFound("compteId.equals=" + compteId);

        // Get all the ligneEcritureList where compte equals to (compteId + 1)
        defaultLigneEcritureShouldNotBeFound("compteId.equals=" + (compteId + 1));
    }

    private void defaultLigneEcritureFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultLigneEcritureShouldBeFound(shouldBeFound);
        defaultLigneEcritureShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLigneEcritureShouldBeFound(String filter) {
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
            .value(hasItem(ligneEcriture.getId().intValue()))
            .jsonPath("$.[*].montant")
            .value(hasItem(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.[*].sens")
            .value(hasItem(DEFAULT_SENS))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE));

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
    private void defaultLigneEcritureShouldNotBeFound(String filter) {
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
    void getNonExistingLigneEcriture() {
        // Get the ligneEcriture
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingLigneEcriture() throws Exception {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ligneEcriture
        LigneEcriture updatedLigneEcriture = ligneEcritureRepository.findById(ligneEcriture.getId()).block();
        updatedLigneEcriture.montant(UPDATED_MONTANT).sens(UPDATED_SENS).libelle(UPDATED_LIBELLE);
        LigneEcritureDTO ligneEcritureDTO = ligneEcritureMapper.toDto(updatedLigneEcriture);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, ligneEcritureDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ligneEcritureDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the LigneEcriture in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLigneEcritureToMatchAllProperties(updatedLigneEcriture);
    }

    @Test
    void putNonExistingLigneEcriture() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligneEcriture.setId(longCount.incrementAndGet());

        // Create the LigneEcriture
        LigneEcritureDTO ligneEcritureDTO = ligneEcritureMapper.toDto(ligneEcriture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, ligneEcritureDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ligneEcritureDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the LigneEcriture in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLigneEcriture() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligneEcriture.setId(longCount.incrementAndGet());

        // Create the LigneEcriture
        LigneEcritureDTO ligneEcritureDTO = ligneEcritureMapper.toDto(ligneEcriture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ligneEcritureDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the LigneEcriture in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLigneEcriture() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligneEcriture.setId(longCount.incrementAndGet());

        // Create the LigneEcriture
        LigneEcritureDTO ligneEcritureDTO = ligneEcritureMapper.toDto(ligneEcriture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ligneEcritureDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the LigneEcriture in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLigneEcritureWithPatch() throws Exception {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ligneEcriture using partial update
        LigneEcriture partialUpdatedLigneEcriture = new LigneEcriture();
        partialUpdatedLigneEcriture.setId(ligneEcriture.getId());

        partialUpdatedLigneEcriture.montant(UPDATED_MONTANT).sens(UPDATED_SENS);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedLigneEcriture.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedLigneEcriture))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the LigneEcriture in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLigneEcritureUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedLigneEcriture, ligneEcriture),
            getPersistedLigneEcriture(ligneEcriture)
        );
    }

    @Test
    void fullUpdateLigneEcritureWithPatch() throws Exception {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ligneEcriture using partial update
        LigneEcriture partialUpdatedLigneEcriture = new LigneEcriture();
        partialUpdatedLigneEcriture.setId(ligneEcriture.getId());

        partialUpdatedLigneEcriture.montant(UPDATED_MONTANT).sens(UPDATED_SENS).libelle(UPDATED_LIBELLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedLigneEcriture.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedLigneEcriture))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the LigneEcriture in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLigneEcritureUpdatableFieldsEquals(partialUpdatedLigneEcriture, getPersistedLigneEcriture(partialUpdatedLigneEcriture));
    }

    @Test
    void patchNonExistingLigneEcriture() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligneEcriture.setId(longCount.incrementAndGet());

        // Create the LigneEcriture
        LigneEcritureDTO ligneEcritureDTO = ligneEcritureMapper.toDto(ligneEcriture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, ligneEcritureDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ligneEcritureDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the LigneEcriture in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLigneEcriture() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligneEcriture.setId(longCount.incrementAndGet());

        // Create the LigneEcriture
        LigneEcritureDTO ligneEcritureDTO = ligneEcritureMapper.toDto(ligneEcriture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ligneEcritureDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the LigneEcriture in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLigneEcriture() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ligneEcriture.setId(longCount.incrementAndGet());

        // Create the LigneEcriture
        LigneEcritureDTO ligneEcritureDTO = ligneEcritureMapper.toDto(ligneEcriture);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ligneEcritureDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the LigneEcriture in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLigneEcriture() {
        // Initialize the database
        insertedLigneEcriture = ligneEcritureRepository.save(ligneEcriture).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ligneEcriture
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, ligneEcriture.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ligneEcritureRepository.count().block();
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

    protected LigneEcriture getPersistedLigneEcriture(LigneEcriture ligneEcriture) {
        return ligneEcritureRepository.findById(ligneEcriture.getId()).block();
    }

    protected void assertPersistedLigneEcritureToMatchAllProperties(LigneEcriture expectedLigneEcriture) {
        // Test fails because reactive api returns an empty object instead of null
        // assertLigneEcritureAllPropertiesEquals(expectedLigneEcriture, getPersistedLigneEcriture(expectedLigneEcriture));
        assertLigneEcritureUpdatableFieldsEquals(expectedLigneEcriture, getPersistedLigneEcriture(expectedLigneEcriture));
    }

    protected void assertPersistedLigneEcritureToMatchUpdatableProperties(LigneEcriture expectedLigneEcriture) {
        // Test fails because reactive api returns an empty object instead of null
        // assertLigneEcritureAllUpdatablePropertiesEquals(expectedLigneEcriture, getPersistedLigneEcriture(expectedLigneEcriture));
        assertLigneEcritureUpdatableFieldsEquals(expectedLigneEcriture, getPersistedLigneEcriture(expectedLigneEcriture));
    }
}
