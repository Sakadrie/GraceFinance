package com.gracefinance.gracefinanceapp.web.rest.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.CompteComptableAsserts.assertCompteComptableUpdatableFieldsEquals;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefinance.gracefinanceapp.IntegrationTest;
import com.gracefinance.gracefinanceapp.domain.principal.CompteComptable;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.principal.CompteComptableRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.CompteComptableDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.CompteComptableMapper;
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
 * Integration tests for the {@link CompteComptableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class CompteComptableResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CLASSE = 1;
    private static final Integer UPDATED_CLASSE = 2;
    private static final Integer SMALLER_CLASSE = 1 - 1;

    private static final String ENTITY_API_URL = "/api/compte-comptables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CompteComptableRepository compteComptableRepository;

    @Autowired
    private CompteComptableMapper compteComptableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private CompteComptable compteComptable;

    private CompteComptable insertedCompteComptable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompteComptable createEntity() {
        return new CompteComptable().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE).classe(DEFAULT_CLASSE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompteComptable createUpdatedEntity() {
        return new CompteComptable().code(UPDATED_CODE).libelle(UPDATED_LIBELLE).classe(UPDATED_CLASSE);
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(CompteComptable.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @BeforeEach
    void initTest() {
        compteComptable = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCompteComptable != null) {
            compteComptableRepository.delete(insertedCompteComptable).block();
            insertedCompteComptable = null;
        }
        deleteEntities(em);
    }

    @Test
    void createCompteComptable() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CompteComptable
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(compteComptable);
        var returnedCompteComptableDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(CompteComptableDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the CompteComptable in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCompteComptable = compteComptableMapper.toEntity(returnedCompteComptableDTO);
        assertCompteComptableUpdatableFieldsEquals(returnedCompteComptable, getPersistedCompteComptable(returnedCompteComptable));

        insertedCompteComptable = returnedCompteComptable;
    }

    @Test
    void createCompteComptableWithExistingId() throws Exception {
        // Create the CompteComptable with an existing ID
        compteComptable.setId(1L);
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(compteComptable);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CompteComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        compteComptable.setCode(null);

        // Create the CompteComptable, which fails.
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(compteComptable);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkLibelleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        compteComptable.setLibelle(null);

        // Create the CompteComptable, which fails.
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(compteComptable);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkClasseIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        compteComptable.setClasse(null);

        // Create the CompteComptable, which fails.
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(compteComptable);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllCompteComptables() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList
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
            .value(hasItem(compteComptable.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE))
            .jsonPath("$.[*].classe")
            .value(hasItem(DEFAULT_CLASSE));
    }

    @Test
    void getCompteComptable() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get the compteComptable
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, compteComptable.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(compteComptable.getId().intValue()))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.libelle")
            .value(is(DEFAULT_LIBELLE))
            .jsonPath("$.classe")
            .value(is(DEFAULT_CLASSE));
    }

    @Test
    void getCompteComptablesByIdFiltering() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        Long id = compteComptable.getId();

        defaultCompteComptableFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultCompteComptableFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultCompteComptableFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    void getAllCompteComptablesByCodeIsEqualToSomething() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where code equals to
        defaultCompteComptableFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    void getAllCompteComptablesByCodeIsInShouldWork() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where code in
        defaultCompteComptableFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    void getAllCompteComptablesByCodeIsNullOrNotNull() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where code is not null
        defaultCompteComptableFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    void getAllCompteComptablesByCodeContainsSomething() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where code contains
        defaultCompteComptableFiltering("code.contains=" + DEFAULT_CODE, "code.contains=" + UPDATED_CODE);
    }

    @Test
    void getAllCompteComptablesByCodeNotContainsSomething() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where code does not contain
        defaultCompteComptableFiltering("code.doesNotContain=" + UPDATED_CODE, "code.doesNotContain=" + DEFAULT_CODE);
    }

    @Test
    void getAllCompteComptablesByLibelleIsEqualToSomething() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where libelle equals to
        defaultCompteComptableFiltering("libelle.equals=" + DEFAULT_LIBELLE, "libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    void getAllCompteComptablesByLibelleIsInShouldWork() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where libelle in
        defaultCompteComptableFiltering("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE, "libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    void getAllCompteComptablesByLibelleIsNullOrNotNull() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where libelle is not null
        defaultCompteComptableFiltering("libelle.specified=true", "libelle.specified=false");
    }

    @Test
    void getAllCompteComptablesByLibelleContainsSomething() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where libelle contains
        defaultCompteComptableFiltering("libelle.contains=" + DEFAULT_LIBELLE, "libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    void getAllCompteComptablesByLibelleNotContainsSomething() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where libelle does not contain
        defaultCompteComptableFiltering("libelle.doesNotContain=" + UPDATED_LIBELLE, "libelle.doesNotContain=" + DEFAULT_LIBELLE);
    }

    @Test
    void getAllCompteComptablesByClasseIsEqualToSomething() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where classe equals to
        defaultCompteComptableFiltering("classe.equals=" + DEFAULT_CLASSE, "classe.equals=" + UPDATED_CLASSE);
    }

    @Test
    void getAllCompteComptablesByClasseIsInShouldWork() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where classe in
        defaultCompteComptableFiltering("classe.in=" + DEFAULT_CLASSE + "," + UPDATED_CLASSE, "classe.in=" + UPDATED_CLASSE);
    }

    @Test
    void getAllCompteComptablesByClasseIsNullOrNotNull() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where classe is not null
        defaultCompteComptableFiltering("classe.specified=true", "classe.specified=false");
    }

    @Test
    void getAllCompteComptablesByClasseIsGreaterThanOrEqualToSomething() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where classe is greater than or equal to
        defaultCompteComptableFiltering("classe.greaterThanOrEqual=" + DEFAULT_CLASSE, "classe.greaterThanOrEqual=" + UPDATED_CLASSE);
    }

    @Test
    void getAllCompteComptablesByClasseIsLessThanOrEqualToSomething() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where classe is less than or equal to
        defaultCompteComptableFiltering("classe.lessThanOrEqual=" + DEFAULT_CLASSE, "classe.lessThanOrEqual=" + SMALLER_CLASSE);
    }

    @Test
    void getAllCompteComptablesByClasseIsLessThanSomething() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where classe is less than
        defaultCompteComptableFiltering("classe.lessThan=" + UPDATED_CLASSE, "classe.lessThan=" + DEFAULT_CLASSE);
    }

    @Test
    void getAllCompteComptablesByClasseIsGreaterThanSomething() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        // Get all the compteComptableList where classe is greater than
        defaultCompteComptableFiltering("classe.greaterThan=" + SMALLER_CLASSE, "classe.greaterThan=" + DEFAULT_CLASSE);
    }

    private void defaultCompteComptableFiltering(String shouldBeFound, String shouldNotBeFound) {
        defaultCompteComptableShouldBeFound(shouldBeFound);
        defaultCompteComptableShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompteComptableShouldBeFound(String filter) {
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
            .value(hasItem(compteComptable.getId().intValue()))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].libelle")
            .value(hasItem(DEFAULT_LIBELLE))
            .jsonPath("$.[*].classe")
            .value(hasItem(DEFAULT_CLASSE));

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
    private void defaultCompteComptableShouldNotBeFound(String filter) {
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
    void getNonExistingCompteComptable() {
        // Get the compteComptable
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingCompteComptable() throws Exception {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the compteComptable
        CompteComptable updatedCompteComptable = compteComptableRepository.findById(compteComptable.getId()).block();
        updatedCompteComptable.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).classe(UPDATED_CLASSE);
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(updatedCompteComptable);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, compteComptableDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CompteComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCompteComptableToMatchAllProperties(updatedCompteComptable);
    }

    @Test
    void putNonExistingCompteComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteComptable.setId(longCount.incrementAndGet());

        // Create the CompteComptable
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(compteComptable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, compteComptableDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CompteComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCompteComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteComptable.setId(longCount.incrementAndGet());

        // Create the CompteComptable
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(compteComptable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CompteComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCompteComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteComptable.setId(longCount.incrementAndGet());

        // Create the CompteComptable
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(compteComptable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the CompteComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCompteComptableWithPatch() throws Exception {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the compteComptable using partial update
        CompteComptable partialUpdatedCompteComptable = new CompteComptable();
        partialUpdatedCompteComptable.setId(compteComptable.getId());

        partialUpdatedCompteComptable.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCompteComptable.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedCompteComptable))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CompteComptable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompteComptableUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCompteComptable, compteComptable),
            getPersistedCompteComptable(compteComptable)
        );
    }

    @Test
    void fullUpdateCompteComptableWithPatch() throws Exception {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the compteComptable using partial update
        CompteComptable partialUpdatedCompteComptable = new CompteComptable();
        partialUpdatedCompteComptable.setId(compteComptable.getId());

        partialUpdatedCompteComptable.code(UPDATED_CODE).libelle(UPDATED_LIBELLE).classe(UPDATED_CLASSE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCompteComptable.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedCompteComptable))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the CompteComptable in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCompteComptableUpdatableFieldsEquals(
            partialUpdatedCompteComptable,
            getPersistedCompteComptable(partialUpdatedCompteComptable)
        );
    }

    @Test
    void patchNonExistingCompteComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteComptable.setId(longCount.incrementAndGet());

        // Create the CompteComptable
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(compteComptable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, compteComptableDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CompteComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCompteComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteComptable.setId(longCount.incrementAndGet());

        // Create the CompteComptable
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(compteComptable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the CompteComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCompteComptable() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        compteComptable.setId(longCount.incrementAndGet());

        // Create the CompteComptable
        CompteComptableDTO compteComptableDTO = compteComptableMapper.toDto(compteComptable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(compteComptableDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the CompteComptable in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCompteComptable() {
        // Initialize the database
        insertedCompteComptable = compteComptableRepository.save(compteComptable).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the compteComptable
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, compteComptable.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return compteComptableRepository.count().block();
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

    protected CompteComptable getPersistedCompteComptable(CompteComptable compteComptable) {
        return compteComptableRepository.findById(compteComptable.getId()).block();
    }

    protected void assertPersistedCompteComptableToMatchAllProperties(CompteComptable expectedCompteComptable) {
        // Test fails because reactive api returns an empty object instead of null
        // assertCompteComptableAllPropertiesEquals(expectedCompteComptable, getPersistedCompteComptable(expectedCompteComptable));
        assertCompteComptableUpdatableFieldsEquals(expectedCompteComptable, getPersistedCompteComptable(expectedCompteComptable));
    }

    protected void assertPersistedCompteComptableToMatchUpdatableProperties(CompteComptable expectedCompteComptable) {
        // Test fails because reactive api returns an empty object instead of null
        // assertCompteComptableAllUpdatablePropertiesEquals(expectedCompteComptable, getPersistedCompteComptable(expectedCompteComptable));
        assertCompteComptableUpdatableFieldsEquals(expectedCompteComptable, getPersistedCompteComptable(expectedCompteComptable));
    }
}
