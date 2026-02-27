package com.gracefinance.gracefinanceapp.service.referentiel;

import com.gracefinance.gracefinanceapp.domain.criteria.CategorieCriteria;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.CategorieDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.gracefinance.gracefinanceapp.domain.referentiel.Categorie}.
 */
public interface CategorieService {
    /**
     * Save a categorie.
     *
     * @param categorieDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<CategorieDTO> save(CategorieDTO categorieDTO);

    /**
     * Updates a categorie.
     *
     * @param categorieDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<CategorieDTO> update(CategorieDTO categorieDTO);

    /**
     * Partially updates a categorie.
     *
     * @param categorieDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<CategorieDTO> partialUpdate(CategorieDTO categorieDTO);
    /**
     * Find categories by criteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<CategorieDTO> findByCriteria(CategorieCriteria criteria, Pageable pageable);

    /**
     * Find the count of categories by criteria.
     * @param criteria filtering criteria
     * @return the count of categories
     */
    public Mono<Long> countByCriteria(CategorieCriteria criteria);

    /**
     * Returns the number of categories available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" categorie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<CategorieDTO> findOne(Long id);

    /**
     * Delete the "id" categorie.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
