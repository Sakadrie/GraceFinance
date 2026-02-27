package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.domain.criteria.DepenseCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.DepenseDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.gracefinance.gracefinanceapp.domain.principal.Depense}.
 */
public interface DepenseService {
    /**
     * Save a depense.
     *
     * @param depenseDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<DepenseDTO> save(DepenseDTO depenseDTO);

    /**
     * Updates a depense.
     *
     * @param depenseDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<DepenseDTO> update(DepenseDTO depenseDTO);

    /**
     * Partially updates a depense.
     *
     * @param depenseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<DepenseDTO> partialUpdate(DepenseDTO depenseDTO);
    /**
     * Find depenses by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<DepenseDTO> findByCriteria(DepenseCriteria criteria, Pageable pageable);

    /**
     * Find the count of depenses by criteria.
     * @param criteria filtering criteria
     * @return the count of depenses
     */
    public Mono<Long> countByCriteria(DepenseCriteria criteria);

    /**
     * Returns the number of depenses available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" depense.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<DepenseDTO> findOne(Long id);

    /**
     * Delete the "id" depense.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
