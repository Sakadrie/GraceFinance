package com.gracefinance.gracefinanceapp.service.security;

import com.gracefinance.gracefinanceapp.service.dto.security.DroitDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.gracefinance.gracefinanceapp.domain.security.Droit}.
 */
public interface DroitService {
    /**
     * Save a droit.
     *
     * @param droitDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<DroitDTO> save(DroitDTO droitDTO);

    /**
     * Updates a droit.
     *
     * @param droitDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<DroitDTO> update(DroitDTO droitDTO);

    /**
     * Partially updates a droit.
     *
     * @param droitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<DroitDTO> partialUpdate(DroitDTO droitDTO);

    /**
     * Get all the droits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<DroitDTO> findAll(Pageable pageable);

    /**
     * Returns the number of droits available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" droit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<DroitDTO> findOne(Long id);

    /**
     * Delete the "id" droit.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
