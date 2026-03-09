package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.service.criteria.principal.EcritureComptableCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.EcritureComptableDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable}.
 */
public interface EcritureComptableService {
    /**
     * Save a ecritureComptable.
     *
     * @param ecritureComptableDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<EcritureComptableDTO> save(EcritureComptableDTO ecritureComptableDTO);

    /**
     * Updates a ecritureComptable.
     *
     * @param ecritureComptableDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<EcritureComptableDTO> update(EcritureComptableDTO ecritureComptableDTO);

    /**
     * Partially updates a ecritureComptable.
     *
     * @param ecritureComptableDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<EcritureComptableDTO> partialUpdate(EcritureComptableDTO ecritureComptableDTO);
    /**
     * Find ecritureComptables by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<EcritureComptableDTO> findByCriteria(EcritureComptableCriteria criteria, Pageable pageable);

    /**
     * Find the count of ecritureComptables by criteria.
     * @param criteria filtering criteria
     * @return the count of ecritureComptables
     */
    public Mono<Long> countByCriteria(EcritureComptableCriteria criteria);

    /**
     * Returns the number of ecritureComptables available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" ecritureComptable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<EcritureComptableDTO> findOne(Long id);

    /**
     * Delete the "id" ecritureComptable.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
