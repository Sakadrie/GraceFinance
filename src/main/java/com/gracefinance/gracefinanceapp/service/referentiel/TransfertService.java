package com.gracefinance.gracefinanceapp.service.referentiel;

import com.gracefinance.gracefinanceapp.service.criteria.referentiel.TransfertCriteria;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.TransfertDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.gracefinance.gracefinanceapp.domain.referentiel.Transfert}.
 */
public interface TransfertService {
    /**
     * Save a transfert.
     *
     * @param transfertDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<TransfertDTO> save(TransfertDTO transfertDTO);

    /**
     * Updates a transfert.
     *
     * @param transfertDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<TransfertDTO> update(TransfertDTO transfertDTO);

    /**
     * Partially updates a transfert.
     *
     * @param transfertDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<TransfertDTO> partialUpdate(TransfertDTO transfertDTO);
    /**
     * Find transferts by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<TransfertDTO> findByCriteria(TransfertCriteria criteria, Pageable pageable);

    /**
     * Find the count of transferts by criteria.
     * @param criteria filtering criteria
     * @return the count of transferts
     */
    public Mono<Long> countByCriteria(TransfertCriteria criteria);

    /**
     * Returns the number of transferts available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" transfert.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<TransfertDTO> findOne(Long id);

    /**
     * Delete the "id" transfert.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
