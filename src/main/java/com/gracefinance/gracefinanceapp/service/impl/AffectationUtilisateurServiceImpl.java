package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.repository.security.AffectationUtilisateurRepository;
import com.gracefinance.gracefinanceapp.service.criteria.security.AffectationUtilisateurCriteria;
import com.gracefinance.gracefinanceapp.service.dto.security.AffectationUtilisateurDTO;
import com.gracefinance.gracefinanceapp.service.mapper.security.AffectationUtilisateurMapper;
import com.gracefinance.gracefinanceapp.service.security.AffectationUtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur}.
 */
@Service
@Transactional
public class AffectationUtilisateurServiceImpl implements AffectationUtilisateurService {

    private static final Logger LOG = LoggerFactory.getLogger(AffectationUtilisateurServiceImpl.class);

    private final AffectationUtilisateurRepository affectationUtilisateurRepository;

    private final AffectationUtilisateurMapper affectationUtilisateurMapper;

    public AffectationUtilisateurServiceImpl(
        AffectationUtilisateurRepository affectationUtilisateurRepository,
        AffectationUtilisateurMapper affectationUtilisateurMapper
    ) {
        this.affectationUtilisateurRepository = affectationUtilisateurRepository;
        this.affectationUtilisateurMapper = affectationUtilisateurMapper;
    }

    @Override
    public Mono<AffectationUtilisateurDTO> save(AffectationUtilisateurDTO affectationUtilisateurDTO) {
        LOG.debug("Request to save AffectationUtilisateur : {}", affectationUtilisateurDTO);
        return affectationUtilisateurRepository
            .save(affectationUtilisateurMapper.toEntity(affectationUtilisateurDTO))
            .map(affectationUtilisateurMapper::toDto);
    }

    @Override
    public Mono<AffectationUtilisateurDTO> update(AffectationUtilisateurDTO affectationUtilisateurDTO) {
        LOG.debug("Request to update AffectationUtilisateur : {}", affectationUtilisateurDTO);
        return affectationUtilisateurRepository
            .save(affectationUtilisateurMapper.toEntity(affectationUtilisateurDTO))
            .map(affectationUtilisateurMapper::toDto);
    }

    @Override
    public Mono<AffectationUtilisateurDTO> partialUpdate(AffectationUtilisateurDTO affectationUtilisateurDTO) {
        LOG.debug("Request to partially update AffectationUtilisateur : {}", affectationUtilisateurDTO);

        return affectationUtilisateurRepository
            .findById(affectationUtilisateurDTO.getId())
            .map(existingAffectationUtilisateur -> {
                affectationUtilisateurMapper.partialUpdate(existingAffectationUtilisateur, affectationUtilisateurDTO);

                return existingAffectationUtilisateur;
            })
            .flatMap(affectationUtilisateurRepository::save)
            .map(affectationUtilisateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<AffectationUtilisateurDTO> findByCriteria(AffectationUtilisateurCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all AffectationUtilisateurs by Criteria");
        return affectationUtilisateurRepository.findByCriteria(criteria, pageable).map(affectationUtilisateurMapper::toDto);
    }

    /**
     * Find the count of affectationUtilisateurs by criteria.
     * @param criteria filtering criteria
     * @return the count of affectationUtilisateurs
     */
    public Mono<Long> countByCriteria(AffectationUtilisateurCriteria criteria) {
        LOG.debug("Request to get the count of all AffectationUtilisateurs by Criteria");
        return affectationUtilisateurRepository.countByCriteria(criteria);
    }

    public Flux<AffectationUtilisateurDTO> findAllWithEagerRelationships(Pageable pageable) {
        return affectationUtilisateurRepository.findAllWithEagerRelationships(pageable).map(affectationUtilisateurMapper::toDto);
    }

    public Mono<Long> countAll() {
        return affectationUtilisateurRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<AffectationUtilisateurDTO> findOne(Long id) {
        LOG.debug("Request to get AffectationUtilisateur : {}", id);
        return affectationUtilisateurRepository.findOneWithEagerRelationships(id).map(affectationUtilisateurMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete AffectationUtilisateur : {}", id);
        return affectationUtilisateurRepository.deleteById(id);
    }
}
