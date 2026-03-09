package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.service.criteria.principal.LigneEcritureCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.LigneEcritureDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture}.
 */
public interface LigneEcritureService {
    /**
     * Save a ligneEcriture.
     *
     * @param ligneEcritureDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<LigneEcritureDTO> save(LigneEcritureDTO ligneEcritureDTO);

    /**
     * Updates a ligneEcriture.
     *
     * @param ligneEcritureDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<LigneEcritureDTO> update(LigneEcritureDTO ligneEcritureDTO);

    /**
     * Partially updates a ligneEcriture.
     *
     * @param ligneEcritureDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<LigneEcritureDTO> partialUpdate(LigneEcritureDTO ligneEcritureDTO);
    /**
     * Find ligneEcritures by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<LigneEcritureDTO> findByCriteria(LigneEcritureCriteria criteria, Pageable pageable);

    /**
     * Find the count of ligneEcritures by criteria.
     * @param criteria filtering criteria
     * @return the count of ligneEcritures
     */
    public Mono<Long> countByCriteria(LigneEcritureCriteria criteria);

    /**
     * Returns the number of ligneEcritures available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" ligneEcriture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<LigneEcritureDTO> findOne(Long id);

    /**
     * Delete the "id" ligneEcriture.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
