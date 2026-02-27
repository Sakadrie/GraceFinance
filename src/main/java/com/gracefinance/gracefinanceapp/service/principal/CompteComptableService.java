package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.domain.criteria.CompteComptableCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.CompteComptableDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.gracefinance.gracefinanceapp.domain.principal.CompteComptable}.
 */
public interface CompteComptableService {
    /**
     * Save a compteComptable.
     *
     * @param compteComptableDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<CompteComptableDTO> save(CompteComptableDTO compteComptableDTO);

    /**
     * Updates a compteComptable.
     *
     * @param compteComptableDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<CompteComptableDTO> update(CompteComptableDTO compteComptableDTO);

    /**
     * Partially updates a compteComptable.
     *
     * @param compteComptableDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<CompteComptableDTO> partialUpdate(CompteComptableDTO compteComptableDTO);
    /**
     * Find compteComptables by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<CompteComptableDTO> findByCriteria(CompteComptableCriteria criteria, Pageable pageable);

    /**
     * Find the count of compteComptables by criteria.
     * @param criteria filtering criteria
     * @return the count of compteComptables
     */
    public Mono<Long> countByCriteria(CompteComptableCriteria criteria);

    /**
     * Returns the number of compteComptables available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" compteComptable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<CompteComptableDTO> findOne(Long id);

    /**
     * Delete the "id" compteComptable.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
