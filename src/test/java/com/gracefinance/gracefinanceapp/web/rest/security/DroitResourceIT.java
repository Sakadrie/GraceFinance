package com.gracefinance.gracefinanceapp.web.rest.security;

import static com.gracefinance.gracefinanceapp.domain.security.DroitAsserts.assertDroitUpdatableFieldsEquals;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefinance.gracefinanceapp.IntegrationTest;
import com.gracefinance.gracefinanceapp.domain.security.Droit;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.security.DroitRepository;
import com.gracefinance.gracefinanceapp.service.dto.security.DroitDTO;
import com.gracefinance.gracefinanceapp.service.mapper.security.DroitMapper;
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
 * Integration tests for the {@link DroitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DroitResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/droits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DroitRepository droitRepository;

    @Autowired
    private DroitMapper droitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Droit droit;

    private Droit insertedDroit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Droit createEntity() {
        return new Droit().nom(DEFAULT_NOM).code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Droit createUpdatedEntity() {
        return new Droit().nom(UPDATED_NOM).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Droit.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @BeforeEach
    void initTest() {
        droit = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedDroit != null) {
            droitRepository.delete(insertedDroit).block();
            insertedDroit = null;
        }
        deleteEntities(em);
    }

    @Test
    void createDroit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Droit
        DroitDTO droitDTO = droitMapper.toDto(droit);
        var returnedDroitDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(droitDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(DroitDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Droit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDroit = droitMapper.toEntity(returnedDroitDTO);
        assertDroitUpdatableFieldsEquals(returnedDroit, getPersistedDroit(returnedDroit));

        insertedDroit = returnedDroit;
    }

    @Test
    void createDroitWithExistingId() throws Exception {
        // Create the Droit with an existing ID
        droit.setId(1L);
        DroitDTO droitDTO = droitMapper.toDto(droit);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(droitDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Droit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        droit.setNom(null);

        // Create the Droit, which fails.
        DroitDTO droitDTO = droitMapper.toDto(droit);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(droitDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        droit.setCode(null);

        // Create the Droit, which fails.
        DroitDTO droitDTO = droitMapper.toDto(droit);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(droitDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllDroits() {
        // Initialize the database
        insertedDroit = droitRepository.save(droit).block();

        // Get all the droitList
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
            .value(hasItem(droit.getId().intValue()))
            .jsonPath("$.[*].nom")
            .value(hasItem(DEFAULT_NOM))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION));
    }

    @Test
    void getDroit() {
        // Initialize the database
        insertedDroit = droitRepository.save(droit).block();

        // Get the droit
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, droit.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(droit.getId().intValue()))
            .jsonPath("$.nom")
            .value(is(DEFAULT_NOM))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingDroit() {
        // Get the droit
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDroit() throws Exception {
        // Initialize the database
        insertedDroit = droitRepository.save(droit).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the droit
        Droit updatedDroit = droitRepository.findById(droit.getId()).block();
        updatedDroit.nom(UPDATED_NOM).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        DroitDTO droitDTO = droitMapper.toDto(updatedDroit);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, droitDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(droitDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Droit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDroitToMatchAllProperties(updatedDroit);
    }

    @Test
    void putNonExistingDroit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        droit.setId(longCount.incrementAndGet());

        // Create the Droit
        DroitDTO droitDTO = droitMapper.toDto(droit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, droitDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(droitDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Droit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDroit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        droit.setId(longCount.incrementAndGet());

        // Create the Droit
        DroitDTO droitDTO = droitMapper.toDto(droit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(droitDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Droit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDroit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        droit.setId(longCount.incrementAndGet());

        // Create the Droit
        DroitDTO droitDTO = droitMapper.toDto(droit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(droitDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Droit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDroitWithPatch() throws Exception {
        // Initialize the database
        insertedDroit = droitRepository.save(droit).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the droit using partial update
        Droit partialUpdatedDroit = new Droit();
        partialUpdatedDroit.setId(droit.getId());

        partialUpdatedDroit.nom(UPDATED_NOM).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDroit.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedDroit))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Droit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDroitUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDroit, droit), getPersistedDroit(droit));
    }

    @Test
    void fullUpdateDroitWithPatch() throws Exception {
        // Initialize the database
        insertedDroit = droitRepository.save(droit).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the droit using partial update
        Droit partialUpdatedDroit = new Droit();
        partialUpdatedDroit.setId(droit.getId());

        partialUpdatedDroit.nom(UPDATED_NOM).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDroit.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedDroit))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Droit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDroitUpdatableFieldsEquals(partialUpdatedDroit, getPersistedDroit(partialUpdatedDroit));
    }

    @Test
    void patchNonExistingDroit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        droit.setId(longCount.incrementAndGet());

        // Create the Droit
        DroitDTO droitDTO = droitMapper.toDto(droit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, droitDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(droitDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Droit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDroit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        droit.setId(longCount.incrementAndGet());

        // Create the Droit
        DroitDTO droitDTO = droitMapper.toDto(droit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(droitDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Droit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDroit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        droit.setId(longCount.incrementAndGet());

        // Create the Droit
        DroitDTO droitDTO = droitMapper.toDto(droit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(droitDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Droit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDroit() {
        // Initialize the database
        insertedDroit = droitRepository.save(droit).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the droit
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, droit.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return droitRepository.count().block();
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

    protected Droit getPersistedDroit(Droit droit) {
        return droitRepository.findById(droit.getId()).block();
    }

    protected void assertPersistedDroitToMatchAllProperties(Droit expectedDroit) {
        // Test fails because reactive api returns an empty object instead of null
        // assertDroitAllPropertiesEquals(expectedDroit, getPersistedDroit(expectedDroit));
        assertDroitUpdatableFieldsEquals(expectedDroit, getPersistedDroit(expectedDroit));
    }

    protected void assertPersistedDroitToMatchUpdatableProperties(Droit expectedDroit) {
        // Test fails because reactive api returns an empty object instead of null
        // assertDroitAllUpdatablePropertiesEquals(expectedDroit, getPersistedDroit(expectedDroit));
        assertDroitUpdatableFieldsEquals(expectedDroit, getPersistedDroit(expectedDroit));
    }
}
