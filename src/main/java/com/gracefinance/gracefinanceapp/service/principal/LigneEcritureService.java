package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.repository.principal.LigneEcritureRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.LigneEcritureCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.LigneEcritureDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.LigneEcritureMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture}.
 */
@Service
@Transactional
public class LigneEcritureService {

    private static final Logger LOG = LoggerFactory.getLogger(LigneEcritureService.class);

    private final LigneEcritureRepository ligneEcritureRepository;
    private final LigneEcritureMapper ligneEcritureMapper;

    public LigneEcritureService(LigneEcritureRepository ligneEcritureRepository, LigneEcritureMapper ligneEcritureMapper) {
        this.ligneEcritureRepository = ligneEcritureRepository;
        this.ligneEcritureMapper = ligneEcritureMapper;
    }

    public LigneEcritureDTO save(LigneEcritureDTO ligneEcritureDTO) {
        LOG.debug("Request to save LigneEcriture : {}", ligneEcritureDTO);
        return ligneEcritureMapper.toDto(ligneEcritureRepository.save(ligneEcritureMapper.toEntity(ligneEcritureDTO)));
    }

    public LigneEcritureDTO update(LigneEcritureDTO ligneEcritureDTO) {
        LOG.debug("Request to update LigneEcriture : {}", ligneEcritureDTO);
        return ligneEcritureMapper.toDto(ligneEcritureRepository.save(ligneEcritureMapper.toEntity(ligneEcritureDTO)));
    }

    public Optional<LigneEcritureDTO> partialUpdate(LigneEcritureDTO ligneEcritureDTO) {
        LOG.debug("Request to partially update LigneEcriture : {}", ligneEcritureDTO);
        return ligneEcritureRepository
            .findById(ligneEcritureDTO.getId())
            .map(existing -> {
                ligneEcritureMapper.partialUpdate(existing, ligneEcritureDTO);
                return existing;
            })
            .map(ligneEcritureRepository::save)
            .map(ligneEcritureMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<LigneEcritureDTO> findOne(Long id) {
        LOG.debug("Request to get LigneEcriture : {}", id);
        return ligneEcritureRepository.findById(id).map(ligneEcritureMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<LigneEcritureDTO> findAll() {
        LOG.debug("Request to get all LigneEcritures");
        return ligneEcritureMapper.toDto(ligneEcritureRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<LigneEcritureDTO> findByCriteria(LigneEcritureCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get LigneEcritures by criteria : {}", criteria);
        return ligneEcritureRepository.findAll(pageable).map(ligneEcritureMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(LigneEcritureCriteria criteria) {
        LOG.debug("Request to count LigneEcritures by criteria : {}", criteria);
        return ligneEcritureRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all LigneEcritures");
        return ligneEcritureRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete LigneEcriture : {}", id);
        ligneEcritureRepository.deleteById(id);
    }
}
