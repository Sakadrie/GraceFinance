package com.gracefinance.gracefinanceapp.web.rest.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.EcritureComptableAsserts.assertEcritureComptableUpdatableFieldsEquals;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefinance.gracefinanceapp.IntegrationTest;
import com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.principal.EcritureComptableRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.EcritureComptableDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.EcritureComptableMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link EcritureComptableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class EcritureComptableResourceIT {

    private static final LocalDate DEFAULT_DATE_COMPTABLE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_COMPTABLE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_COMPTABLE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_NUMERO_PIECE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_PIECE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_EXTERNE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_EXTERNE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ecriture-comptables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EcritureComptableRepository ecritureComptableRepository;

    @Autowired
    private EcritureComptableMapper ecritureComptableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private EcritureComptable ecritureComptable;

    private EcritureComptable insertedEcritureComptable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EcritureComptable createEntity() {
        return new EcritureComptable()
            .dateComptable(DEFAULT_DATE_COMPTABLE)
            .numeroPiece(DEFAULT_NUMERO_PIECE)
            .libelle(DEFAULT_LIBELLE)
            .referenceExterne(DEFAULT_REFERENCE_EXTERNE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EcritureComptable createUpdatedEntity() {
        return new EcritureComptable()
            .dateComptable(UPDATED_DATE_COMPTABLE)
            .numeroPiece(UPDATED_NUMERO_PIECE)
            .libelle(UPDATED_LIBELLE)
            .referenceExterne(UPDATED_REFERENCE_EXTERNE);
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(EcritureComptable.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @BeforeEach
    void initTest() {
        ecritureComptable = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEcritureComptable != null) {
            ecritureComptableRepository.delete(insertedEcritureComptable).block();
            insertedEcritureComptable = null;
        }
        deleteEntities(em);
    }

    @Test
    void createEcritureComptable() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EcritureComptable
        EcritureComptableDTO ecritureComptableDTO = ecritureComptableMapper.toDto(ecritureComptable);
        var returnedEcritureComptableDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ecritureComptableDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(EcritureComptableDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the EcritureComptable in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEcritureComptable = ecritureComptableMapper.toEntity(returnedEcritureComptableDTO);
        assertEcritureComptableUpdatableFieldsEquals(returnedEcritureComptable, getPersistedEcritureComptable(returnedEcritureComptable));

        insertedEcritureComptable = returnedEcritureComptable;
    }

    @Test
    void createEcritureComptableWithExistingId() throws Exception {
        // Create the EcritureComptable with an existing ID
        ecritureComptable.setId(1L);
        EcritureComptableDTO ecritureComptableDTO = ecritureComptableMapper.toDto(ecritureComptable);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ecritureComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EcritureComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkDateComptableIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ecritureComptable.setDateComptable(null);

        // Create the EcritureComptable, which fails.
        EcritureComptableDTO ecritureComptableDTO = ecritureComptableMapper.toDto(ecritureComptable);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ecritureComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkNumeroPieceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        ecritureComptable.setNumeroPiece(null);

        // Create the EcritureComptable, which fails.
        EcritureComptableDTO ecritureComptableDTO = ecritureComptableMapper.toDto(ecritureComptable);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ecritureComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllEcritureComptables() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList
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
            .value(hasItem(ecritureComptable.getId().intValue()))
            .jsonPath("$.[*].dateComptable")
            .value(hasItem(DEFAULT_DATE_COMPTABLE.toString()))
            .jsonPath("$.[*].numeroPiece")
            .value(hasItem(DEFAULT_NUMERO_PIECE))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE))
            .jsonPath("$.[*].referenceExterne")
            .value(hasItem(DEFAULT_REFERENCE_EXTERNE));
    }

    @Test
    void getEcritureComptable() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get the ecritureComptable
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, ecritureComptable.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(ecritureComptable.getId().intValue()))
            .jsonPath("$.dateComptable")
            .value(is(DEFAULT_DATE_COMPTABLE.toString()))
            .jsonPath("$.numeroPiece")
            .value(is(DEFAULT_NUMERO_PIECE))
            .jsonPath("$.libelle")
            .value(is(DEFAULT_LIBELLE))
            .jsonPath("$.referenceExterne")
            .value(is(DEFAULT_REFERENCE_EXTERNE));
    }

    @Test
    void getEcritureComptablesByIdFiltering() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        Long id = ecritureComptable.getId();

        defaultEcritureComptableFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultEcritureComptableFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultEcritureComptableFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllEcritureComptablesByDateComptableIsEqualToSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where dateComptable equals to
        defaultEcritureComptableFiltering(
            "dateComptable.equals=" + DEFAULT_DATE_COMPTABLE,
            "dateComptable.equals=" + UPDATED_DATE_COMPTABLE
        );
    }

    @Test
    void getAllEcritureComptablesByDateComptableIsInShouldWork() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where dateComptable in
        defaultEcritureComptableFiltering(
            "dateComptable.in=" + DEFAULT_DATE_COMPTABLE + "," + UPDATED_DATE_COMPTABLE,
            "dateComptable.in=" + UPDATED_DATE_COMPTABLE
        );
    }

    @Test
    void getAllEcritureComptablesByDateComptableIsNullOrNotNull() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where dateComptable is not null
        defaultEcritureComptableFiltering("dateComptable.specified=true", "dateComptable.specified=false");
    }

    @Test
    void getAllEcritureComptablesByDateComptableIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where dateComptable is greater than or equal to
        defaultEcritureComptableFiltering(
            "dateComptable.greaterThanOrEqual=" + DEFAULT_DATE_COMPTABLE,
            "dateComptable.greaterThanOrEqual=" + UPDATED_DATE_COMPTABLE
        );
    }

    @Test
    void getAllEcritureComptablesByDateComptableIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where dateComptable is less than or equal to
        defaultEcritureComptableFiltering(
            "dateComptable.lessThanOrEqual=" + DEFAULT_DATE_COMPTABLE,
            "dateComptable.lessThanOrEqual=" + SMALLER_DATE_COMPTABLE
        );
    }

    @Test
    void getAllEcritureComptablesByDateComptableIsLessThanSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where dateComptable is less than
        defaultEcritureComptableFiltering(
            "dateComptable.lessThan=" + UPDATED_DATE_COMPTABLE,
            "dateComptable.lessThan=" + DEFAULT_DATE_COMPTABLE
        );
    }

    @Test
    void getAllEcritureComptablesByDateComptableIsGreaterThanSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where dateComptable is greater than
        defaultEcritureComptableFiltering(
            "dateComptable.greaterThan=" + SMALLER_DATE_COMPTABLE,
            "dateComptable.greaterThan=" + DEFAULT_DATE_COMPTABLE
        );
    }

    @Test
    void getAllEcritureComptablesByNumeroPieceIsEqualToSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where numeroPiece equals to
        defaultEcritureComptableFiltering("numeroPiece.equals=" + DEFAULT_NUMERO_PIECE, "numeroPiece.equals=" + UPDATED_NUMERO_PIECE);
    }

    @Test
    void getAllEcritureComptablesByNumeroPieceIsInShouldWork() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where numeroPiece in
        defaultEcritureComptableFiltering(
            "numeroPiece.in=" + DEFAULT_NUMERO_PIECE + "," + UPDATED_NUMERO_PIECE,
            "numeroPiece.in=" + UPDATED_NUMERO_PIECE
        );
    }

    @Test
    void getAllEcritureComptablesByNumeroPieceIsNullOrNotNull() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where numeroPiece is not null
        defaultEcritureComptableFiltering("numeroPiece.specified=true", "numeroPiece.specified=false");
    }

    @Test
    void getAllEcritureComptablesByNumeroPieceContainsSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where numeroPiece contains
        defaultEcritureComptableFiltering("numeroPiece.contains=" + DEFAULT_NUMERO_PIECE, "numeroPiece.contains=" + UPDATED_NUMERO_PIECE);
    }

    @Test
    void getAllEcritureComptablesByNumeroPieceNotContainsSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where numeroPiece does not contain
        defaultEcritureComptableFiltering(
            "numeroPiece.doesNotContain=" + UPDATED_NUMERO_PIECE,
            "numeroPiece.doesNotContain=" + DEFAULT_NUMERO_PIECE
        );
    }

    @Test
    void getAllEcritureComptablesByLibelleIsEqualToSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where libelle equals to
        defaultEcritureComptableFiltering("libelle.equals=" + DEFAULT_LIBELLE, "libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    void getAllEcritureComptablesByLibelleIsInShouldWork() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where libelle in
        defaultEcritureComptableFiltering("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE, "libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    void getAllEcritureComptablesByLibelleIsNullOrNotNull() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where libelle is not null
        defaultEcritureComptableFiltering("libelle.specified=true", "libelle.specified=false");
    }

    @Test
    void getAllEcritureComptablesByLibelleContainsSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where libelle contains
        defaultEcritureComptableFiltering("libelle.contains=" + DEFAULT_LIBELLE, "libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    void getAllEcritureComptablesByLibelleNotContainsSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where libelle does not contain
        defaultEcritureComptableFiltering("libelle.doesNotContain=" + UPDATED_LIBELLE, "libelle.doesNotContain=" + DEFAULT_LIBELLE);
    }

    @Test
    void getAllEcritureComptablesByReferenceExterneIsEqualToSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where referenceExterne equals to
        defaultEcritureComptableFiltering(
            "referenceExterne.equals=" + DEFAULT_REFERENCE_EXTERNE,
            "referenceExterne.equals=" + UPDATED_REFERENCE_EXTERNE
        );
    }

    @Test
    void getAllEcritureComptablesByReferenceExterneIsInShouldWork() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where referenceExterne in
        defaultEcritureComptableFiltering(
            "referenceExterne.in=" + DEFAULT_REFERENCE_EXTERNE + "," + UPDATED_REFERENCE_EXTERNE,
            "referenceExterne.in=" + UPDATED_REFERENCE_EXTERNE
        );
    }

    @Test
    void getAllEcritureComptablesByReferenceExterneIsNullOrNotNull() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where referenceExterne is not null
        defaultEcritureComptableFiltering("referenceExterne.specified=true", "referenceExterne.specified=false");
    }

    @Test
    void getAllEcritureComptablesByReferenceExterneContainsSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where referenceExterne contains
        defaultEcritureComptableFiltering(
            "referenceExterne.contains=" + DEFAULT_REFERENCE_EXTERNE,
            "referenceExterne.contains=" + UPDATED_REFERENCE_EXTERNE
        );
    }

    @Test
    void getAllEcritureComptablesByReferenceExterneNotContainsSomething() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        // Get all the ecritureComptableList where referenceExterne does not contain
        defaultEcritureComptableFiltering(
            "referenceExterne.doesNotContain=" + UPDATED_REFERENCE_EXTERNE,
            "referenceExterne.doesNotContain=" + DEFAULT_REFERENCE_EXTERNE
        );
    }

    private void defaultEcritureComptableFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultEcritureComptableShouldBeFound(shouldBeFound);
        defaultEcritureComptableShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEcritureComptableShouldBeFound(String filter) {
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
            .value(hasItem(ecritureComptable.getId().intValue()))
            .jsonPath("$.[*].dateComptable")
            .value(hasItem(DEFAULT_DATE_COMPTABLE.toString()))
            .jsonPath("$.[*].numeroPiece")
            .value(hasItem(DEFAULT_NUMERO_PIECE))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE))
            .jsonPath("$.[*].referenceExterne")
            .value(hasItem(DEFAULT_REFERENCE_EXTERNE));

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
    private void defaultEcritureComptableShouldNotBeFound(String filter) {
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
    void getNonExistingEcritureComptable() {
        // Get the ecritureComptable
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingEcritureComptable() throws Exception {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecritureComptable
        EcritureComptable updatedEcritureComptable = ecritureComptableRepository.findById(ecritureComptable.getId()).block();
        updatedEcritureComptable
            .dateComptable(UPDATED_DATE_COMPTABLE)
            .numeroPiece(UPDATED_NUMERO_PIECE)
            .libelle(UPDATED_LIBELLE)
            .referenceExterne(UPDATED_REFERENCE_EXTERNE);
        EcritureComptableDTO ecritureComptableDTO = ecritureComptableMapper.toDto(updatedEcritureComptable);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, ecritureComptableDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ecritureComptableDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the EcritureComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEcritureComptableToMatchAllProperties(updatedEcritureComptable);
    }

    @Test
    void putNonExistingEcritureComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecritureComptable.setId(longCount.incrementAndGet());

        // Create the EcritureComptable
        EcritureComptableDTO ecritureComptableDTO = ecritureComptableMapper.toDto(ecritureComptable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, ecritureComptableDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ecritureComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EcritureComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEcritureComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecritureComptable.setId(longCount.incrementAndGet());

        // Create the EcritureComptable
        EcritureComptableDTO ecritureComptableDTO = ecritureComptableMapper.toDto(ecritureComptable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ecritureComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EcritureComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEcritureComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecritureComptable.setId(longCount.incrementAndGet());

        // Create the EcritureComptable
        EcritureComptableDTO ecritureComptableDTO = ecritureComptableMapper.toDto(ecritureComptable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(ecritureComptableDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the EcritureComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEcritureComptableWithPatch() throws Exception {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecritureComptable using partial update
        EcritureComptable partialUpdatedEcritureComptable = new EcritureComptable();
        partialUpdatedEcritureComptable.setId(ecritureComptable.getId());

        partialUpdatedEcritureComptable.dateComptable(UPDATED_DATE_COMPTABLE).libelle(UPDATED_LIBELLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEcritureComptable.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedEcritureComptable))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the EcritureComptable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEcritureComptableUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEcritureComptable, ecritureComptable),
            getPersistedEcritureComptable(ecritureComptable)
        );
    }

    @Test
    void fullUpdateEcritureComptableWithPatch() throws Exception {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ecritureComptable using partial update
        EcritureComptable partialUpdatedEcritureComptable = new EcritureComptable();
        partialUpdatedEcritureComptable.setId(ecritureComptable.getId());

        partialUpdatedEcritureComptable
            .dateComptable(UPDATED_DATE_COMPTABLE)
            .numeroPiece(UPDATED_NUMERO_PIECE)
            .libelle(UPDATED_LIBELLE)
            .referenceExterne(UPDATED_REFERENCE_EXTERNE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEcritureComptable.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedEcritureComptable))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the EcritureComptable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEcritureComptableUpdatableFieldsEquals(
            partialUpdatedEcritureComptable,
            getPersistedEcritureComptable(partialUpdatedEcritureComptable)
        );
    }

    @Test
    void patchNonExistingEcritureComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecritureComptable.setId(longCount.incrementAndGet());

        // Create the EcritureComptable
        EcritureComptableDTO ecritureComptableDTO = ecritureComptableMapper.toDto(ecritureComptable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, ecritureComptableDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ecritureComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EcritureComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEcritureComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecritureComptable.setId(longCount.incrementAndGet());

        // Create the EcritureComptable
        EcritureComptableDTO ecritureComptableDTO = ecritureComptableMapper.toDto(ecritureComptable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ecritureComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EcritureComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEcritureComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ecritureComptable.setId(longCount.incrementAndGet());

        // Create the EcritureComptable
        EcritureComptableDTO ecritureComptableDTO = ecritureComptableMapper.toDto(ecritureComptable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(ecritureComptableDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the EcritureComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEcritureComptable() {
        // Initialize the database
        insertedEcritureComptable = ecritureComptableRepository.save(ecritureComptable).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ecritureComptable
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, ecritureComptable.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ecritureComptableRepository.count().block();
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

    protected EcritureComptable getPersistedEcritureComptable(EcritureComptable ecritureComptable) {
        return ecritureComptableRepository.findById(ecritureComptable.getId()).block();
    }

    protected void assertPersistedEcritureComptableToMatchAllProperties(EcritureComptable expectedEcritureComptable) {
        // Test fails because reactive api returns an empty object instead of null
        // assertEcritureComptableAllPropertiesEquals(expectedEcritureComptable, getPersistedEcritureComptable(expectedEcritureComptable));
        assertEcritureComptableUpdatableFieldsEquals(expectedEcritureComptable, getPersistedEcritureComptable(expectedEcritureComptable));
    }

    protected void assertPersistedEcritureComptableToMatchUpdatableProperties(EcritureComptable expectedEcritureComptable) {
        // Test fails because reactive api returns an empty object instead of null
        // assertEcritureComptableAllUpdatablePropertiesEquals(expectedEcritureComptable, getPersistedEcritureComptable(expectedEcritureComptable));
        assertEcritureComptableUpdatableFieldsEquals(expectedEcritureComptable, getPersistedEcritureComptable(expectedEcritureComptable));
    }
}
