package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.repository.principal.LigneEcritureRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.LigneEcritureCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.LigneEcritureDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.LigneEcritureMapper;
import com.gracefinance.gracefinanceapp.service.principal.LigneEcritureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture}.
 */
@Service
@Transactional
public class LigneEcritureServiceImpl implements LigneEcritureService {

    private static final Logger LOG = LoggerFactory.getLogger(LigneEcritureServiceImpl.class);

    private final LigneEcritureRepository ligneEcritureRepository;

    private final LigneEcritureMapper ligneEcritureMapper;

    public LigneEcritureServiceImpl(LigneEcritureRepository ligneEcritureRepository, LigneEcritureMapper ligneEcritureMapper) {
        this.ligneEcritureRepository = ligneEcritureRepository;
        this.ligneEcritureMapper = ligneEcritureMapper;
    }

    @Override
    public Mono<LigneEcritureDTO> save(LigneEcritureDTO ligneEcritureDTO) {
        LOG.debug("Request to save LigneEcriture : {}", ligneEcritureDTO);
        return ligneEcritureRepository.save(ligneEcritureMapper.toEntity(ligneEcritureDTO)).map(ligneEcritureMapper::toDto);
    }

    @Override
    public Mono<LigneEcritureDTO> update(LigneEcritureDTO ligneEcritureDTO) {
        LOG.debug("Request to update LigneEcriture : {}", ligneEcritureDTO);
        return ligneEcritureRepository.save(ligneEcritureMapper.toEntity(ligneEcritureDTO)).map(ligneEcritureMapper::toDto);
    }

    @Override
    public Mono<LigneEcritureDTO> partialUpdate(LigneEcritureDTO ligneEcritureDTO) {
        LOG.debug("Request to partially update LigneEcriture : {}", ligneEcritureDTO);

        return ligneEcritureRepository
            .findById(ligneEcritureDTO.getId())
            .map(existingLigneEcriture -> {
                ligneEcritureMapper.partialUpdate(existingLigneEcriture, ligneEcritureDTO);

                return existingLigneEcriture;
            })
            .flatMap(ligneEcritureRepository::save)
            .map(ligneEcritureMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<LigneEcritureDTO> findByCriteria(LigneEcritureCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all LigneEcritures by Criteria");
        return ligneEcritureRepository.findByCriteria(criteria, pageable).map(ligneEcritureMapper::toDto);
    }

    /**
     * Find the count of ligneEcritures by criteria.
     * @param criteria filtering criteria
     * @return the count of ligneEcritures
     */
    public Mono<Long> countByCriteria(LigneEcritureCriteria criteria) {
        LOG.debug("Request to get the count of all LigneEcritures by Criteria");
        return ligneEcritureRepository.countByCriteria(criteria);
    }

    public Mono<Long> countAll() {
        return ligneEcritureRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<LigneEcritureDTO> findOne(Long id) {
        LOG.debug("Request to get LigneEcriture : {}", id);
        return ligneEcritureRepository.findById(id).map(ligneEcritureMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete LigneEcriture : {}", id);
        return ligneEcritureRepository.deleteById(id);
    }
}
