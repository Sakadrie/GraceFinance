package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.domain.criteria.RecetteCriteria;
import com.gracefinance.gracefinanceapp.repository.principal.RecetteRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.RecetteDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.RecetteMapper;
import com.gracefinance.gracefinanceapp.service.principal.RecetteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.principal.Recette}.
 */
@Service
@Transactional
public class RecetteServiceImpl implements RecetteService {

    private static final Logger LOG = LoggerFactory.getLogger(RecetteServiceImpl.class);

    private final RecetteRepository recetteRepository;

    private final RecetteMapper recetteMapper;

    public RecetteServiceImpl(RecetteRepository recetteRepository, RecetteMapper recetteMapper) {
        this.recetteRepository = recetteRepository;
        this.recetteMapper = recetteMapper;
    }

    @Override
    public Mono<RecetteDTO> save(RecetteDTO recetteDTO) {
        LOG.debug("Request to save Recette : {}", recetteDTO);
        return recetteRepository.save(recetteMapper.toEntity(recetteDTO)).map(recetteMapper::toDto);
    }

    @Override
    public Mono<RecetteDTO> update(RecetteDTO recetteDTO) {
        LOG.debug("Request to update Recette : {}", recetteDTO);
        return recetteRepository.save(recetteMapper.toEntity(recetteDTO)).map(recetteMapper::toDto);
    }

    @Override
    public Mono<RecetteDTO> partialUpdate(RecetteDTO recetteDTO) {
        LOG.debug("Request to partially update Recette : {}", recetteDTO);

        return recetteRepository
            .findById(recetteDTO.getId())
            .map(existingRecette -> {
                recetteMapper.partialUpdate(existingRecette, recetteDTO);

                return existingRecette;
            })
            .flatMap(recetteRepository::save)
            .map(recetteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<RecetteDTO> findByCriteria(RecetteCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all Recettes by Criteria");
        return recetteRepository.findByCriteria(criteria, pageable).map(recetteMapper::toDto);
    }

    /**
     * Find the count of recettes by criteria.
     * @param criteria filtering criteria
     * @return the count of recettes
     */
    public Mono<Long> countByCriteria(RecetteCriteria criteria) {
        LOG.debug("Request to get the count of all Recettes by Criteria");
        return recetteRepository.countByCriteria(criteria);
    }

    public Mono<Long> countAll() {
        return recetteRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<RecetteDTO> findOne(Long id) {
        LOG.debug("Request to get Recette : {}", id);
        return recetteRepository.findById(id).map(recetteMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Recette : {}", id);
        return recetteRepository.deleteById(id);
    }
}
