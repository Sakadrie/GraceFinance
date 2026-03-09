package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.service.criteria.principal.CaisseCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.CaisseDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.gracefinance.gracefinanceapp.domain.principal.Caisse}.
 */
public interface CaisseService {
    /**
     * Save a caisse.
     *
     * @param caisseDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<CaisseDTO> save(CaisseDTO caisseDTO);

    /**
     * Updates a caisse.
     *
     * @param caisseDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<CaisseDTO> update(CaisseDTO caisseDTO);

    /**
     * Partially updates a caisse.
     *
     * @param caisseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<CaisseDTO> partialUpdate(CaisseDTO caisseDTO);
    /**
     * Find caisses by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<CaisseDTO> findByCriteria(CaisseCriteria criteria, Pageable pageable);

    /**
     * Find the count of caisses by criteria.
     * @param criteria filtering criteria
     * @return the count of caisses
     */
    public Mono<Long> countByCriteria(CaisseCriteria criteria);

    /**
     * Returns the number of caisses available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" caisse.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<CaisseDTO> findOne(Long id);

    /**
     * Delete the "id" caisse.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
