package com.gracefinance.gracefinanceapp.web.rest.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.RecetteAsserts.*;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefinance.gracefinanceapp.IntegrationTest;
import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.domain.principal.Recette;
import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.principal.CaisseRepository;
import com.gracefinance.gracefinanceapp.repository.principal.EntiteFinanciereRepository;
import com.gracefinance.gracefinanceapp.repository.principal.RecetteRepository;
import com.gracefinance.gracefinanceapp.repository.referentiel.CategorieRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.RecetteDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.RecetteMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link RecetteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class RecetteResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_RECETTE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RECETTE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_RECETTE = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_MONTANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT = new BigDecimal(2);
    private static final BigDecimal SMALLER_MONTANT = new BigDecimal(1 - 1);

    private static final String DEFAULT_TYPE_RECETTE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_RECETTE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ANONYME = false;
    private static final Boolean UPDATED_ANONYME = true;

    private static final String DEFAULT_MEMBRE_NOM = "AAAAAAAAAA";
    private static final String UPDATED_MEMBRE_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIF = "AAAAAAAAAA";
    private static final String UPDATED_MOTIF = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_PIECE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_PIECE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/recettes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RecetteRepository recetteRepository;

    @Autowired
    private RecetteMapper recetteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Recette recette;

    private Recette insertedRecette;

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
    public static Recette createEntity(EntityManager em) {
        Recette recette = new Recette()
            .code(DEFAULT_CODE)
            .dateRecette(DEFAULT_DATE_RECETTE)
            .montant(DEFAULT_MONTANT)
            .typeRecette(DEFAULT_TYPE_RECETTE)
            .anonyme(DEFAULT_ANONYME)
            .membreNom(DEFAULT_MEMBRE_NOM)
            .motif(DEFAULT_MOTIF)
            .referencePiece(DEFAULT_REFERENCE_PIECE)
            .statut(DEFAULT_STATUT);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createEntity()).block();
        recette.setEntiteFinanciere(entiteFinanciere);
        // Add required entity
        Caisse caisse;
        caisse = em.insert(CaisseResourceIT.createEntity(em)).block();
        recette.setCaisse(caisse);
        // Add required entity
        Categorie categorie;
        categorie = em.insert(CategorieResourceIT.createEntity(em)).block();
        recette.setCategorie(categorie);
        return recette;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recette createUpdatedEntity(EntityManager em) {
        Recette updatedRecette = new Recette()
            .code(UPDATED_CODE)
            .dateRecette(UPDATED_DATE_RECETTE)
            .montant(UPDATED_MONTANT)
            .typeRecette(UPDATED_TYPE_RECETTE)
            .anonyme(UPDATED_ANONYME)
            .membreNom(UPDATED_MEMBRE_NOM)
            .motif(UPDATED_MOTIF)
            .referencePiece(UPDATED_REFERENCE_PIECE)
            .statut(UPDATED_STATUT);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createUpdatedEntity()).block();
        updatedRecette.setEntiteFinanciere(entiteFinanciere);
        // Add required entity
        Caisse caisse;
        caisse = em.insert(CaisseResourceIT.createUpdatedEntity(em)).block();
        updatedRecette.setCaisse(caisse);
        // Add required entity
        Categorie categorie;
        categorie = em.insert(CategorieResourceIT.createUpdatedEntity(em)).block();
        updatedRecette.setCategorie(categorie);
        return updatedRecette;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Recette.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        EntiteFinanciereResourceIT.deleteEntities(em);
        CaisseResourceIT.deleteEntities(em);
        CategorieResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        recette = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedRecette != null) {
            recetteRepository.delete(insertedRecette).block();
            insertedRecette = null;
        }
        deleteEntities(em);
    }

    @Test
    void createRecette() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Recette
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);
        var returnedRecetteDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(RecetteDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Recette in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRecette = recetteMapper.toEntity(returnedRecetteDTO);
        assertRecetteUpdatableFieldsEquals(returnedRecette, getPersistedRecette(returnedRecette));

        insertedRecette = returnedRecette;
    }

    @Test
    void createRecetteWithExistingId() throws Exception {
        // Create the Recette with an existing ID
        recette.setId(1L);
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Recette in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recette.setCode(null);

        // Create the Recette, which fails.
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDateRecetteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recette.setDateRecette(null);

        // Create the Recette, which fails.
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkMontantIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recette.setMontant(null);

        // Create the Recette, which fails.
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeRecetteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recette.setTypeRecette(null);

        // Create the Recette, which fails.
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkAnonymeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recette.setAnonyme(null);

        // Create the Recette, which fails.
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkStatutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        recette.setStatut(null);

        // Create the Recette, which fails.
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllRecettes() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList
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
            .value(hasItem(recette.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].dateRecette")
            .value(hasItem(DEFAULT_DATE_RECETTE.toString()))
            .jsonPath("$.[*].montant")
            .value(hasItem(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.[*].typeRecette")
            .value(hasItem(DEFAULT_TYPE_RECETTE))
            .jsonPath("$.[*].anonyme")
            .value(hasItem(DEFAULT_ANONYME))
            .jsonPath("$.[*].membreNom")
            .value(hasItem(DEFAULT_MEMBRE_NOM))
            .jsonPath("$.[*].motif")
            .value(hasItem(DEFAULT_MOTIF))
            .jsonPath("$.[*].referencePiece")
            .value(hasItem(DEFAULT_REFERENCE_PIECE))
            .jsonPath("$.[*].statut")
            .value(hasItem(DEFAULT_STATUT));
    }

    @Test
    void getRecette() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get the recette
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, recette.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(recette.getId().intValue()))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.dateRecette")
            .value(is(DEFAULT_DATE_RECETTE.toString()))
            .jsonPath("$.montant")
            .value(is(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.typeRecette")
            .value(is(DEFAULT_TYPE_RECETTE))
            .jsonPath("$.anonyme")
            .value(is(DEFAULT_ANONYME))
            .jsonPath("$.membreNom")
            .value(is(DEFAULT_MEMBRE_NOM))
            .jsonPath("$.motif")
            .value(is(DEFAULT_MOTIF))
            .jsonPath("$.referencePiece")
            .value(is(DEFAULT_REFERENCE_PIECE))
            .jsonPath("$.statut")
            .value(is(DEFAULT_STATUT));
    }

    @Test
    void getRecettesByIdFiltering() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        Long id = recette.getId();

        defaultRecetteFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultRecetteFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultRecetteFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllRecettesByCodeIsEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where code equals to
        defaultRecetteFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    void getAllRecettesByCodeIsInShouldWork() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where code in
        defaultRecetteFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    void getAllRecettesByCodeIsNullOrNotNull() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where code is not null
        defaultRecetteFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    void getAllRecettesByCodeContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where code contains
        defaultRecetteFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    void getAllRecettesByCodeNotContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where code does not contain
        defaultRecetteFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    void getAllRecettesByDateRecetteIsEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where dateRecette equals to
        defaultRecetteFiltering("dateRecette.equals=" + DEFAULT_DATE_RECETTE, "dateRecette.equals=" + UPDATED_DATE_RECETTE);
    }

    @Test
    void getAllRecettesByDateRecetteIsInShouldWork() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where dateRecette in
        defaultRecetteFiltering(
            "dateRecette.in=" + DEFAULT_DATE_RECETTE + "," + UPDATED_DATE_RECETTE,
            "dateRecette.in=" + UPDATED_DATE_RECETTE
        );
    }

    @Test
    void getAllRecettesByDateRecetteIsNullOrNotNull() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where dateRecette is not null
        defaultRecetteFiltering("dateRecette.specified=true", "dateRecette.specified=false");
    }

    @Test
    void getAllRecettesByDateRecetteIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where dateRecette is greater than or equal to
        defaultRecetteFiltering(
            "dateRecette.greaterThanOrEqual=" + DEFAULT_DATE_RECETTE,
            "dateRecette.greaterThanOrEqual=" + UPDATED_DATE_RECETTE
        );
    }

    @Test
    void getAllRecettesByDateRecetteIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where dateRecette is less than or equal to
        defaultRecetteFiltering(
            "dateRecette.lessThanOrEqual=" + DEFAULT_DATE_RECETTE,
            "dateRecette.lessThanOrEqual=" + SMALLER_DATE_RECETTE
        );
    }

    @Test
    void getAllRecettesByDateRecetteIsLessThanSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where dateRecette is less than
        defaultRecetteFiltering("dateRecette.lessThan=" + UPDATED_DATE_RECETTE, "dateRecette.lessThan=" + DEFAULT_DATE_RECETTE);
    }

    @Test
    void getAllRecettesByDateRecetteIsGreaterThanSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where dateRecette is greater than
        defaultRecetteFiltering("dateRecette.greaterThan=" + SMALLER_DATE_RECETTE, "dateRecette.greaterThan=" + DEFAULT_DATE_RECETTE);
    }

    @Test
    void getAllRecettesByMontantIsEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where montant equals to
        defaultRecetteFiltering("montant.equals=" + DEFAULT_MONTANT, "montant.equals=" + UPDATED_MONTANT);
    }

    @Test
    void getAllRecettesByMontantIsInShouldWork() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where montant in
        defaultRecetteFiltering("montant.in=" + DEFAULT_MONTANT + "," + UPDATED_MONTANT, "montant.in=" + UPDATED_MONTANT);
    }

    @Test
    void getAllRecettesByMontantIsNullOrNotNull() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where montant is not null
        defaultRecetteFiltering("montant.specified=true", "montant.specified=false");
    }

    @Test
    void getAllRecettesByMontantIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where montant is greater than or equal to
        defaultRecetteFiltering("montant.greaterThanOrEqual=" + DEFAULT_MONTANT, "montant.greaterThanOrEqual=" + UPDATED_MONTANT);
    }

    @Test
    void getAllRecettesByMontantIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where montant is less than or equal to
        defaultRecetteFiltering("montant.lessThanOrEqual=" + DEFAULT_MONTANT, "montant.lessThanOrEqual=" + SMALLER_MONTANT);
    }

    @Test
    void getAllRecettesByMontantIsLessThanSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where montant is less than
        defaultRecetteFiltering("montant.lessThan=" + UPDATED_MONTANT, "montant.lessThan=" + DEFAULT_MONTANT);
    }

    @Test
    void getAllRecettesByMontantIsGreaterThanSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where montant is greater than
        defaultRecetteFiltering("montant.greaterThan=" + SMALLER_MONTANT, "montant.greaterThan=" + DEFAULT_MONTANT);
    }

    @Test
    void getAllRecettesByTypeRecetteIsEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where typeRecette equals to
        defaultRecetteFiltering("typeRecette.equals=" + DEFAULT_TYPE_RECETTE, "typeRecette.equals=" + UPDATED_TYPE_RECETTE);
    }

    @Test
    void getAllRecettesByTypeRecetteIsInShouldWork() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where typeRecette in
        defaultRecetteFiltering(
            "typeRecette.in=" + DEFAULT_TYPE_RECETTE + "," + UPDATED_TYPE_RECETTE,
            "typeRecette.in=" + UPDATED_TYPE_RECETTE
        );
    }

    @Test
    void getAllRecettesByTypeRecetteIsNullOrNotNull() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where typeRecette is not null
        defaultRecetteFiltering("typeRecette.specified=true", "typeRecette.specified=false");
    }

    @Test
    void getAllRecettesByTypeRecetteContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where typeRecette contains
        defaultRecetteFiltering("typeRecette.contains=" + DEFAULT_TYPE_RECETTE, "typeRecette.contains=" + UPDATED_TYPE_RECETTE);
    }

    @Test
    void getAllRecettesByTypeRecetteNotContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where typeRecette does not contain
        defaultRecetteFiltering("typeRecette.doesNotContain=" + UPDATED_TYPE_RECETTE, "typeRecette.doesNotContain=" + DEFAULT_TYPE_RECETTE);
    }

    @Test
    void getAllRecettesByAnonymeIsEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where anonyme equals to
        defaultRecetteFiltering("anonyme.equals=" + DEFAULT_ANONYME, "anonyme.equals=" + UPDATED_ANONYME);
    }

    @Test
    void getAllRecettesByAnonymeIsInShouldWork() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where anonyme in
        defaultRecetteFiltering("anonyme.in=" + DEFAULT_ANONYME + "," + UPDATED_ANONYME, "anonyme.in=" + UPDATED_ANONYME);
    }

    @Test
    void getAllRecettesByAnonymeIsNullOrNotNull() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where anonyme is not null
        defaultRecetteFiltering("anonyme.specified=true", "anonyme.specified=false");
    }

    @Test
    void getAllRecettesByMembreNomIsEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where membreNom equals to
        defaultRecetteFiltering("membreNom.equals=" + DEFAULT_MEMBRE_NOM, "membreNom.equals=" + UPDATED_MEMBRE_NOM);
    }

    @Test
    void getAllRecettesByMembreNomIsInShouldWork() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where membreNom in
        defaultRecetteFiltering("membreNom.in=" + DEFAULT_MEMBRE_NOM + "," + UPDATED_MEMBRE_NOM, "membreNom.in=" + UPDATED_MEMBRE_NOM);
    }

    @Test
    void getAllRecettesByMembreNomIsNullOrNotNull() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where membreNom is not null
        defaultRecetteFiltering("membreNom.specified=true", "membreNom.specified=false");
    }

    @Test
    void getAllRecettesByMembreNomContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where membreNom contains
        defaultRecetteFiltering("membreNom.contains=" + DEFAULT_MEMBRE_NOM, "membreNom.contains=" + UPDATED_MEMBRE_NOM);
    }

    @Test
    void getAllRecettesByMembreNomNotContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where membreNom does not contain
        defaultRecetteFiltering("membreNom.doesNotContain=" + UPDATED_MEMBRE_NOM, "membreNom.doesNotContain=" + DEFAULT_MEMBRE_NOM);
    }

    @Test
    void getAllRecettesByMotifIsEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where motif equals to
        defaultRecetteFiltering("motif.equals=" + DEFAULT_MOTIF, "motif.equals=" + UPDATED_MOTIF);
    }

    @Test
    void getAllRecettesByMotifIsInShouldWork() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where motif in
        defaultRecetteFiltering("motif.in=" + DEFAULT_MOTIF + "," + UPDATED_MOTIF, "motif.in=" + UPDATED_MOTIF);
    }

    @Test
    void getAllRecettesByMotifIsNullOrNotNull() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where motif is not null
        defaultRecetteFiltering("motif.specified=true", "motif.specified=false");
    }

    @Test
    void getAllRecettesByMotifContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where motif contains
        defaultRecetteFiltering("motif.contains=" + DEFAULT_MOTIF, "motif.contains=" + UPDATED_MOTIF);
    }

    @Test
    void getAllRecettesByMotifNotContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where motif does not contain
        defaultRecetteFiltering("motif.doesNotContain=" + UPDATED_MOTIF, "motif.doesNotContain=" + DEFAULT_MOTIF);
    }

    @Test
    void getAllRecettesByReferencePieceIsEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where referencePiece equals to
        defaultRecetteFiltering("referencePiece.equals=" + DEFAULT_REFERENCE_PIECE, "referencePiece.equals=" + UPDATED_REFERENCE_PIECE);
    }

    @Test
    void getAllRecettesByReferencePieceIsInShouldWork() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where referencePiece in
        defaultRecetteFiltering(
            "referencePiece.in=" + DEFAULT_REFERENCE_PIECE + "," + UPDATED_REFERENCE_PIECE,
            "referencePiece.in=" + UPDATED_REFERENCE_PIECE
        );
    }

    @Test
    void getAllRecettesByReferencePieceIsNullOrNotNull() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where referencePiece is not null
        defaultRecetteFiltering("referencePiece.specified=true", "referencePiece.specified=false");
    }

    @Test
    void getAllRecettesByReferencePieceContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where referencePiece contains
        defaultRecetteFiltering("referencePiece.contains=" + DEFAULT_REFERENCE_PIECE, "referencePiece.contains=" + UPDATED_REFERENCE_PIECE);
    }

    @Test
    void getAllRecettesByReferencePieceNotContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where referencePiece does not contain
        defaultRecetteFiltering(
            "referencePiece.doesNotContain=" + UPDATED_REFERENCE_PIECE,
            "referencePiece.doesNotContain=" + DEFAULT_REFERENCE_PIECE
        );
    }

    @Test
    void getAllRecettesByStatutIsEqualToSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where statut equals to
        defaultRecetteFiltering("statut.equals=" + DEFAULT_STATUT, "statut.equals=" + UPDATED_STATUT);
    }

    @Test
    void getAllRecettesByStatutIsInShouldWork() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where statut in
        defaultRecetteFiltering("statut.in=" + DEFAULT_STATUT + "," + UPDATED_STATUT, "statut.in=" + UPDATED_STATUT);
    }

    @Test
    void getAllRecettesByStatutIsNullOrNotNull() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where statut is not null
        defaultRecetteFiltering("statut.specified=true", "statut.specified=false");
    }

    @Test
    void getAllRecettesByStatutContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where statut contains
        defaultRecetteFiltering("statut.contains=" + DEFAULT_STATUT, "statut.contains=" + UPDATED_STATUT);
    }

    @Test
    void getAllRecettesByStatutNotContainsSomething() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        // Get all the recetteList where statut does not contain
        defaultRecetteFiltering("statut.doesNotContain=" + UPDATED_STATUT, "statut.doesNotContain=" + DEFAULT_STATUT);
    }

    @Test
    void getAllRecettesByEntiteFinanciereIsEqualToSomething() {
        EntiteFinanciere entiteFinanciere = EntiteFinanciereResourceIT.createEntity();
        entiteFinanciereRepository.save(entiteFinanciere).block();
        Long entiteFinanciereId = entiteFinanciere.getId();
        recette.setEntiteFinanciereId(entiteFinanciereId);
        insertedRecette = recetteRepository.save(recette).block();
        // Get all the recetteList where entiteFinanciere equals to entiteFinanciereId
        defaultRecetteShouldBeFound("entiteFinanciereId.equals=" + entiteFinanciereId);

        // Get all the recetteList where entiteFinanciere equals to (entiteFinanciereId + 1)
        defaultRecetteShouldNotBeFound("entiteFinanciereId.equals=" + (entiteFinanciereId + 1));
    }

    @Test
    void getAllRecettesByCaisseIsEqualToSomething() {
        Caisse caisse = CaisseResourceIT.createEntity(em);
        caisseRepository.save(caisse).block();
        Long caisseId = caisse.getId();
        recette.setCaisseId(caisseId);
        insertedRecette = recetteRepository.save(recette).block();
        // Get all the recetteList where caisse equals to caisseId
        defaultRecetteShouldBeFound("caisseId.equals=" + caisseId);

        // Get all the recetteList where caisse equals to (caisseId + 1)
        defaultRecetteShouldNotBeFound("caisseId.equals=" + (caisseId + 1));
    }

    @Test
    void getAllRecettesByCategorieIsEqualToSomething() {
        Categorie categorie = CategorieResourceIT.createEntity(em);
        categorieRepository.save(categorie).block();
        Long categorieId = categorie.getId();
        recette.setCategorieId(categorieId);
        insertedRecette = recetteRepository.save(recette).block();
        // Get all the recetteList where categorie equals to categorieId
        defaultRecetteShouldBeFound("categorieId.equals=" + categorieId);

        // Get all the recetteList where categorie equals to (categorieId + 1)
        defaultRecetteShouldNotBeFound("categorieId.equals=" + (categorieId + 1));
    }

    private void defaultRecetteFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultRecetteShouldBeFound(shouldBeFound);
        defaultRecetteShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRecetteShouldBeFound(String filter) {
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
            .value(hasItem(recette.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].dateRecette")
            .value(hasItem(DEFAULT_DATE_RECETTE.toString()))
            .jsonPath("$.[*].montant")
            .value(hasItem(sameNumber(DEFAULT_MONTANT)))
            .jsonPath("$.[*].typeRecette")
            .value(hasItem(DEFAULT_TYPE_RECETTE))
            .jsonPath("$.[*].anonyme")
            .value(hasItem(DEFAULT_ANONYME))
            .jsonPath("$.[*].membreNom")
            .value(hasItem(DEFAULT_MEMBRE_NOM))
            .jsonPath("$.[*].motif")
            .value(hasItem(DEFAULT_MOTIF))
            .jsonPath("$.[*].referencePiece")
            .value(hasItem(DEFAULT_REFERENCE_PIECE))
            .jsonPath("$.[*].statut")
            .value(hasItem(DEFAULT_STATUT));

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
    private void defaultRecetteShouldNotBeFound(String filter) {
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
    void getNonExistingRecette() {
        // Get the recette
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingRecette() throws Exception {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recette
        Recette updatedRecette = recetteRepository.findById(recette.getId()).block();
        updatedRecette
            .code(UPDATED_CODE)
            .dateRecette(UPDATED_DATE_RECETTE)
            .montant(UPDATED_MONTANT)
            .typeRecette(UPDATED_TYPE_RECETTE)
            .anonyme(UPDATED_ANONYME)
            .membreNom(UPDATED_MEMBRE_NOM)
            .motif(UPDATED_MOTIF)
            .referencePiece(UPDATED_REFERENCE_PIECE)
            .statut(UPDATED_STATUT);
        RecetteDTO recetteDTO = recetteMapper.toDto(updatedRecette);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, recetteDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Recette in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRecetteToMatchAllProperties(updatedRecette);
    }

    @Test
    void putNonExistingRecette() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recette.setId(longCount.incrementAndGet());

        // Create the Recette
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, recetteDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Recette in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRecette() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recette.setId(longCount.incrementAndGet());

        // Create the Recette
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Recette in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRecette() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recette.setId(longCount.incrementAndGet());

        // Create the Recette
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Recette in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRecetteWithPatch() throws Exception {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recette using partial update
        Recette partialUpdatedRecette = new Recette();
        partialUpdatedRecette.setId(recette.getId());

        partialUpdatedRecette.montant(UPDATED_MONTANT).typeRecette(UPDATED_TYPE_RECETTE).anonyme(UPDATED_ANONYME).motif(UPDATED_MOTIF);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRecette.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedRecette))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Recette in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecetteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRecette, recette), getPersistedRecette(recette));
    }

    @Test
    void fullUpdateRecetteWithPatch() throws Exception {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recette using partial update
        Recette partialUpdatedRecette = new Recette();
        partialUpdatedRecette.setId(recette.getId());

        partialUpdatedRecette
            .code(UPDATED_CODE)
            .dateRecette(UPDATED_DATE_RECETTE)
            .montant(UPDATED_MONTANT)
            .typeRecette(UPDATED_TYPE_RECETTE)
            .anonyme(UPDATED_ANONYME)
            .membreNom(UPDATED_MEMBRE_NOM)
            .motif(UPDATED_MOTIF)
            .referencePiece(UPDATED_REFERENCE_PIECE)
            .statut(UPDATED_STATUT);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedRecette.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedRecette))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Recette in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecetteUpdatableFieldsEquals(partialUpdatedRecette, getPersistedRecette(partialUpdatedRecette));
    }

    @Test
    void patchNonExistingRecette() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recette.setId(longCount.incrementAndGet());

        // Create the Recette
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, recetteDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Recette in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRecette() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recette.setId(longCount.incrementAndGet());

        // Create the Recette
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Recette in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRecette() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recette.setId(longCount.incrementAndGet());

        // Create the Recette
        RecetteDTO recetteDTO = recetteMapper.toDto(recette);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(recetteDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Recette in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRecette() {
        // Initialize the database
        insertedRecette = recetteRepository.save(recette).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the recette
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, recette.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return recetteRepository.count().block();
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

    protected Recette getPersistedRecette(Recette recette) {
        return recetteRepository.findById(recette.getId()).block();
    }

    protected void assertPersistedRecetteToMatchAllProperties(Recette expectedRecette) {
        // Test fails because reactive api returns an empty object instead of null
        // assertRecetteAllPropertiesEquals(expectedRecette, getPersistedRecette(expectedRecette));
        assertRecetteUpdatableFieldsEquals(expectedRecette, getPersistedRecette(expectedRecette));
    }

    protected void assertPersistedRecetteToMatchUpdatableProperties(Recette expectedRecette) {
        // Test fails because reactive api returns an empty object instead of null
        // assertRecetteAllUpdatablePropertiesEquals(expectedRecette, getPersistedRecette(expectedRecette));
        assertRecetteUpdatableFieldsEquals(expectedRecette, getPersistedRecette(expectedRecette));
    }
}
