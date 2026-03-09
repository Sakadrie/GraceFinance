package com.gracefinance.gracefinanceapp.service.security;

import com.gracefinance.gracefinanceapp.service.criteria.security.AffectationUtilisateurCriteria;
import com.gracefinance.gracefinanceapp.service.dto.security.AffectationUtilisateurDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur}.
 */
public interface AffectationUtilisateurService {
    /**
     * Save a affectationUtilisateur.
     *
     * @param affectationUtilisateurDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<AffectationUtilisateurDTO> save(AffectationUtilisateurDTO affectationUtilisateurDTO);

    /**
     * Updates a affectationUtilisateur.
     *
     * @param affectationUtilisateurDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<AffectationUtilisateurDTO> update(AffectationUtilisateurDTO affectationUtilisateurDTO);

    /**
     * Partially updates a affectationUtilisateur.
     *
     * @param affectationUtilisateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<AffectationUtilisateurDTO> partialUpdate(AffectationUtilisateurDTO affectationUtilisateurDTO);
    /**
     * Find affectationUtilisateurs by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<AffectationUtilisateurDTO> findByCriteria(AffectationUtilisateurCriteria criteria, Pageable pageable);

    /**
     * Find the count of affectationUtilisateurs by criteria.
     * @param criteria filtering criteria
     * @return the count of affectationUtilisateurs
     */
    public Mono<Long> countByCriteria(AffectationUtilisateurCriteria criteria);

    /**
     * Get all the affectationUtilisateurs with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<AffectationUtilisateurDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Returns the number of affectationUtilisateurs available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" affectationUtilisateur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<AffectationUtilisateurDTO> findOne(Long id);

    /**
     * Delete the "id" affectationUtilisateur.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
