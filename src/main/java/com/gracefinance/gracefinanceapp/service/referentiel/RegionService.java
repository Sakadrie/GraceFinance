package com.gracefinance.gracefinanceapp.service.referentiel;

import com.gracefinance.gracefinanceapp.repository.referentiel.RegionRepository;
import com.gracefinance.gracefinanceapp.service.criteria.referentiel.RegionCriteria;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.RegionDTO;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.RegionMapper;
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
 * {@link com.gracefinance.gracefinanceapp.domain.referentiel.Region}.
 */
@Service
@Transactional
public class RegionService {

    private static final Logger LOG = LoggerFactory.getLogger(RegionService.class);

    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public RegionService(RegionRepository regionRepository, RegionMapper regionMapper) {
        this.regionRepository = regionRepository;
        this.regionMapper = regionMapper;
    }

    public RegionDTO save(RegionDTO regionDTO) {
        LOG.debug("Request to save Region : {}", regionDTO);
        return regionMapper.toDto(regionRepository.save(regionMapper.toEntity(regionDTO)));
    }

    public RegionDTO update(RegionDTO regionDTO) {
        LOG.debug("Request to update Region : {}", regionDTO);
        return regionMapper.toDto(regionRepository.save(regionMapper.toEntity(regionDTO)));
    }

    public Optional<RegionDTO> partialUpdate(RegionDTO regionDTO) {
        LOG.debug("Request to partially update Region : {}", regionDTO);
        return regionRepository
            .findById(regionDTO.getId())
            .map(existing -> {
                regionMapper.partialUpdate(existing, regionDTO);
                return existing;
            })
            .map(regionRepository::save)
            .map(regionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<RegionDTO> findOne(Long id) {
        LOG.debug("Request to get Region : {}", id);
        return regionRepository.findById(id).map(regionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<RegionDTO> findAll() {
        LOG.debug("Request to get all Regions");
        return regionMapper.toDto(regionRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<RegionDTO> findByCriteria(RegionCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get Regions by criteria : {}", criteria);
        return regionRepository.findAll(pageable).map(regionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(RegionCriteria criteria) {
        LOG.debug("Request to count Regions by criteria : {}", criteria);
        return regionRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all Regions");
        return regionRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Region : {}", id);
        regionRepository.deleteById(id);
    }
}
