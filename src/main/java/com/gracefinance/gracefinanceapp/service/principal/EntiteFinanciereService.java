package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere}.
 */
public interface EntiteFinanciereService {
    /**
     * Save a entiteFinanciere.
     *
     * @param entiteFinanciereDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<EntiteFinanciereDTO> save(EntiteFinanciereDTO entiteFinanciereDTO);

    /**
     * Updates a entiteFinanciere.
     *
     * @param entiteFinanciereDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<EntiteFinanciereDTO> update(EntiteFinanciereDTO entiteFinanciereDTO);

    /**
     * Partially updates a entiteFinanciere.
     *
     * @param entiteFinanciereDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<EntiteFinanciereDTO> partialUpdate(EntiteFinanciereDTO entiteFinanciereDTO);

    /**
     * Get all the entiteFinancieres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<EntiteFinanciereDTO> findAll(Pageable pageable);

    /**
     * Get all the entiteFinancieres with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<EntiteFinanciereDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Returns the number of entiteFinancieres available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" entiteFinanciere.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<EntiteFinanciereDTO> findOne(Long id);

    /**
     * Delete the "id" entiteFinanciere.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
