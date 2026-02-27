package com.gracefinance.gracefinanceapp.web.rest.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciereAsserts.*;
import static com.gracefinance.gracefinanceapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gracefinance.gracefinanceapp.IntegrationTest;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.repository.EntityManager;
import com.gracefinance.gracefinanceapp.repository.principal.EntiteFinanciereRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.EntiteFinanciereMapper;
import com.gracefinance.gracefinanceapp.service.principal.EntiteFinanciereService;
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
 * Integration tests for the {@link EntiteFinanciereResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
public class EntiteFinanciereResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIF = false;
    private static final Boolean UPDATED_ACTIF = true;

    private static final String ENTITY_API_URL = "/api/entite-financieres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EntiteFinanciereRepository entiteFinanciereRepository;

    @Mock
    private EntiteFinanciereRepository entiteFinanciereRepositoryMock;

    @Autowired
    private EntiteFinanciereMapper entiteFinanciereMapper;

    @Mock
    private EntiteFinanciereService entiteFinanciereServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private EntiteFinanciere entiteFinanciere;

    private EntiteFinanciere insertedEntiteFinanciere;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntiteFinanciere createEntity() {
        return new EntiteFinanciere()
            .nom(DEFAULT_NOM)
            .code(DEFAULT_CODE)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .actif(DEFAULT_ACTIF);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntiteFinanciere createUpdatedEntity() {
        return new EntiteFinanciere()
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .actif(UPDATED_ACTIF);
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll("rel_entite_financiere__eglise_liee").block();
            em.deleteAll(EntiteFinanciere.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @BeforeEach
    void initTest() {
        entiteFinanciere = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedEntiteFinanciere != null) {
            entiteFinanciereRepository.delete(insertedEntiteFinanciere).block();
            insertedEntiteFinanciere = null;
        }
        deleteEntities(em);
    }

    @Test
    void createEntiteFinanciere() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EntiteFinanciere
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);
        var returnedEntiteFinanciereDTO = webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(EntiteFinanciereDTO.class)
            .returnResult()
            .getResponseBody();

        // Validate the EntiteFinanciere in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEntiteFinanciere = entiteFinanciereMapper.toEntity(returnedEntiteFinanciereDTO);
        assertEntiteFinanciereUpdatableFieldsEquals(returnedEntiteFinanciere, getPersistedEntiteFinanciere(returnedEntiteFinanciere));

        insertedEntiteFinanciere = returnedEntiteFinanciere;
    }

    @Test
    void createEntiteFinanciereWithExistingId() throws Exception {
        // Create the EntiteFinanciere with an existing ID
        entiteFinanciere.setId(1L);
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EntiteFinanciere in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkNomIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entiteFinanciere.setNom(null);

        // Create the EntiteFinanciere, which fails.
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entiteFinanciere.setCode(null);

        // Create the EntiteFinanciere, which fails.
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entiteFinanciere.setType(null);

        // Create the EntiteFinanciere, which fails.
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkActifIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entiteFinanciere.setActif(null);

        // Create the EntiteFinanciere, which fails.
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllEntiteFinancieres() {
        // Initialize the database
        insertedEntiteFinanciere = entiteFinanciereRepository.save(entiteFinanciere).block();

        // Get all the entiteFinanciereList
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
            .value(hasItem(entiteFinanciere.getId().intValue()))
            .jsonPath("$.[*].nom")
            .value(hasItem(DEFAULT_NOM))
            .jsonPath("$.[*].code")
            .value(hasItem(DEFAULT_CODE))
            .jsonPath("$.[*].type")
            .value(hasItem(DEFAULT_TYPE))
            .jsonPath("$.[*].description")
            .value(hasItem(DEFAULT_DESCRIPTION))
            .jsonPath("$.[*].actif")
            .value(hasItem(DEFAULT_ACTIF));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEntiteFinancieresWithEagerRelationshipsIsEnabled() {
        when(entiteFinanciereServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(entiteFinanciereServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEntiteFinancieresWithEagerRelationshipsIsNotEnabled() {
        when(entiteFinanciereServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(entiteFinanciereRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getEntiteFinanciere() {
        // Initialize the database
        insertedEntiteFinanciere = entiteFinanciereRepository.save(entiteFinanciere).block();

        // Get the entiteFinanciere
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, entiteFinanciere.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(entiteFinanciere.getId().intValue()))
            .jsonPath("$.nom")
            .value(is(DEFAULT_NOM))
            .jsonPath("$.code")
            .value(is(DEFAULT_CODE))
            .jsonPath("$.type")
            .value(is(DEFAULT_TYPE))
            .jsonPath("$.description")
            .value(is(DEFAULT_DESCRIPTION))
            .jsonPath("$.actif")
            .value(is(DEFAULT_ACTIF));
    }

    @Test
    void getNonExistingEntiteFinanciere() {
        // Get the entiteFinanciere
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingEntiteFinanciere() throws Exception {
        // Initialize the database
        insertedEntiteFinanciere = entiteFinanciereRepository.save(entiteFinanciere).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entiteFinanciere
        EntiteFinanciere updatedEntiteFinanciere = entiteFinanciereRepository.findById(entiteFinanciere.getId()).block();
        updatedEntiteFinanciere
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .actif(UPDATED_ACTIF);
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(updatedEntiteFinanciere);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, entiteFinanciereDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the EntiteFinanciere in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEntiteFinanciereToMatchAllProperties(updatedEntiteFinanciere);
    }

    @Test
    void putNonExistingEntiteFinanciere() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entiteFinanciere.setId(longCount.incrementAndGet());

        // Create the EntiteFinanciere
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, entiteFinanciereDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EntiteFinanciere in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEntiteFinanciere() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entiteFinanciere.setId(longCount.incrementAndGet());

        // Create the EntiteFinanciere
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EntiteFinanciere in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEntiteFinanciere() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entiteFinanciere.setId(longCount.incrementAndGet());

        // Create the EntiteFinanciere
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the EntiteFinanciere in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEntiteFinanciereWithPatch() throws Exception {
        // Initialize the database
        insertedEntiteFinanciere = entiteFinanciereRepository.save(entiteFinanciere).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entiteFinanciere using partial update
        EntiteFinanciere partialUpdatedEntiteFinanciere = new EntiteFinanciere();
        partialUpdatedEntiteFinanciere.setId(entiteFinanciere.getId());

        partialUpdatedEntiteFinanciere.nom(UPDATED_NOM).code(UPDATED_CODE).type(UPDATED_TYPE).actif(UPDATED_ACTIF);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEntiteFinanciere.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedEntiteFinanciere))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the EntiteFinanciere in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntiteFinanciereUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEntiteFinanciere, entiteFinanciere),
            getPersistedEntiteFinanciere(entiteFinanciere)
        );
    }

    @Test
    void fullUpdateEntiteFinanciereWithPatch() throws Exception {
        // Initialize the database
        insertedEntiteFinanciere = entiteFinanciereRepository.save(entiteFinanciere).block();

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entiteFinanciere using partial update
        EntiteFinanciere partialUpdatedEntiteFinanciere = new EntiteFinanciere();
        partialUpdatedEntiteFinanciere.setId(entiteFinanciere.getId());

        partialUpdatedEntiteFinanciere
            .nom(UPDATED_NOM)
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .actif(UPDATED_ACTIF);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedEntiteFinanciere.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(partialUpdatedEntiteFinanciere))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the EntiteFinanciere in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntiteFinanciereUpdatableFieldsEquals(
            partialUpdatedEntiteFinanciere,
            getPersistedEntiteFinanciere(partialUpdatedEntiteFinanciere)
        );
    }

    @Test
    void patchNonExistingEntiteFinanciere() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entiteFinanciere.setId(longCount.incrementAndGet());

        // Create the EntiteFinanciere
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, entiteFinanciereDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EntiteFinanciere in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEntiteFinanciere() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entiteFinanciere.setId(longCount.incrementAndGet());

        // Create the EntiteFinanciere
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the EntiteFinanciere in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEntiteFinanciere() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entiteFinanciere.setId(longCount.incrementAndGet());

        // Create the EntiteFinanciere
        EntiteFinanciereDTO entiteFinanciereDTO = entiteFinanciereMapper.toDto(entiteFinanciere);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(om.writeValueAsBytes(entiteFinanciereDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the EntiteFinanciere in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEntiteFinanciere() {
        // Initialize the database
        insertedEntiteFinanciere = entiteFinanciereRepository.save(entiteFinanciere).block();

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the entiteFinanciere
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, entiteFinanciere.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return entiteFinanciereRepository.count().block();
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

    protected EntiteFinanciere getPersistedEntiteFinanciere(EntiteFinanciere entiteFinanciere) {
        return entiteFinanciereRepository.findById(entiteFinanciere.getId()).block();
    }

    protected void assertPersistedEntiteFinanciereToMatchAllProperties(EntiteFinanciere expectedEntiteFinanciere) {
        // Test fails because reactive api returns an empty object instead of null
        // assertEntiteFinanciereAllPropertiesEquals(expectedEntiteFinanciere, getPersistedEntiteFinanciere(expectedEntiteFinanciere));
        assertEntiteFinanciereUpdatableFieldsEquals(expectedEntiteFinanciere, getPersistedEntiteFinanciere(expectedEntiteFinanciere));
    }

    protected void assertPersistedEntiteFinanciereToMatchUpdatableProperties(EntiteFinanciere expectedEntiteFinanciere) {
        // Test fails because reactive api returns an empty object instead of null
        // assertEntiteFinanciereAllUpdatablePropertiesEquals(expectedEntiteFinanciere, getPersistedEntiteFinanciere(expectedEntiteFinanciere));
        assertEntiteFinanciereUpdatableFieldsEquals(expectedEntiteFinanciere, getPersistedEntiteFinanciere(expectedEntiteFinanciere));
    }
}
