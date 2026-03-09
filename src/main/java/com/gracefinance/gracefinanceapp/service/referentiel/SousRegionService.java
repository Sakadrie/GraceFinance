package com.gracefinance.gracefinanceapp.service.referentiel;

import com.gracefinance.gracefinanceapp.repository.referentiel.SousRegionRepository;
import com.gracefinance.gracefinanceapp.service.criteria.referentiel.SousRegionCriteria;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.SousRegionDTO;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.SousRegionMapper;
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
 * {@link com.gracefinance.gracefinanceapp.domain.referentiel.SousRegion}.
 */
@Service
@Transactional
public class SousRegionService {

    private static final Logger LOG = LoggerFactory.getLogger(SousRegionService.class);

    private final SousRegionRepository sousRegionRepository;
    private final SousRegionMapper sousRegionMapper;

    public SousRegionService(SousRegionRepository sousRegionRepository, SousRegionMapper sousRegionMapper) {
        this.sousRegionRepository = sousRegionRepository;
        this.sousRegionMapper = sousRegionMapper;
    }

    public SousRegionDTO save(SousRegionDTO sousRegionDTO) {
        LOG.debug("Request to save SousRegion : {}", sousRegionDTO);
        return sousRegionMapper.toDto(sousRegionRepository.save(sousRegionMapper.toEntity(sousRegionDTO)));
    }

    public SousRegionDTO update(SousRegionDTO sousRegionDTO) {
        LOG.debug("Request to update SousRegion : {}", sousRegionDTO);
        return sousRegionMapper.toDto(sousRegionRepository.save(sousRegionMapper.toEntity(sousRegionDTO)));
    }

    public Optional<SousRegionDTO> partialUpdate(SousRegionDTO sousRegionDTO) {
        LOG.debug("Request to partially update SousRegion : {}", sousRegionDTO);
        return sousRegionRepository
            .findById(sousRegionDTO.getId())
            .map(existing -> {
                sousRegionMapper.partialUpdate(existing, sousRegionDTO);
                return existing;
            })
            .map(sousRegionRepository::save)
            .map(sousRegionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<SousRegionDTO> findOne(Long id) {
        LOG.debug("Request to get SousRegion : {}", id);
        return sousRegionRepository.findById(id).map(sousRegionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<SousRegionDTO> findAll() {
        LOG.debug("Request to get all SousRegions");
        return sousRegionMapper.toDto(sousRegionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<SousRegionDTO> findByCriteria(SousRegionCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get SousRegions by criteria : {}", criteria);
        return sousRegionRepository.findAll(pageable).map(sousRegionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(SousRegionCriteria criteria) {
        LOG.debug("Request to count SousRegions by criteria : {}", criteria);
        return sousRegionRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all SousRegions");
        return sousRegionRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete SousRegion : {}", id);
        sousRegionRepository.deleteById(id);
    }
}
