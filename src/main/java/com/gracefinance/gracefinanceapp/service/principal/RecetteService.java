package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.service.criteria.principal.RecetteCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.RecetteDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.gracefinance.gracefinanceapp.domain.principal.Recette}.
 */
public interface RecetteService {
    /**
     * Save a recette.
     *
     * @param recetteDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<RecetteDTO> save(RecetteDTO recetteDTO);

    /**
     * Updates a recette.
     *
     * @param recetteDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<RecetteDTO> update(RecetteDTO recetteDTO);

    /**
     * Partially updates a recette.
     *
     * @param recetteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<RecetteDTO> partialUpdate(RecetteDTO recetteDTO);
    /**
     * Find recettes by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<RecetteDTO> findByCriteria(RecetteCriteria criteria, Pageable pageable);

    /**
     * Find the count of recettes by criteria.
     * @param criteria filtering criteria
     * @return the count of recettes
     */
    public Mono<Long> countByCriteria(RecetteCriteria criteria);

    /**
     * Returns the number of recettes available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" recette.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<RecetteDTO> findOne(Long id);

    /**
     * Delete the "id" recette.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
