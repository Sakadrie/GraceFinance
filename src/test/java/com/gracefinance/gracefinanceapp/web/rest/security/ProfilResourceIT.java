package com.gracefinance.gracefinanceapp.web.rest.security;

import static com.gracefinance.gracefinanceapp.domain.security.ProfilAsserts.assertProfilUpdatableFieldsEquals;
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
import com.gracefinance.gracefinanceapp.domain.security.Profil;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.security.ProfilRepository;
import com.gracefinance.gracefinanceapp.service.dto.security.ProfilDTO;
import com.gracefinance.gracefinanceapp.service.mapper.security.ProfilMapper;
import com.gracefinance.gracefinanceapp.service.security.ProfilService;
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
 * Integration tests for the {@link ProfilResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ProfilResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/profils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProfilRepository profilRepository;

    @Mock
    private ProfilRepository profilRepositoryMock;

    @Autowired
    private ProfilMapper profilMapper;

    @Mock
    private ProfilService profilServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Profil profil;

    private Profil insertedProfil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profil createEntity() {
        return new Profil().nom(DEFAULT_NOM).code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profil createUpdatedEntity() {
        return new Profil().nom(UPDATED_NOM).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll("rel_profil__droit").block();
            em.deleteAll(Profil.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @BeforeEach
    void initTest() {
        profil = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedProfil != null) {
            profilRepository.delete(insertedProfil).block();
            insertedProfil = null;
        }
        deleteEntities(em);
    }

    @Test
    void createProfil() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Profil
        ProfilDTO profilDTO = profilMapper.toDto(profil);
        var returnedProfilDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(profilDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(ProfilDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the Profil in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedProfil = profilMapper.toEntity(returnedProfilDTO);
        assertProfilUpdatableFieldsEquals(returnedProfil, getPersistedProfil(returnedProfil));

        insertedProfil = returnedProfil;
    }

    @Test
    void createProfilWithExistingId() throws Exception {
        // Create the Profil with an existing ID
        profil.setId(1L);
        ProfilDTO profilDTO = profilMapper.toDto(profil);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(profilDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Profil in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        profil.setNom(null);

        // Create the Profil, which fails.
        ProfilDTO profilDTO = profilMapper.toDto(profil);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(profilDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        profil.setCode(null);

        // Create the Profil, which fails.
        ProfilDTO profilDTO = profilMapper.toDto(profil);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(profilDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllProfils() {
        // Initialize the database
        insertedProfil = profilRepository.save(profil).block();

        // Get all the profilList
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
            .value(hasItem(profil.getId().intValue()))
            .jsonPath("$.[*].nom")
            .value(hasItem(DEFAULT_NOM))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfilsWithEagerRelationshipsIsEnabled() {
        when(profilServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(profilServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProfilsWithEagerRelationshipsIsNotEnabled() {
        when(profilServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(profilRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getProfil() {
        // Initialize the database
        insertedProfil = profilRepository.save(profil).block();

        // Get the profil
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, profil.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(profil.getId().intValue()))
            .jsonPath("$.nom")
            .value(is(DEFAULT_NOM))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingProfil() {
        // Get the profil
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingProfil() throws Exception {
        // Initialize the database
        insertedProfil = profilRepository.save(profil).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profil
        Profil updatedProfil = profilRepository.findById(profil.getId()).block();
        updatedProfil.nom(UPDATED_NOM).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        ProfilDTO profilDTO = profilMapper.toDto(updatedProfil);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, profilDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(profilDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Profil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProfilToMatchAllProperties(updatedProfil);
    }

    @Test
    void putNonExistingProfil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profil.setId(longCount.incrementAndGet());

        // Create the Profil
        ProfilDTO profilDTO = profilMapper.toDto(profil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, profilDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(profilDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Profil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchProfil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profil.setId(longCount.incrementAndGet());

        // Create the Profil
        ProfilDTO profilDTO = profilMapper.toDto(profil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(profilDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Profil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamProfil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profil.setId(longCount.incrementAndGet());

        // Create the Profil
        ProfilDTO profilDTO = profilMapper.toDto(profil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(profilDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Profil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateProfilWithPatch() throws Exception {
        // Initialize the database
        insertedProfil = profilRepository.save(profil).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profil using partial update
        Profil partialUpdatedProfil = new Profil();
        partialUpdatedProfil.setId(profil.getId());

        partialUpdatedProfil.nom(UPDATED_NOM).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProfil.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedProfil))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Profil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfilUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedProfil, profil), getPersistedProfil(profil));
    }

    @Test
    void fullUpdateProfilWithPatch() throws Exception {
        // Initialize the database
        insertedProfil = profilRepository.save(profil).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the profil using partial update
        Profil partialUpdatedProfil = new Profil();
        partialUpdatedProfil.setId(profil.getId());

        partialUpdatedProfil.nom(UPDATED_NOM).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedProfil.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedProfil))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Profil in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfilUpdatableFieldsEquals(partialUpdatedProfil, getPersistedProfil(partialUpdatedProfil));
    }

    @Test
    void patchNonExistingProfil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profil.setId(longCount.incrementAndGet());

        // Create the Profil
        ProfilDTO profilDTO = profilMapper.toDto(profil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, profilDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(profilDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Profil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchProfil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profil.setId(longCount.incrementAndGet());

        // Create the Profil
        ProfilDTO profilDTO = profilMapper.toDto(profil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(profilDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Profil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamProfil() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        profil.setId(longCount.incrementAndGet());

        // Create the Profil
        ProfilDTO profilDTO = profilMapper.toDto(profil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(profilDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Profil in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteProfil() {
        // Initialize the database
        insertedProfil = profilRepository.save(profil).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the profil
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, profil.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return profilRepository.count().block();
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

    protected Profil getPersistedProfil(Profil profil) {
        return profilRepository.findById(profil.getId()).block();
    }

    protected void assertPersistedProfilToMatchAllProperties(Profil expectedProfil) {
        // Test fails because reactive api returns an empty object instead of null
        // assertProfilAllPropertiesEquals(expectedProfil, getPersistedProfil(expectedProfil));
        assertProfilUpdatableFieldsEquals(expectedProfil, getPersistedProfil(expectedProfil));
    }

    protected void assertPersistedProfilToMatchUpdatableProperties(Profil expectedProfil) {
        // Test fails because reactive api returns an empty object instead of null
        // assertProfilAllUpdatablePropertiesEquals(expectedProfil, getPersistedProfil(expectedProfil));
        assertProfilUpdatableFieldsEquals(expectedProfil, getPersistedProfil(expectedProfil));
    }
}
