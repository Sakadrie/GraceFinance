package com.gracefinance.gracefinanceapp.web.rest.referentiel;

import static com.gracefinance.gracefinanceapp.domain.referentiel.TransfertAsserts.assertTransfertUpdatableFieldsEquals;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefinance.gracefinanceapp.IntegrationTest;
import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.domain.referentiel.Transfert;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.principal.CaisseRepository;
import com.gracefinance.gracefinanceapp.repository.principal.EntiteFinanciereRepository;
import com.gracefinance.gracefinanceapp.repository.referentiel.TransfertRepository;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.TransfertDTO;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.TransfertMapper;
import com.gracefinance.gracefinanceapp.web.rest.principal.CaisseResourceIT;
import com.gracefinance.gracefinanceapp.web.rest.principal.EntiteFinanciereResourceIT;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link TransfertResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TransfertResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_TRANSFERT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TRANSFERT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_TRANSFERT = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_MONTANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT = new BigDecimal(2);
    private static final BigDecimal SMALLER_MONTANT = new BigDecimal(1 - 1);

    private static final String DEFAULT_MOTIF = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_TRANSFERT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_TRANSFERT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDER_PAR = "AAAAAAAAAA";
    private static final String UPDATED_VALIDER_PAR = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_VALIDATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_VALIDATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/transferts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TransfertRepository transfertRepository;

    @Autowired
    private TransfertMapper transfertMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Transfert transfert;

    private Transfert insertedTransfert;

    @Autowired
    private EntiteFinanciereRepository entiteFinanciereRepository;

    @Autowired
    private CaisseRepository caisseRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfert createEntity(EntityManager em) {
        Transfert transfert = new Transfert()
            .code(DEFAULT_CODE)
            .dateTransfert(DEFAULT_DATE_TRANSFERT)
            .montant(DEFAULT_MONTANT)
            .motif(DEFAULT_MOTIF)
            .typeTransfert(DEFAULT_TYPE_TRANSFERT)
            .statut(DEFAULT_STATUT)
            .validerPar(DEFAULT_VALIDER_PAR)
            .dateValidation(DEFAULT_DATE_VALIDATION);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createEntity()).block();
        transfert.setEntiteFinanciereSource(entiteFinanciere);
        // Add required entity
        Caisse caisse;
        caisse = em.insert(CaisseResourceIT.createEntity(em)).block();
        transfert.setCaisseSource(caisse);
        // Add required entity
        transfert.setCaisseDestination(caisse);
        return transfert;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfert createUpdatedEntity(EntityManager em) {
        Transfert updatedTransfert = new Transfert()
            .code(UPDATED_CODE)
            .dateTransfert(UPDATED_DATE_TRANSFERT)
            .montant(UPDATED_MONTANT)
            .motif(UPDATED_MOTIF)
            .typeTransfert(UPDATED_TYPE_TRANSFERT)
            .statut(UPDATED_STATUT)
            .validerPar(UPDATED_VALIDER_PAR)
            .dateValidation(UPDATED_DATE_VALIDATION);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createUpdatedEntity()).block();
        updatedTransfert.setEntiteFinanciereSource(entiteFinanciere);
        // Add required entity
        Caisse caisse;
        caisse = em.insert(CaisseResourceIT.createUpdatedEntity(em)).block();
        updatedTransfert.setCaisseSource(caisse);
        // Add required entity
        updatedTransfert.setCaisseDestination(caisse);
        return updatedTransfert;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Transfert.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        EntiteFinanciereResourceIT.deleteEntities(em);
        CaisseResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        transfert = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedTransfert != null) {
            transfertRepository.delete(insertedTransfert).block();
            insertedTransfert = null;
        }
        deleteEntities(em);
    }

    @Test
    void createTransfert() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Transfert
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);
        var returnedTransfertDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(TransfertDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Transfert in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTransfert = transfertMapper.toEntity(returnedTransfertDTO);
        assertTransfertUpdatableFieldsEquals(returnedTransfert, getPersistedTransfert(returnedTransfert));

        insertedTransfert = returnedTransfert;
    }

    @Test
    void createTransfertWithExistingId() throws Exception {
        // Create the Transfert with an existing ID
        transfert.setId(1L);
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transfert in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transfert.setCode(null);

        // Create the Transfert, which fails.
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDateTransfertIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transfert.setDateTransfert(null);

        // Create the Transfert, which fails.
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkMontantIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transfert.setMontant(null);

        // Create the Transfert, which fails.
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeTransfertIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transfert.setTypeTransfert(null);

        // Create the Transfert, which fails.
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStatutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        transfert.setStatut(null);

        // Create the Transfert, which fails.
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllTransferts() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList
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
            .value(hasItem(transfert.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].dateTransfert")
            .value(hasItem(DEFAULT_DATE_TRANSFERT.toString()))
            .jsonPath("$.[*].montant")
            .value(hasItem(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.[*].motif")
            .value(hasItem(DEFAULT_MOTIF))
            .jsonPath("$.[*].typeTransfert")
            .value(hasItem(DEFAULT_TYPE_TRANSFERT))
            .jsonPath("$.[*].statut")
            .value(hasItem(DEFAULT_STATUT))
            .jsonPath("$.[*].validerPar")
            .value(hasItem(DEFAULT_VALIDER_PAR))
            .jsonPath("$.[*].dateValidation")
            .value(hasItem(DEFAULT_DATE_VALIDATION.toString()));
    }

    @Test
    void getTransfert() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get the transfert
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, transfert.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(transfert.getId().intValue()))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.dateTransfert")
            .value(is(DEFAULT_DATE_TRANSFERT.toString()))
            .jsonPath("$.montant")
            .value(is(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.motif")
            .value(is(DEFAULT_MOTIF))
            .jsonPath("$.typeTransfert")
            .value(is(DEFAULT_TYPE_TRANSFERT))
            .jsonPath("$.statut")
            .value(is(DEFAULT_STATUT))
            .jsonPath("$.validerPar")
            .value(is(DEFAULT_VALIDER_PAR))
            .jsonPath("$.dateValidation")
            .value(is(DEFAULT_DATE_VALIDATION.toString()));
    }

    @Test
    void getTransfertsByIdFiltering() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        Long id = transfert.getId();

        defaultTransfertFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultTransfertFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultTransfertFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllTransfertsByCodeIsEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where code equals to
        defaultTransfertFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    void getAllTransfertsByCodeIsInShouldWork() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where code in
        defaultTransfertFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    void getAllTransfertsByCodeIsNullOrNotNull() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where code is not null
        defaultTransfertFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    void getAllTransfertsByCodeContainsSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where code contains
        defaultTransfertFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    void getAllTransfertsByCodeNotContainsSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where code does not contain
        defaultTransfertFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    void getAllTransfertsByDateTransfertIsEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where dateTransfert equals to
        defaultTransfertFiltering("dateTransfert.equals=" + DEFAULT_DATE_TRANSFERT, "dateTransfert.equals=" + UPDATED_DATE_TRANSFERT);
    }

    @Test
    void getAllTransfertsByDateTransfertIsInShouldWork() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where dateTransfert in
        defaultTransfertFiltering(
            "dateTransfert.in=" + DEFAULT_DATE_TRANSFERT + "," + UPDATED_DATE_TRANSFERT,
            "dateTransfert.in=" + UPDATED_DATE_TRANSFERT
        );
    }

    @Test
    void getAllTransfertsByDateTransfertIsNullOrNotNull() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where dateTransfert is not null
        defaultTransfertFiltering("dateTransfert.specified=true", "dateTransfert.specified=false");
    }

    @Test
    void getAllTransfertsByDateTransfertIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where dateTransfert is greater than or equal to
        defaultTransfertFiltering(
            "dateTransfert.greaterThanOrEqual=" + DEFAULT_DATE_TRANSFERT,
            "dateTransfert.greaterThanOrEqual=" + UPDATED_DATE_TRANSFERT
        );
    }

    @Test
    void getAllTransfertsByDateTransfertIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where dateTransfert is less than or equal to
        defaultTransfertFiltering(
            "dateTransfert.lessThanOrEqual=" + DEFAULT_DATE_TRANSFERT,
            "dateTransfert.lessThanOrEqual=" + SMALLER_DATE_TRANSFERT
        );
    }

    @Test
    void getAllTransfertsByDateTransfertIsLessThanSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where dateTransfert is less than
        defaultTransfertFiltering("dateTransfert.lessThan=" + UPDATED_DATE_TRANSFERT, "dateTransfert.lessThan=" + DEFAULT_DATE_TRANSFERT);
    }

    @Test
    void getAllTransfertsByDateTransfertIsGreaterThanSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where dateTransfert is greater than
        defaultTransfertFiltering(
            "dateTransfert.greaterThan=" + SMALLER_DATE_TRANSFERT,
            "dateTransfert.greaterThan=" + DEFAULT_DATE_TRANSFERT
        );
    }

    @Test
    void getAllTransfertsByMontantIsEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where montant equals to
        defaultTransfertFiltering("montant.equals=" + DEFAULT_MONTANT, "montant.equals=" + UPDATED_MONTANT);
    }

    @Test
    void getAllTransfertsByMontantIsInShouldWork() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where montant in
        defaultTransfertFiltering("montant.in=" + DEFAULT_MONTANT + "," + UPDATED_MONTANT, "montant.in=" + UPDATED_MONTANT);
    }

    @Test
    void getAllTransfertsByMontantIsNullOrNotNull() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where montant is not null
        defaultTransfertFiltering("montant.specified=true", "montant.specified=false");
    }

    @Test
    void getAllTransfertsByMontantIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where montant is greater than or equal to
        defaultTransfertFiltering("montant.greaterThanOrEqual=" + DEFAULT_MONTANT, "montant.greaterThanOrEqual=" + UPDATED_MONTANT);
    }

    @Test
    void getAllTransfertsByMontantIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where montant is less than or equal to
        defaultTransfertFiltering("montant.lessThanOrEqual=" + DEFAULT_MONTANT, "montant.lessThanOrEqual=" + SMALLER_MONTANT);
    }

    @Test
    void getAllTransfertsByMontantIsLessThanSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where montant is less than
        defaultTransfertFiltering("montant.lessThan=" + UPDATED_MONTANT, "montant.lessThan=" + DEFAULT_MONTANT);
    }

    @Test
    void getAllTransfertsByMontantIsGreaterThanSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where montant is greater than
        defaultTransfertFiltering("montant.greaterThan=" + SMALLER_MONTANT, "montant.greaterThan=" + DEFAULT_MONTANT);
    }

    @Test
    void getAllTransfertsByMotifIsEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where motif equals to
        defaultTransfertFiltering("motif.equals=" + DEFAULT_MOTIF, "motif.equals=" + UPDATED_MOTIF);
    }

    @Test
    void getAllTransfertsByMotifIsInShouldWork() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where motif in
        defaultTransfertFiltering("motif.in=" + DEFAULT_MOTIF + "," + UPDATED_MOTIF, "motif.in=" + UPDATED_MOTIF);
    }

    @Test
    void getAllTransfertsByMotifIsNullOrNotNull() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where motif is not null
        defaultTransfertFiltering("motif.specified=true", "motif.specified=false");
    }

    @Test
    void getAllTransfertsByMotifContainsSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where motif contains
        defaultTransfertFiltering("motif.contains=" + DEFAULT_MOTIF, "motif.contains=" + UPDATED_MOTIF);
    }

    @Test
    void getAllTransfertsByMotifNotContainsSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where motif does not contain
        defaultTransfertFiltering("motif.doesNotContain=" + UPDATED_MOTIF, "motif.doesNotContain=" + DEFAULT_MOTIF);
    }

    @Test
    void getAllTransfertsByTypeTransfertIsEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where typeTransfert equals to
        defaultTransfertFiltering("typeTransfert.equals=" + DEFAULT_TYPE_TRANSFERT, "typeTransfert.equals=" + UPDATED_TYPE_TRANSFERT);
    }

    @Test
    void getAllTransfertsByTypeTransfertIsInShouldWork() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where typeTransfert in
        defaultTransfertFiltering(
            "typeTransfert.in=" + DEFAULT_TYPE_TRANSFERT + "," + UPDATED_TYPE_TRANSFERT,
            "typeTransfert.in=" + UPDATED_TYPE_TRANSFERT
        );
    }

    @Test
    void getAllTransfertsByTypeTransfertIsNullOrNotNull() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where typeTransfert is not null
        defaultTransfertFiltering("typeTransfert.specified=true", "typeTransfert.specified=false");
    }

    @Test
    void getAllTransfertsByTypeTransfertContainsSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where typeTransfert contains
        defaultTransfertFiltering("typeTransfert.contains=" + DEFAULT_TYPE_TRANSFERT, "typeTransfert.contains=" + UPDATED_TYPE_TRANSFERT);
    }

    @Test
    void getAllTransfertsByTypeTransfertNotContainsSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where typeTransfert does not contain
        defaultTransfertFiltering(
            "typeTransfert.doesNotContain=" + UPDATED_TYPE_TRANSFERT,
            "typeTransfert.doesNotContain=" + DEFAULT_TYPE_TRANSFERT
        );
    }

    @Test
    void getAllTransfertsByStatutIsEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where statut equals to
        defaultTransfertFiltering("statut.equals=" + DEFAULT_STATUT, "statut.equals=" + UPDATED_STATUT);
    }

    @Test
    void getAllTransfertsByStatutIsInShouldWork() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where statut in
        defaultTransfertFiltering("statut.in=" + DEFAULT_STATUT + "," + UPDATED_STATUT, "statut.in=" + UPDATED_STATUT);
    }

    @Test
    void getAllTransfertsByStatutIsNullOrNotNull() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where statut is not null
        defaultTransfertFiltering("statut.specified=true", "statut.specified=false");
    }

    @Test
    void getAllTransfertsByStatutContainsSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where statut contains
        defaultTransfertFiltering("statut.contains=" + DEFAULT_STATUT, "statut.contains=" + UPDATED_STATUT);
    }

    @Test
    void getAllTransfertsByStatutNotContainsSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where statut does not contain
        defaultTransfertFiltering("statut.doesNotContain=" + UPDATED_STATUT, "statut.doesNotContain=" + DEFAULT_STATUT);
    }

    @Test
    void getAllTransfertsByValiderParIsEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where validerPar equals to
        defaultTransfertFiltering("validerPar.equals=" + DEFAULT_VALIDER_PAR, "validerPar.equals=" + UPDATED_VALIDER_PAR);
    }

    @Test
    void getAllTransfertsByValiderParIsInShouldWork() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where validerPar in
        defaultTransfertFiltering(
            "validerPar.in=" + DEFAULT_VALIDER_PAR + "," + UPDATED_VALIDER_PAR,
            "validerPar.in=" + UPDATED_VALIDER_PAR
        );
    }

    @Test
    void getAllTransfertsByValiderParIsNullOrNotNull() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where validerPar is not null
        defaultTransfertFiltering("validerPar.specified=true", "validerPar.specified=false");
    }

    @Test
    void getAllTransfertsByValiderParContainsSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where validerPar contains
        defaultTransfertFiltering("validerPar.contains=" + DEFAULT_VALIDER_PAR, "validerPar.contains=" + UPDATED_VALIDER_PAR);
    }

    @Test
    void getAllTransfertsByValiderParNotContainsSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where validerPar does not contain
        defaultTransfertFiltering("validerPar.doesNotContain=" + UPDATED_VALIDER_PAR, "validerPar.doesNotContain=" + DEFAULT_VALIDER_PAR);
    }

    @Test
    void getAllTransfertsByDateValidationIsEqualToSomething() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where dateValidation equals to
        defaultTransfertFiltering("dateValidation.equals=" + DEFAULT_DATE_VALIDATION, "dateValidation.equals=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    void getAllTransfertsByDateValidationIsInShouldWork() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where dateValidation in
        defaultTransfertFiltering(
            "dateValidation.in=" + DEFAULT_DATE_VALIDATION + "," + UPDATED_DATE_VALIDATION,
            "dateValidation.in=" + UPDATED_DATE_VALIDATION
        );
    }

    @Test
    void getAllTransfertsByDateValidationIsNullOrNotNull() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        // Get all the transfertList where dateValidation is not null
        defaultTransfertFiltering("dateValidation.specified=true", "dateValidation.specified=false");
    }

    @Test
    void getAllTransfertsByEntiteFinanciereSourceIsEqualToSomething() {
        EntiteFinanciere entiteFinanciereSource = EntiteFinanciereResourceIT.createEntity();
        entiteFinanciereRepository.save(entiteFinanciereSource).block();
        Long entiteFinanciereSourceId = entiteFinanciereSource.getId();
        transfert.setEntiteFinanciereSourceId(entiteFinanciereSourceId);
        insertedTransfert = transfertRepository.save(transfert).block();
        // Get all the transfertList where entiteFinanciereSource equals to entiteFinanciereSourceId
        defaultTransfertShouldBeFound("entiteFinanciereSourceId.equals=" + entiteFinanciereSourceId);

        // Get all the transfertList where entiteFinanciereSource equals to (entiteFinanciereSourceId + 1)
        defaultTransfertShouldNotBeFound("entiteFinanciereSourceId.equals=" + (entiteFinanciereSourceId + 1));
    }

    @Test
    void getAllTransfertsByCaisseSourceIsEqualToSomething() {
        Caisse caisseSource = CaisseResourceIT.createEntity(em);
        caisseRepository.save(caisseSource).block();
        Long caisseSourceId = caisseSource.getId();
        transfert.setCaisseSourceId(caisseSourceId);
        insertedTransfert = transfertRepository.save(transfert).block();
        // Get all the transfertList where caisseSource equals to caisseSourceId
        defaultTransfertShouldBeFound("caisseSourceId.equals=" + caisseSourceId);

        // Get all the transfertList where caisseSource equals to (caisseSourceId + 1)
        defaultTransfertShouldNotBeFound("caisseSourceId.equals=" + (caisseSourceId + 1));
    }

    @Test
    void getAllTransfertsByCaisseDestinationIsEqualToSomething() {
        Caisse caisseDestination = CaisseResourceIT.createEntity(em);
        caisseRepository.save(caisseDestination).block();
        Long caisseDestinationId = caisseDestination.getId();
        transfert.setCaisseDestinationId(caisseDestinationId);
        insertedTransfert = transfertRepository.save(transfert).block();
        // Get all the transfertList where caisseDestination equals to caisseDestinationId
        defaultTransfertShouldBeFound("caisseDestinationId.equals=" + caisseDestinationId);

        // Get all the transfertList where caisseDestination equals to (caisseDestinationId + 1)
        defaultTransfertShouldNotBeFound("caisseDestinationId.equals=" + (caisseDestinationId + 1));
    }

    private void defaultTransfertFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultTransfertShouldBeFound(shouldBeFound);
        defaultTransfertShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransfertShouldBeFound(String filter) {
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
            .value(hasItem(transfert.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].dateTransfert")
            .value(hasItem(DEFAULT_DATE_TRANSFERT.toString()))
            .jsonPath("$.[*].montant")
            .value(hasItem(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.[*].motif")
            .value(hasItem(DEFAULT_MOTIF))
            .jsonPath("$.[*].typeTransfert")
            .value(hasItem(DEFAULT_TYPE_TRANSFERT))
            .jsonPath("$.[*].statut")
            .value(hasItem(DEFAULT_STATUT))
            .jsonPath("$.[*].validerPar")
            .value(hasItem(DEFAULT_VALIDER_PAR))
            .jsonPath("$.[*].dateValidation")
            .value(hasItem(DEFAULT_DATE_VALIDATION.toString()));

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
    private void defaultTransfertShouldNotBeFound(String filter) {
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
    void getNonExistingTransfert() {
        // Get the transfert
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingTransfert() throws Exception {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transfert
        Transfert updatedTransfert = transfertRepository.findById(transfert.getId()).block();
        updatedTransfert
            .code(UPDATED_CODE)
            .dateTransfert(UPDATED_DATE_TRANSFERT)
            .montant(UPDATED_MONTANT)
            .motif(UPDATED_MOTIF)
            .typeTransfert(UPDATED_TYPE_TRANSFERT)
            .statut(UPDATED_STATUT)
            .validerPar(UPDATED_VALIDER_PAR)
            .dateValidation(UPDATED_DATE_VALIDATION);
        TransfertDTO transfertDTO = transfertMapper.toDto(updatedTransfert);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, transfertDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Transfert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTransfertToMatchAllProperties(updatedTransfert);
    }

    @Test
    void putNonExistingTransfert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transfert.setId(longCount.incrementAndGet());

        // Create the Transfert
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, transfertDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transfert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTransfert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transfert.setId(longCount.incrementAndGet());

        // Create the Transfert
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transfert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTransfert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transfert.setId(longCount.incrementAndGet());

        // Create the Transfert
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Transfert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTransfertWithPatch() throws Exception {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transfert using partial update
        Transfert partialUpdatedTransfert = new Transfert();
        partialUpdatedTransfert.setId(transfert.getId());

        partialUpdatedTransfert
            .code(UPDATED_CODE)
            .dateTransfert(UPDATED_DATE_TRANSFERT)
            .montant(UPDATED_MONTANT)
            .typeTransfert(UPDATED_TYPE_TRANSFERT)
            .statut(UPDATED_STATUT)
            .validerPar(UPDATED_VALIDER_PAR);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTransfert.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedTransfert))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Transfert in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTransfertUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTransfert, transfert),
            getPersistedTransfert(transfert)
        );
    }

    @Test
    void fullUpdateTransfertWithPatch() throws Exception {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the transfert using partial update
        Transfert partialUpdatedTransfert = new Transfert();
        partialUpdatedTransfert.setId(transfert.getId());

        partialUpdatedTransfert
            .code(UPDATED_CODE)
            .dateTransfert(UPDATED_DATE_TRANSFERT)
            .montant(UPDATED_MONTANT)
            .motif(UPDATED_MOTIF)
            .typeTransfert(UPDATED_TYPE_TRANSFERT)
            .statut(UPDATED_STATUT)
            .validerPar(UPDATED_VALIDER_PAR)
            .dateValidation(UPDATED_DATE_VALIDATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTransfert.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedTransfert))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Transfert in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTransfertUpdatableFieldsEquals(partialUpdatedTransfert, getPersistedTransfert(partialUpdatedTransfert));
    }

    @Test
    void patchNonExistingTransfert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transfert.setId(longCount.incrementAndGet());

        // Create the Transfert
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, transfertDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transfert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTransfert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transfert.setId(longCount.incrementAndGet());

        // Create the Transfert
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Transfert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTransfert() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        transfert.setId(longCount.incrementAndGet());

        // Create the Transfert
        TransfertDTO transfertDTO = transfertMapper.toDto(transfert);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(transfertDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Transfert in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTransfert() {
        // Initialize the database
        insertedTransfert = transfertRepository.save(transfert).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the transfert
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, transfert.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return transfertRepository.count().block();
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

    protected Transfert getPersistedTransfert(Transfert transfert) {
        return transfertRepository.findById(transfert.getId()).block();
    }

    protected void assertPersistedTransfertToMatchAllProperties(Transfert expectedTransfert) {
        // Test fails because reactive api returns an empty object instead of null
        // assertTransfertAllPropertiesEquals(expectedTransfert, getPersistedTransfert(expectedTransfert));
        assertTransfertUpdatableFieldsEquals(expectedTransfert, getPersistedTransfert(expectedTransfert));
    }

    protected void assertPersistedTransfertToMatchUpdatableProperties(Transfert expectedTransfert) {
        // Test fails because reactive api returns an empty object instead of null
        // assertTransfertAllUpdatablePropertiesEquals(expectedTransfert, getPersistedTransfert(expectedTransfert));
        assertTransfertUpdatableFieldsEquals(expectedTransfert, getPersistedTransfert(expectedTransfert));
    }
}
