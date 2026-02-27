package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.domain.criteria.CategorieCriteria;
import com.gracefinance.gracefinanceapp.repository.referentiel.CategorieRepository;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.CategorieDTO;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.CategorieMapper;
import com.gracefinance.gracefinanceapp.service.referentiel.CategorieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.referentiel.Categorie}.
 */
@Service
@Transactional
public class CategorieServiceImpl implements CategorieService {

    private static final Logger LOG = LoggerFactory.getLogger(CategorieServiceImpl.class);

    private final CategorieRepository categorieRepository;

    private final CategorieMapper categorieMapper;

    public CategorieServiceImpl(CategorieRepository categorieRepository, CategorieMapper categorieMapper) {
        this.categorieRepository = categorieRepository;
        this.categorieMapper = categorieMapper;
    }

    @Override
    public Mono<CategorieDTO> save(CategorieDTO categorieDTO) {
        LOG.debug("Request to save Categorie : {}", categorieDTO);
        return categorieRepository.save(categorieMapper.toEntity(categorieDTO)).map(categorieMapper::toDto);
    }

    @Override
    public Mono<CategorieDTO> update(CategorieDTO categorieDTO) {
        LOG.debug("Request to update Categorie : {}", categorieDTO);
        return categorieRepository.save(categorieMapper.toEntity(categorieDTO)).map(categorieMapper::toDto);
    }

    @Override
    public Mono<CategorieDTO> partialUpdate(CategorieDTO categorieDTO) {
        LOG.debug("Request to partially update Categorie : {}", categorieDTO);

        return categorieRepository
            .findById(categorieDTO.getId())
            .map(existingCategorie -> {
                categorieMapper.partialUpdate(existingCategorie, categorieDTO);

                return existingCategorie;
            })
            .flatMap(categorieRepository::save)
            .map(categorieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<CategorieDTO> findByCriteria(CategorieCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all Categories by Criteria");
        return categorieRepository.findByCriteria(criteria, pageable).map(categorieMapper::toDto);
    }

    /**
     * Find the count of categories by criteria.
     * @param criteria filtering criteria
     * @return the count of categories
     */
    public Mono<Long> countByCriteria(CategorieCriteria criteria) {
        LOG.debug("Request to get the count of all Categories by Criteria");
        return categorieRepository.countByCriteria(criteria);
    }

    public Mono<Long> countAll() {
        return categorieRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<CategorieDTO> findOne(Long id) {
        LOG.debug("Request to get Categorie : {}", id);
        return categorieRepository.findById(id).map(categorieMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Categorie : {}", id);
        return categorieRepository.deleteById(id);
    }
}
