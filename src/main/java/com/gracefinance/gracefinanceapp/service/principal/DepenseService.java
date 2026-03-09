package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.repository.principal.DepenseRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.DepenseCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.DepenseDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.DepenseMapper;
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
 * {@link com.gracefinance.gracefinanceapp.domain.principal.Depense}.
 */
@Service
@Transactional
public class DepenseService {

    private static final Logger LOG = LoggerFactory.getLogger(DepenseService.class);

    private final DepenseRepository depenseRepository;
    private final DepenseMapper depenseMapper;

    public DepenseService(DepenseRepository depenseRepository, DepenseMapper depenseMapper) {
        this.depenseRepository = depenseRepository;
        this.depenseMapper = depenseMapper;
    }

    public DepenseDTO save(DepenseDTO depenseDTO) {
        LOG.debug("Request to save Depense : {}", depenseDTO);
        return depenseMapper.toDto(depenseRepository.save(depenseMapper.toEntity(depenseDTO)));
    }

    public DepenseDTO update(DepenseDTO depenseDTO) {
        LOG.debug("Request to update Depense : {}", depenseDTO);
        return depenseMapper.toDto(depenseRepository.save(depenseMapper.toEntity(depenseDTO)));
    }

    public Optional<DepenseDTO> partialUpdate(DepenseDTO depenseDTO) {
        LOG.debug("Request to partially update Depense : {}", depenseDTO);
        return depenseRepository
            .findById(depenseDTO.getId())
            .map(existing -> {
                depenseMapper.partialUpdate(existing, depenseDTO);
                return existing;
            })
            .map(depenseRepository::save)
            .map(depenseMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<DepenseDTO> findOne(Long id) {
        LOG.debug("Request to get Depense : {}", id);
        return depenseRepository.findById(id).map(depenseMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<DepenseDTO> findAll() {
        LOG.debug("Request to get all Depenses");
        return depenseMapper.toDto(depenseRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<DepenseDTO> findByCriteria(DepenseCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get Depenses by criteria : {}", criteria);
        return depenseRepository.findAll(pageable).map(depenseMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(DepenseCriteria criteria) {
        LOG.debug("Request to count Depenses by criteria : {}", criteria);
        return depenseRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all Depenses");
        return depenseRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Depense : {}", id);
        depenseRepository.deleteById(id);
    }
}
