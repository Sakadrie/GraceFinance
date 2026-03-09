package com.gracefinance.gracefinanceapp.service.referentiel;

import com.gracefinance.gracefinanceapp.repository.referentiel.DistrictRepository;
import com.gracefinance.gracefinanceapp.service.criteria.referentiel.DistrictCriteria;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.DistrictDTO;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.DistrictMapper;
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
 * {@link com.gracefinance.gracefinanceapp.domain.referentiel.District}.
 */
@Service
@Transactional
public class DistrictService {

    private static final Logger LOG = LoggerFactory.getLogger(DistrictService.class);

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    public DistrictService(DistrictRepository districtRepository, DistrictMapper districtMapper) {
        this.districtRepository = districtRepository;
        this.districtMapper = districtMapper;
    }

    public DistrictDTO save(DistrictDTO districtDTO) {
        LOG.debug("Request to save District : {}", districtDTO);
        return districtMapper.toDto(districtRepository.save(districtMapper.toEntity(districtDTO)));
    }

    public DistrictDTO update(DistrictDTO districtDTO) {
        LOG.debug("Request to update District : {}", districtDTO);
        return districtMapper.toDto(districtRepository.save(districtMapper.toEntity(districtDTO)));
    }

    public Optional<DistrictDTO> partialUpdate(DistrictDTO districtDTO) {
        LOG.debug("Request to partially update District : {}", districtDTO);
        return districtRepository
            .findById(districtDTO.getId())
            .map(existing -> {
                districtMapper.partialUpdate(existing, districtDTO);
                return existing;
            })
            .map(districtRepository::save)
            .map(districtMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<DistrictDTO> findOne(Long id) {
        LOG.debug("Request to get District : {}", id);
        return districtRepository.findById(id).map(districtMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<DistrictDTO> findAll() {
        LOG.debug("Request to get all Districts");
        return districtMapper.toDto(districtRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<DistrictDTO> findByCriteria(DistrictCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get Districts by criteria : {}", criteria);
        return districtRepository.findAll(pageable).map(districtMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(DistrictCriteria criteria) {
        LOG.debug("Request to count Districts by criteria : {}", criteria);
        return districtRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all Districts");
        return districtRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete District : {}", id);
        districtRepository.deleteById(id);
    }
}
