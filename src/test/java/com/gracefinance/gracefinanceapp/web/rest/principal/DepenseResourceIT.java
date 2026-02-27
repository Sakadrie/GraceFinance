package com.gracefinance.gracefinanceapp.web.rest.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.DepenseAsserts.*;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefinance.gracefinanceapp.IntegrationTest;
import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.domain.principal.Depense;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.principal.CaisseRepository;
import com.gracefinance.gracefinanceapp.repository.principal.DepenseRepository;
import com.gracefinance.gracefinanceapp.repository.principal.EntiteFinanciereRepository;
import com.gracefinance.gracefinanceapp.repository.referentiel.CategorieRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.DepenseDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.DepenseMapper;
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
 * Integration tests for the {@link DepenseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DepenseResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEPENSE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEPENSE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_DEPENSE = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_MONTANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT = new BigDecimal(2);
    private static final BigDecimal SMALLER_MONTANT = new BigDecimal(1 - 1);

    private static final String DEFAULT_MOTIF = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_PIECE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_PIECE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDER_PAR = "AAAAAAAAAA";
    private static final String UPDATED_VALIDER_PAR = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_VALIDATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_VALIDATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/depenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DepenseRepository depenseRepository;

    @Autowired
    private DepenseMapper depenseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Depense depense;

    private Depense insertedDepense;

    @Autowired
    private EntiteFinanciereRepository entiteFinanciereRepository;

    @Autowired
    private CaisseRepository caisseRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depense createEntity(EntityManager em) {
        Depense depense = new Depense()
            .code(DEFAULT_CODE)
            .dateDepense(DEFAULT_DATE_DEPENSE)
            .montant(DEFAULT_MONTANT)
            .motif(DEFAULT_MOTIF)
            .referencePiece(DEFAULT_REFERENCE_PIECE)
            .statut(DEFAULT_STATUT)
            .validerPar(DEFAULT_VALIDER_PAR)
            .dateValidation(DEFAULT_DATE_VALIDATION);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createEntity()).block();
        depense.setEntiteFinanciere(entiteFinanciere);
        // Add required entity
        Caisse caisse;
        caisse = em.insert(CaisseResourceIT.createEntity(em)).block();
        depense.setCaisse(caisse);
        // Add required entity
        Categorie categorie;
        categorie = em.insert(CategorieResourceIT.createEntity(em)).block();
        depense.setCategorie(categorie);
        return depense;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Depense createUpdatedEntity(EntityManager em) {
        Depense updatedDepense = new Depense()
            .code(UPDATED_CODE)
            .dateDepense(UPDATED_DATE_DEPENSE)
            .montant(UPDATED_MONTANT)
            .motif(UPDATED_MOTIF)
            .referencePiece(UPDATED_REFERENCE_PIECE)
            .statut(UPDATED_STATUT)
            .validerPar(UPDATED_VALIDER_PAR)
            .dateValidation(UPDATED_DATE_VALIDATION);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createUpdatedEntity()).block();
        updatedDepense.setEntiteFinanciere(entiteFinanciere);
        // Add required entity
        Caisse caisse;
        caisse = em.insert(CaisseResourceIT.createUpdatedEntity(em)).block();
        updatedDepense.setCaisse(caisse);
        // Add required entity
        Categorie categorie;
        categorie = em.insert(CategorieResourceIT.createUpdatedEntity(em)).block();
        updatedDepense.setCategorie(categorie);
        return updatedDepense;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Depense.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        EntiteFinanciereResourceIT.deleteEntities(em);
        CaisseResourceIT.deleteEntities(em);
        CategorieResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        depense = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedDepense != null) {
            depenseRepository.delete(insertedDepense).block();
            insertedDepense = null;
        }
        deleteEntities(em);
    }

    @Test
    void createDepense() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Depense
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);
        var returnedDepenseDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(DepenseDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Depense in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDepense = depenseMapper.toEntity(returnedDepenseDTO);
        assertDepenseUpdatableFieldsEquals(returnedDepense, getPersistedDepense(returnedDepense));

        insertedDepense = returnedDepense;
    }

    @Test
    void createDepenseWithExistingId() throws Exception {
        // Create the Depense with an existing ID
        depense.setId(1L);
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Depense in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        depense.setCode(null);

        // Create the Depense, which fails.
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDateDepenseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        depense.setDateDepense(null);

        // Create the Depense, which fails.
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkMontantIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        depense.setMontant(null);

        // Create the Depense, which fails.
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkMotifIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        depense.setMotif(null);

        // Create the Depense, which fails.
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStatutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        depense.setStatut(null);

        // Create the Depense, which fails.
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllDepenses() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList
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
            .value(hasItem(depense.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].dateDepense")
            .value(hasItem(DEFAULT_DATE_DEPENSE.toString()))
            .jsonPath("$.[*].montant")
            .value(hasItem(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.[*].motif")
            .value(hasItem(DEFAULT_MOTIF))
            .jsonPath("$.[*].referencePiece")
            .value(hasItem(DEFAULT_REFERENCE_PIECE))
            .jsonPath("$.[*].statut")
            .value(hasItem(DEFAULT_STATUT))
            .jsonPath("$.[*].validerPar")
            .value(hasItem(DEFAULT_VALIDER_PAR))
            .jsonPath("$.[*].dateValidation")
            .value(hasItem(DEFAULT_DATE_VALIDATION.toString()));
    }

    @Test
    void getDepense() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get the depense
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, depense.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(depense.getId().intValue()))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.dateDepense")
            .value(is(DEFAULT_DATE_DEPENSE.toString()))
            .jsonPath("$.montant")
            .value(is(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.motif")
            .value(is(DEFAULT_MOTIF))
            .jsonPath("$.referencePiece")
            .value(is(DEFAULT_REFERENCE_PIECE))
            .jsonPath("$.statut")
            .value(is(DEFAULT_STATUT))
            .jsonPath("$.validerPar")
            .value(is(DEFAULT_VALIDER_PAR))
            .jsonPath("$.dateValidation")
            .value(is(DEFAULT_DATE_VALIDATION.toString()));
    }

    @Test
    void getDepensesByIdFiltering() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        Long id = depense.getId();

        defaultDepenseFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDepenseFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDepenseFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllDepensesByCodeIsEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where code equals to
        defaultDepenseFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    void getAllDepensesByCodeIsInShouldWork() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where code in
        defaultDepenseFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    void getAllDepensesByCodeIsNullOrNotNull() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where code is not null
        defaultDepenseFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    void getAllDepensesByCodeContainsSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where code contains
        defaultDepenseFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    void getAllDepensesByCodeNotContainsSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where code does not contain
        defaultDepenseFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    void getAllDepensesByDateDepenseIsEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where dateDepense equals to
        defaultDepenseFiltering("dateDepense.equals=" + DEFAULT_DATE_DEPENSE, "dateDepense.equals=" + UPDATED_DATE_DEPENSE);
    }

    @Test
    void getAllDepensesByDateDepenseIsInShouldWork() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where dateDepense in
        defaultDepenseFiltering(
            "dateDepense.in=" + DEFAULT_DATE_DEPENSE + "," + UPDATED_DATE_DEPENSE,
            "dateDepense.in=" + UPDATED_DATE_DEPENSE
        );
    }

    @Test
    void getAllDepensesByDateDepenseIsNullOrNotNull() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where dateDepense is not null
        defaultDepenseFiltering("dateDepense.specified=true", "dateDepense.specified=false");
    }

    @Test
    void getAllDepensesByDateDepenseIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where dateDepense is greater than or equal to
        defaultDepenseFiltering(
            "dateDepense.greaterThanOrEqual=" + DEFAULT_DATE_DEPENSE,
            "dateDepense.greaterThanOrEqual=" + UPDATED_DATE_DEPENSE
        );
    }

    @Test
    void getAllDepensesByDateDepenseIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where dateDepense is less than or equal to
        defaultDepenseFiltering(
            "dateDepense.lessThanOrEqual=" + DEFAULT_DATE_DEPENSE,
            "dateDepense.lessThanOrEqual=" + SMALLER_DATE_DEPENSE
        );
    }

    @Test
    void getAllDepensesByDateDepenseIsLessThanSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where dateDepense is less than
        defaultDepenseFiltering("dateDepense.lessThan=" + UPDATED_DATE_DEPENSE, "dateDepense.lessThan=" + DEFAULT_DATE_DEPENSE);
    }

    @Test
    void getAllDepensesByDateDepenseIsGreaterThanSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where dateDepense is greater than
        defaultDepenseFiltering("dateDepense.greaterThan=" + SMALLER_DATE_DEPENSE, "dateDepense.greaterThan=" + DEFAULT_DATE_DEPENSE);
    }

    @Test
    void getAllDepensesByMontantIsEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where montant equals to
        defaultDepenseFiltering("montant.equals=" + DEFAULT_MONTANT, "montant.equals=" + UPDATED_MONTANT);
    }

    @Test
    void getAllDepensesByMontantIsInShouldWork() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where montant in
        defaultDepenseFiltering("montant.in=" + DEFAULT_MONTANT + "," + UPDATED_MONTANT, "montant.in=" + UPDATED_MONTANT);
    }

    @Test
    void getAllDepensesByMontantIsNullOrNotNull() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where montant is not null
        defaultDepenseFiltering("montant.specified=true", "montant.specified=false");
    }

    @Test
    void getAllDepensesByMontantIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where montant is greater than or equal to
        defaultDepenseFiltering("montant.greaterThanOrEqual=" + DEFAULT_MONTANT, "montant.greaterThanOrEqual=" + UPDATED_MONTANT);
    }

    @Test
    void getAllDepensesByMontantIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where montant is less than or equal to
        defaultDepenseFiltering("montant.lessThanOrEqual=" + DEFAULT_MONTANT, "montant.lessThanOrEqual=" + SMALLER_MONTANT);
    }

    @Test
    void getAllDepensesByMontantIsLessThanSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where montant is less than
        defaultDepenseFiltering("montant.lessThan=" + UPDATED_MONTANT, "montant.lessThan=" + DEFAULT_MONTANT);
    }

    @Test
    void getAllDepensesByMontantIsGreaterThanSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where montant is greater than
        defaultDepenseFiltering("montant.greaterThan=" + SMALLER_MONTANT, "montant.greaterThan=" + DEFAULT_MONTANT);
    }

    @Test
    void getAllDepensesByMotifIsEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where motif equals to
        defaultDepenseFiltering("motif.equals=" + DEFAULT_MOTIF, "motif.equals=" + UPDATED_MOTIF);
    }

    @Test
    void getAllDepensesByMotifIsInShouldWork() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where motif in
        defaultDepenseFiltering("motif.in=" + DEFAULT_MOTIF + "," + UPDATED_MOTIF, "motif.in=" + UPDATED_MOTIF);
    }

    @Test
    void getAllDepensesByMotifIsNullOrNotNull() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where motif is not null
        defaultDepenseFiltering("motif.specified=true", "motif.specified=false");
    }

    @Test
    void getAllDepensesByMotifContainsSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where motif contains
        defaultDepenseFiltering("motif.contains=" + DEFAULT_MOTIF, "motif.contains=" + UPDATED_MOTIF);
    }

    @Test
    void getAllDepensesByMotifNotContainsSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where motif does not contain
        defaultDepenseFiltering("motif.doesNotContain=" + UPDATED_MOTIF, "motif.doesNotContain=" + DEFAULT_MOTIF);
    }

    @Test
    void getAllDepensesByReferencePieceIsEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where referencePiece equals to
        defaultDepenseFiltering("referencePiece.equals=" + DEFAULT_REFERENCE_PIECE, "referencePiece.equals=" + UPDATED_REFERENCE_PIECE);
    }

    @Test
    void getAllDepensesByReferencePieceIsInShouldWork() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where referencePiece in
        defaultDepenseFiltering(
            "referencePiece.in=" + DEFAULT_REFERENCE_PIECE + "," + UPDATED_REFERENCE_PIECE,
            "referencePiece.in=" + UPDATED_REFERENCE_PIECE
        );
    }

    @Test
    void getAllDepensesByReferencePieceIsNullOrNotNull() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where referencePiece is not null
        defaultDepenseFiltering("referencePiece.specified=true", "referencePiece.specified=false");
    }

    @Test
    void getAllDepensesByReferencePieceContainsSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where referencePiece contains
        defaultDepenseFiltering("referencePiece.contains=" + DEFAULT_REFERENCE_PIECE, "referencePiece.contains=" + UPDATED_REFERENCE_PIECE);
    }

    @Test
    void getAllDepensesByReferencePieceNotContainsSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where referencePiece does not contain
        defaultDepenseFiltering(
            "referencePiece.doesNotContain=" + UPDATED_REFERENCE_PIECE,
            "referencePiece.doesNotContain=" + DEFAULT_REFERENCE_PIECE
        );
    }

    @Test
    void getAllDepensesByStatutIsEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where statut equals to
        defaultDepenseFiltering("statut.equals=" + DEFAULT_STATUT, "statut.equals=" + UPDATED_STATUT);
    }

    @Test
    void getAllDepensesByStatutIsInShouldWork() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where statut in
        defaultDepenseFiltering("statut.in=" + DEFAULT_STATUT + "," + UPDATED_STATUT, "statut.in=" + UPDATED_STATUT);
    }

    @Test
    void getAllDepensesByStatutIsNullOrNotNull() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where statut is not null
        defaultDepenseFiltering("statut.specified=true", "statut.specified=false");
    }

    @Test
    void getAllDepensesByStatutContainsSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where statut contains
        defaultDepenseFiltering("statut.contains=" + DEFAULT_STATUT, "statut.contains=" + UPDATED_STATUT);
    }

    @Test
    void getAllDepensesByStatutNotContainsSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where statut does not contain
        defaultDepenseFiltering("statut.doesNotContain=" + UPDATED_STATUT, "statut.doesNotContain=" + DEFAULT_STATUT);
    }

    @Test
    void getAllDepensesByValiderParIsEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where validerPar equals to
        defaultDepenseFiltering("validerPar.equals=" + DEFAULT_VALIDER_PAR, "validerPar.equals=" + UPDATED_VALIDER_PAR);
    }

    @Test
    void getAllDepensesByValiderParIsInShouldWork() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where validerPar in
        defaultDepenseFiltering("validerPar.in=" + DEFAULT_VALIDER_PAR + "," + UPDATED_VALIDER_PAR, "validerPar.in=" + UPDATED_VALIDER_PAR);
    }

    @Test
    void getAllDepensesByValiderParIsNullOrNotNull() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where validerPar is not null
        defaultDepenseFiltering("validerPar.specified=true", "validerPar.specified=false");
    }

    @Test
    void getAllDepensesByValiderParContainsSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where validerPar contains
        defaultDepenseFiltering("validerPar.contains=" + DEFAULT_VALIDER_PAR, "validerPar.contains=" + UPDATED_VALIDER_PAR);
    }

    @Test
    void getAllDepensesByValiderParNotContainsSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where validerPar does not contain
        defaultDepenseFiltering("validerPar.doesNotContain=" + UPDATED_VALIDER_PAR, "validerPar.doesNotContain=" + DEFAULT_VALIDER_PAR);
    }

    @Test
    void getAllDepensesByDateValidationIsEqualToSomething() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where dateValidation equals to
        defaultDepenseFiltering("dateValidation.equals=" + DEFAULT_DATE_VALIDATION, "dateValidation.equals=" + UPDATED_DATE_VALIDATION);
    }

    @Test
    void getAllDepensesByDateValidationIsInShouldWork() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where dateValidation in
        defaultDepenseFiltering(
            "dateValidation.in=" + DEFAULT_DATE_VALIDATION + "," + UPDATED_DATE_VALIDATION,
            "dateValidation.in=" + UPDATED_DATE_VALIDATION
        );
    }

    @Test
    void getAllDepensesByDateValidationIsNullOrNotNull() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        // Get all the depenseList where dateValidation is not null
        defaultDepenseFiltering("dateValidation.specified=true", "dateValidation.specified=false");
    }

    @Test
    void getAllDepensesByEntiteFinanciereIsEqualToSomething() {
        EntiteFinanciere entiteFinanciere = EntiteFinanciereResourceIT.createEntity();
        entiteFinanciereRepository.save(entiteFinanciere).block();
        Long entiteFinanciereId = entiteFinanciere.getId();
        depense.setEntiteFinanciereId(entiteFinanciereId);
        insertedDepense = depenseRepository.save(depense).block();
        // Get all the depenseList where entiteFinanciere equals to entiteFinanciereId
        defaultDepenseShouldBeFound("entiteFinanciereId.equals=" + entiteFinanciereId);

        // Get all the depenseList where entiteFinanciere equals to (entiteFinanciereId + 1)
        defaultDepenseShouldNotBeFound("entiteFinanciereId.equals=" + (entiteFinanciereId + 1));
    }

    @Test
    void getAllDepensesByCaisseIsEqualToSomething() {
        Caisse caisse = CaisseResourceIT.createEntity(em);
        caisseRepository.save(caisse).block();
        Long caisseId = caisse.getId();
        depense.setCaisseId(caisseId);
        insertedDepense = depenseRepository.save(depense).block();
        // Get all the depenseList where caisse equals to caisseId
        defaultDepenseShouldBeFound("caisseId.equals=" + caisseId);

        // Get all the depenseList where caisse equals to (caisseId + 1)
        defaultDepenseShouldNotBeFound("caisseId.equals=" + (caisseId + 1));
    }

    @Test
    void getAllDepensesByCategorieIsEqualToSomething() {
        Categorie categorie = CategorieResourceIT.createEntity(em);
        categorieRepository.save(categorie).block();
        Long categorieId = categorie.getId();
        depense.setCategorieId(categorieId);
        insertedDepense = depenseRepository.save(depense).block();
        // Get all the depenseList where categorie equals to categorieId
        defaultDepenseShouldBeFound("categorieId.equals=" + categorieId);

        // Get all the depenseList where categorie equals to (categorieId + 1)
        defaultDepenseShouldNotBeFound("categorieId.equals=" + (categorieId + 1));
    }

    private void defaultDepenseFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultDepenseShouldBeFound(shouldBeFound);
        defaultDepenseShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepenseShouldBeFound(String filter) {
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
            .value(hasItem(depense.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].dateDepense")
            .value(hasItem(DEFAULT_DATE_DEPENSE.toString()))
            .jsonPath("$.[*].montant")
            .value(hasItem(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.[*].motif")
            .value(hasItem(DEFAULT_MOTIF))
            .jsonPath("$.[*].referencePiece")
            .value(hasItem(DEFAULT_REFERENCE_PIECE))
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
    private void defaultDepenseShouldNotBeFound(String filter) {
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
    void getNonExistingDepense() {
        // Get the depense
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDepense() throws Exception {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the depense
        Depense updatedDepense = depenseRepository.findById(depense.getId()).block();
        updatedDepense
            .code(UPDATED_CODE)
            .dateDepense(UPDATED_DATE_DEPENSE)
            .montant(UPDATED_MONTANT)
            .motif(UPDATED_MOTIF)
            .referencePiece(UPDATED_REFERENCE_PIECE)
            .statut(UPDATED_STATUT)
            .validerPar(UPDATED_VALIDER_PAR)
            .dateValidation(UPDATED_DATE_VALIDATION);
        DepenseDTO depenseDTO = depenseMapper.toDto(updatedDepense);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, depenseDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Depense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDepenseToMatchAllProperties(updatedDepense);
    }

    @Test
    void putNonExistingDepense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depense.setId(longCount.incrementAndGet());

        // Create the Depense
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, depenseDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Depense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDepense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depense.setId(longCount.incrementAndGet());

        // Create the Depense
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Depense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDepense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depense.setId(longCount.incrementAndGet());

        // Create the Depense
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Depense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDepenseWithPatch() throws Exception {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the depense using partial update
        Depense partialUpdatedDepense = new Depense();
        partialUpdatedDepense.setId(depense.getId());

        partialUpdatedDepense.code(UPDATED_CODE).statut(UPDATED_STATUT).validerPar(UPDATED_VALIDER_PAR);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDepense.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedDepense))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Depense in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepenseUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDepense, depense), getPersistedDepense(depense));
    }

    @Test
    void fullUpdateDepenseWithPatch() throws Exception {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the depense using partial update
        Depense partialUpdatedDepense = new Depense();
        partialUpdatedDepense.setId(depense.getId());

        partialUpdatedDepense
            .code(UPDATED_CODE)
            .dateDepense(UPDATED_DATE_DEPENSE)
            .montant(UPDATED_MONTANT)
            .motif(UPDATED_MOTIF)
            .referencePiece(UPDATED_REFERENCE_PIECE)
            .statut(UPDATED_STATUT)
            .validerPar(UPDATED_VALIDER_PAR)
            .dateValidation(UPDATED_DATE_VALIDATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDepense.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedDepense))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Depense in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDepenseUpdatableFieldsEquals(partialUpdatedDepense, getPersistedDepense(partialUpdatedDepense));
    }

    @Test
    void patchNonExistingDepense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depense.setId(longCount.incrementAndGet());

        // Create the Depense
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, depenseDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Depense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDepense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depense.setId(longCount.incrementAndGet());

        // Create the Depense
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Depense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDepense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        depense.setId(longCount.incrementAndGet());

        // Create the Depense
        DepenseDTO depenseDTO = depenseMapper.toDto(depense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(depenseDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Depense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDepense() {
        // Initialize the database
        insertedDepense = depenseRepository.save(depense).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the depense
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, depense.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return depenseRepository.count().block();
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

    protected Depense getPersistedDepense(Depense depense) {
        return depenseRepository.findById(depense.getId()).block();
    }

    protected void assertPersistedDepenseToMatchAllProperties(Depense expectedDepense) {
        // Test fails because reactive api returns an empty object instead of null
        // assertDepenseAllPropertiesEquals(expectedDepense, getPersistedDepense(expectedDepense));
        assertDepenseUpdatableFieldsEquals(expectedDepense, getPersistedDepense(expectedDepense));
    }

    protected void assertPersistedDepenseToMatchUpdatableProperties(Depense expectedDepense) {
        // Test fails because reactive api returns an empty object instead of null
        // assertDepenseAllUpdatablePropertiesEquals(expectedDepense, getPersistedDepense(expectedDepense));
        assertDepenseUpdatableFieldsEquals(expectedDepense, getPersistedDepense(expectedDepense));
    }
}
