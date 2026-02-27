package com.gracefinance.gracefinanceapp.web.rest.security;

import static com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateurAsserts.assertAffectationUtilisateurUpdatableFieldsEquals;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefinance.gracefinanceapp.IntegrationTest;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur;
import com.gracefinance.gracefinanceapp.domain.security.User;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.principal.EntiteFinanciereRepository;
import com.gracefinance.gracefinanceapp.repository.security.AffectationUtilisateurRepository;
import com.gracefinance.gracefinanceapp.repository.security.UserRepository;
import com.gracefinance.gracefinanceapp.service.dto.security.AffectationUtilisateurDTO;
import com.gracefinance.gracefinanceapp.service.mapper.security.AffectationUtilisateurMapper;
import com.gracefinance.gracefinanceapp.service.security.AffectationUtilisateurService;
import com.gracefinance.gracefinanceapp.web.rest.AffectationUtilisateurResource;
import com.gracefinance.gracefinanceapp.web.rest.principal.EntiteFinanciereResourceIT;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

/**
 * Integration tests for the {@link AffectationUtilisateurResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class AffectationUtilisateurResourceIT {

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    private static final LocalDate DEFAULT_DATE_AFFECTATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_AFFECTATION = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_AFFECTATION = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/affectation-utilisateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AffectationUtilisateurRepository affectationUtilisateurRepository;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private AffectationUtilisateurRepository affectationUtilisateurRepositoryMock;

    @Autowired
    private AffectationUtilisateurMapper affectationUtilisateurMapper;

    @Mock
    private AffectationUtilisateurService affectationUtilisateurServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private AffectationUtilisateur affectationUtilisateur;

    private AffectationUtilisateur insertedAffectationUtilisateur;

    @Autowired
    private EntiteFinanciereRepository entiteFinanciereRepository;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AffectationUtilisateur createEntity(EntityManager em) {
        AffectationUtilisateur affectationUtilisateur = new AffectationUtilisateur()
            .actif(DEFAULT_ACTIF)
            .dateAffectation(DEFAULT_DATE_AFFECTATION);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity()).block();
        affectationUtilisateur.setUser(user);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createEntity()).block();
        affectationUtilisateur.setEntiteFinanciere(entiteFinanciere);
        return affectationUtilisateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AffectationUtilisateur createUpdatedEntity(EntityManager em) {
        AffectationUtilisateur updatedAffectationUtilisateur = new AffectationUtilisateur()
            .actif(UPDATED_ACTIF)
            .dateAffectation(UPDATED_DATE_AFFECTATION);
        // Add required entity
        User user = em.insert(UserResourceIT.createEntity()).block();
        updatedAffectationUtilisateur.setUser(user);
        // Add required entity
        EntiteFinanciere entiteFinanciere;
        entiteFinanciere = em.insert(EntiteFinanciereResourceIT.createUpdatedEntity()).block();
        updatedAffectationUtilisateur.setEntiteFinanciere(entiteFinanciere);
        return updatedAffectationUtilisateur;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll("rel_affectation_utilisateur__profil").block();
            em.deleteAll(AffectationUtilisateur.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
        UserResourceIT.deleteEntities(em);
        EntiteFinanciereResourceIT.deleteEntities(em);
    }

    @BeforeEach
    void initTest() {
        affectationUtilisateur = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedAffectationUtilisateur != null) {
            affectationUtilisateurRepository.delete(insertedAffectationUtilisateur).block();
            insertedAffectationUtilisateur = null;
        }
        deleteEntities(em);
        userRepository.deleteAllUserAuthorities().block();
        userRepository.deleteAll().block();
    }

    @Test
    void createAffectationUtilisateur() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AffectationUtilisateur
        AffectationUtilisateurDTO affectationUtilisateurDTO = affectationUtilisateurMapper.toDto(affectationUtilisateur);
        var returnedAffectationUtilisateurDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(affectationUtilisateurDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(AffectationUtilisateurDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the AffectationUtilisateur in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAffectationUtilisateur = affectationUtilisateurMapper.toEntity(returnedAffectationUtilisateurDTO);
        assertAffectationUtilisateurUpdatableFieldsEquals(
            returnedAffectationUtilisateur,
            getPersistedAffectationUtilisateur(returnedAffectationUtilisateur)
        );

        insertedAffectationUtilisateur = returnedAffectationUtilisateur;
    }

    @Test
    void createAffectationUtilisateurWithExistingId() throws Exception {
        // Create the AffectationUtilisateur with an existing ID
        affectationUtilisateur.setId(1L);
        AffectationUtilisateurDTO affectationUtilisateurDTO = affectationUtilisateurMapper.toDto(affectationUtilisateur);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(affectationUtilisateurDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the AffectationUtilisateur in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkActifIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        affectationUtilisateur.setActif(null);

        // Create the AffectationUtilisateur, which fails.
        AffectationUtilisateurDTO affectationUtilisateurDTO = affectationUtilisateurMapper.toDto(affectationUtilisateur);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(affectationUtilisateurDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkDateAffectationIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        affectationUtilisateur.setDateAffectation(null);

        // Create the AffectationUtilisateur, which fails.
        AffectationUtilisateurDTO affectationUtilisateurDTO = affectationUtilisateurMapper.toDto(affectationUtilisateur);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(affectationUtilisateurDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllAffectationUtilisateurs() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get all the affectationUtilisateurList
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
            .value(hasItem(affectationUtilisateur.getId().intValue()))
            .jsonPath("$.[*].actif")
            .value(hasItem(DEFAULT_ACTIF))
            .jsonPath("$.[*].dateAffectation")
            .value(hasItem(DEFAULT_DATE_AFFECTATION.toString()));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAffectationUtilisateursWithEagerRelationshipsIsEnabled() {
        when(affectationUtilisateurServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(affectationUtilisateurServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAffectationUtilisateursWithEagerRelationshipsIsNotEnabled() {
        when(affectationUtilisateurServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(affectationUtilisateurRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getAffectationUtilisateur() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get the affectationUtilisateur
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, affectationUtilisateur.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(affectationUtilisateur.getId().intValue()))
            .jsonPath("$.actif")
            .value(is(DEFAULT_ACTIF))
            .jsonPath("$.dateAffectation")
            .value(is(DEFAULT_DATE_AFFECTATION.toString()));
    }

    @Test
    void getAffectationUtilisateursByIdFiltering() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        Long id = affectationUtilisateur.getId();

        defaultAffectationUtilisateurFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultAffectationUtilisateurFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultAffectationUtilisateurFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllAffectationUtilisateursByActifIsEqualToSomething() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get all the affectationUtilisateurList where actif equals to
        defaultAffectationUtilisateurFiltering("actif.equals=" + DEFAULT_ACTIF, "actif.equals=" + UPDATED_ACTIF);
    }

    @Test
    void getAllAffectationUtilisateursByActifIsInShouldWork() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get all the affectationUtilisateurList where actif in
        defaultAffectationUtilisateurFiltering("actif.in=" + DEFAULT_ACTIF + "," + UPDATED_ACTIF, "actif.in=" + UPDATED_ACTIF);
    }

    @Test
    void getAllAffectationUtilisateursByActifIsNullOrNotNull() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get all the affectationUtilisateurList where actif is not null
        defaultAffectationUtilisateurFiltering("actif.specified=true", "actif.specified=false");
    }

    @Test
    void getAllAffectationUtilisateursByDateAffectationIsEqualToSomething() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get all the affectationUtilisateurList where dateAffectation equals to
        defaultAffectationUtilisateurFiltering(
            "dateAffectation.equals=" + DEFAULT_DATE_AFFECTATION,
            "dateAffectation.equals=" + UPDATED_DATE_AFFECTATION
        );
    }

    @Test
    void getAllAffectationUtilisateursByDateAffectationIsInShouldWork() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get all the affectationUtilisateurList where dateAffectation in
        defaultAffectationUtilisateurFiltering(
            "dateAffectation.in=" + DEFAULT_DATE_AFFECTATION + "," + UPDATED_DATE_AFFECTATION,
            "dateAffectation.in=" + UPDATED_DATE_AFFECTATION
        );
    }

    @Test
    void getAllAffectationUtilisateursByDateAffectationIsNullOrNotNull() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get all the affectationUtilisateurList where dateAffectation is not null
        defaultAffectationUtilisateurFiltering("dateAffectation.specified=true", "dateAffectation.specified=false");
    }

    @Test
    void getAllAffectationUtilisateursByDateAffectationIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get all the affectationUtilisateurList where dateAffectation is greater than or equal to
        defaultAffectationUtilisateurFiltering(
            "dateAffectation.greaterThanOrEqual=" + DEFAULT_DATE_AFFECTATION,
            "dateAffectation.greaterThanOrEqual=" + UPDATED_DATE_AFFECTATION
        );
    }

    @Test
    void getAllAffectationUtilisateursByDateAffectationIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get all the affectationUtilisateurList where dateAffectation is less than or equal to
        defaultAffectationUtilisateurFiltering(
            "dateAffectation.lessThanOrEqual=" + DEFAULT_DATE_AFFECTATION,
            "dateAffectation.lessThanOrEqual=" + SMALLER_DATE_AFFECTATION
        );
    }

    @Test
    void getAllAffectationUtilisateursByDateAffectationIsLessThanSomething() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get all the affectationUtilisateurList where dateAffectation is less than
        defaultAffectationUtilisateurFiltering(
            "dateAffectation.lessThan=" + UPDATED_DATE_AFFECTATION,
            "dateAffectation.lessThan=" + DEFAULT_DATE_AFFECTATION
        );
    }

    @Test
    void getAllAffectationUtilisateursByDateAffectationIsGreaterThanSomething() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        // Get all the affectationUtilisateurList where dateAffectation is greater than
        defaultAffectationUtilisateurFiltering(
            "dateAffectation.greaterThan=" + SMALLER_DATE_AFFECTATION,
            "dateAffectation.greaterThan=" + DEFAULT_DATE_AFFECTATION
        );
    }

    @Test
    void getAllAffectationUtilisateursByUserIsEqualToSomething() {
        User user = UserResourceIT.createEntity();
        userRepository.save(user).block();
        Long userId = user.getId();
        affectationUtilisateur.setUserId(userId);
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();
        // Get all the affectationUtilisateurList where user equals to userId
        defaultAffectationUtilisateurShouldBeFound("userId.equals=" + userId);

        // Get all the affectationUtilisateurList where user equals to (userId + 1)
        defaultAffectationUtilisateurShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    void getAllAffectationUtilisateursByEntiteFinanciereIsEqualToSomething() {
        EntiteFinanciere entiteFinanciere = EntiteFinanciereResourceIT.createEntity();
        entiteFinanciereRepository.save(entiteFinanciere).block();
        Long entiteFinanciereId = entiteFinanciere.getId();
        affectationUtilisateur.setEntiteFinanciereId(entiteFinanciereId);
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();
        // Get all the affectationUtilisateurList where entiteFinanciere equals to entiteFinanciereId
        defaultAffectationUtilisateurShouldBeFound("entiteFinanciereId.equals=" + entiteFinanciereId);

        // Get all the affectationUtilisateurList where entiteFinanciere equals to (entiteFinanciereId + 1)
        defaultAffectationUtilisateurShouldNotBeFound("entiteFinanciereId.equals=" + (entiteFinanciereId + 1));
    }

    private void defaultAffectationUtilisateurFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultAffectationUtilisateurShouldBeFound(shouldBeFound);
        defaultAffectationUtilisateurShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAffectationUtilisateurShouldBeFound(String filter) {
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
            .value(hasItem(affectationUtilisateur.getId().intValue()))
            .jsonPath("$.[*].actif")
            .value(hasItem(DEFAULT_ACTIF))
            .jsonPath("$.[*].dateAffectation")
            .value(hasItem(DEFAULT_DATE_AFFECTATION.toString()));

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
    private void defaultAffectationUtilisateurShouldNotBeFound(String filter) {
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
    void getNonExistingAffectationUtilisateur() {
        // Get the affectationUtilisateur
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingAffectationUtilisateur() throws Exception {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the affectationUtilisateur
        AffectationUtilisateur updatedAffectationUtilisateur = affectationUtilisateurRepository
            .findById(affectationUtilisateur.getId())
            .block();
        updatedAffectationUtilisateur.actif(UPDATED_ACTIF).dateAffectation(UPDATED_DATE_AFFECTATION);
        AffectationUtilisateurDTO affectationUtilisateurDTO = affectationUtilisateurMapper.toDto(updatedAffectationUtilisateur);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, affectationUtilisateurDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(affectationUtilisateurDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the AffectationUtilisateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAffectationUtilisateurToMatchAllProperties(updatedAffectationUtilisateur);
    }

    @Test
    void putNonExistingAffectationUtilisateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affectationUtilisateur.setId(longCount.incrementAndGet());

        // Create the AffectationUtilisateur
        AffectationUtilisateurDTO affectationUtilisateurDTO = affectationUtilisateurMapper.toDto(affectationUtilisateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, affectationUtilisateurDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(affectationUtilisateurDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the AffectationUtilisateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAffectationUtilisateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affectationUtilisateur.setId(longCount.incrementAndGet());

        // Create the AffectationUtilisateur
        AffectationUtilisateurDTO affectationUtilisateurDTO = affectationUtilisateurMapper.toDto(affectationUtilisateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(affectationUtilisateurDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the AffectationUtilisateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAffectationUtilisateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affectationUtilisateur.setId(longCount.incrementAndGet());

        // Create the AffectationUtilisateur
        AffectationUtilisateurDTO affectationUtilisateurDTO = affectationUtilisateurMapper.toDto(affectationUtilisateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(affectationUtilisateurDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the AffectationUtilisateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAffectationUtilisateurWithPatch() throws Exception {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the affectationUtilisateur using partial update
        AffectationUtilisateur partialUpdatedAffectationUtilisateur = new AffectationUtilisateur();
        partialUpdatedAffectationUtilisateur.setId(affectationUtilisateur.getId());

        partialUpdatedAffectationUtilisateur.actif(UPDATED_ACTIF).dateAffectation(UPDATED_DATE_AFFECTATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAffectationUtilisateur.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedAffectationUtilisateur))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the AffectationUtilisateur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAffectationUtilisateurUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAffectationUtilisateur, affectationUtilisateur),
            getPersistedAffectationUtilisateur(affectationUtilisateur)
        );
    }

    @Test
    void fullUpdateAffectationUtilisateurWithPatch() throws Exception {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the affectationUtilisateur using partial update
        AffectationUtilisateur partialUpdatedAffectationUtilisateur = new AffectationUtilisateur();
        partialUpdatedAffectationUtilisateur.setId(affectationUtilisateur.getId());

        partialUpdatedAffectationUtilisateur.actif(UPDATED_ACTIF).dateAffectation(UPDATED_DATE_AFFECTATION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedAffectationUtilisateur.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedAffectationUtilisateur))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the AffectationUtilisateur in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAffectationUtilisateurUpdatableFieldsEquals(
            partialUpdatedAffectationUtilisateur,
            getPersistedAffectationUtilisateur(partialUpdatedAffectationUtilisateur)
        );
    }

    @Test
    void patchNonExistingAffectationUtilisateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affectationUtilisateur.setId(longCount.incrementAndGet());

        // Create the AffectationUtilisateur
        AffectationUtilisateurDTO affectationUtilisateurDTO = affectationUtilisateurMapper.toDto(affectationUtilisateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, affectationUtilisateurDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(affectationUtilisateurDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the AffectationUtilisateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAffectationUtilisateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affectationUtilisateur.setId(longCount.incrementAndGet());

        // Create the AffectationUtilisateur
        AffectationUtilisateurDTO affectationUtilisateurDTO = affectationUtilisateurMapper.toDto(affectationUtilisateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(affectationUtilisateurDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the AffectationUtilisateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAffectationUtilisateur() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        affectationUtilisateur.setId(longCount.incrementAndGet());

        // Create the AffectationUtilisateur
        AffectationUtilisateurDTO affectationUtilisateurDTO = affectationUtilisateurMapper.toDto(affectationUtilisateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(affectationUtilisateurDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the AffectationUtilisateur in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAffectationUtilisateur() {
        // Initialize the database
        insertedAffectationUtilisateur = affectationUtilisateurRepository.save(affectationUtilisateur).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the affectationUtilisateur
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, affectationUtilisateur.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return affectationUtilisateurRepository.count().block();
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

    protected AffectationUtilisateur getPersistedAffectationUtilisateur(AffectationUtilisateur affectationUtilisateur) {
        return affectationUtilisateurRepository.findById(affectationUtilisateur.getId()).block();
    }

    protected void assertPersistedAffectationUtilisateurToMatchAllProperties(AffectationUtilisateur expectedAffectationUtilisateur) {
        // Test fails because reactive api returns an empty object instead of null
        // assertAffectationUtilisateurAllPropertiesEquals(expectedAffectationUtilisateur, getPersistedAffectationUtilisateur(expectedAffectationUtilisateur));
        assertAffectationUtilisateurUpdatableFieldsEquals(
            expectedAffectationUtilisateur,
            getPersistedAffectationUtilisateur(expectedAffectationUtilisateur)
        );
    }

    protected void assertPersistedAffectationUtilisateurToMatchUpdatableProperties(AffectationUtilisateur expectedAffectationUtilisateur) {
        // Test fails because reactive api returns an empty object instead of null
        // assertAffectationUtilisateurAllUpdatablePropertiesEquals(expectedAffectationUtilisateur, getPersistedAffectationUtilisateur(expectedAffectationUtilisateur));
        assertAffectationUtilisateurUpdatableFieldsEquals(
            expectedAffectationUtilisateur,
            getPersistedAffectationUtilisateur(expectedAffectationUtilisateur)
        );
    }
}
