package com.gracefinance.gracefinanceapp.service.security;

import com.gracefinance.gracefinanceapp.repository.security.AffectationUtilisateurRepository;
import com.gracefinance.gracefinanceapp.service.criteria.security.AffectationUtilisateurCriteria;
import com.gracefinance.gracefinanceapp.service.dto.security.AffectationUtilisateurDTO;
import com.gracefinance.gracefinanceapp.service.mapper.security.AffectationUtilisateurMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur}.
 */
@Service
@Transactional
public class AffectationUtilisateurService {

    private static final Logger LOG = LoggerFactory.getLogger(AffectationUtilisateurService.class);

    private final AffectationUtilisateurRepository affectationUtilisateurRepository;
    private final AffectationUtilisateurMapper affectationUtilisateurMapper;

    public AffectationUtilisateurService(
        AffectationUtilisateurRepository affectationUtilisateurRepository,
        AffectationUtilisateurMapper affectationUtilisateurMapper
    ) {
        this.affectationUtilisateurRepository = affectationUtilisateurRepository;
        this.affectationUtilisateurMapper = affectationUtilisateurMapper;
    }

    public AffectationUtilisateurDTO save(AffectationUtilisateurDTO affectationUtilisateurDTO) {
        LOG.debug("Request to save AffectationUtilisateur : {}", affectationUtilisateurDTO);
        return affectationUtilisateurMapper.toDto(
            affectationUtilisateurRepository.save(affectationUtilisateurMapper.toEntity(affectationUtilisateurDTO))
        );
    }

    public AffectationUtilisateurDTO update(AffectationUtilisateurDTO affectationUtilisateurDTO) {
        LOG.debug("Request to update AffectationUtilisateur : {}", affectationUtilisateurDTO);
        return affectationUtilisateurMapper.toDto(
            affectationUtilisateurRepository.save(affectationUtilisateurMapper.toEntity(affectationUtilisateurDTO))
        );
    }

    public Optional<AffectationUtilisateurDTO> partialUpdate(AffectationUtilisateurDTO affectationUtilisateurDTO) {
        LOG.debug("Request to partially update AffectationUtilisateur : {}", affectationUtilisateurDTO);
        return affectationUtilisateurRepository
            .findById(affectationUtilisateurDTO.getId())
            .map(existing -> {
                affectationUtilisateurMapper.partialUpdate(existing, affectationUtilisateurDTO);
                return existing;
            })
            .map(affectationUtilisateurRepository::save)
            .map(affectationUtilisateurMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<AffectationUtilisateurDTO> findOne(Long id) {
        LOG.debug("Request to get AffectationUtilisateur : {}", id);
        return affectationUtilisateurRepository.findById(id).map(affectationUtilisateurMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<AffectationUtilisateurDTO> findByCriteria(AffectationUtilisateurCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get AffectationUtilisateurs by criteria : {}", criteria);
        return affectationUtilisateurRepository.findAll(pageable).map(affectationUtilisateurMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<AffectationUtilisateurDTO> findAllWithEagerRelationships(Pageable pageable) {
        LOG.debug("Request to get all AffectationUtilisateurs with eager relationships");
        return affectationUtilisateurRepository.findAllWithEagerRelationships(pageable).map(affectationUtilisateurMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(AffectationUtilisateurCriteria criteria) {
        LOG.debug("Request to count AffectationUtilisateurs by criteria : {}", criteria);
        return affectationUtilisateurRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all AffectationUtilisateurs");
        return affectationUtilisateurRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete AffectationUtilisateur : {}", id);
        affectationUtilisateurRepository.deleteById(id);
    }
}
