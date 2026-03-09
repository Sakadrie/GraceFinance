package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.repository.principal.CompteComptableRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.CompteComptableCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.CompteComptableDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.CompteComptableMapper;
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
 * {@link com.gracefinance.gracefinanceapp.domain.principal.CompteComptable}.
 */
@Service
@Transactional
public class CompteComptableService {

    private static final Logger LOG = LoggerFactory.getLogger(CompteComptableService.class);

    private final CompteComptableRepository compteComptableRepository;
    private final CompteComptableMapper compteComptableMapper;

    public CompteComptableService(CompteComptableRepository compteComptableRepository, CompteComptableMapper compteComptableMapper) {
        this.compteComptableRepository = compteComptableRepository;
        this.compteComptableMapper = compteComptableMapper;
    }

    public CompteComptableDTO save(CompteComptableDTO compteComptableDTO) {
        LOG.debug("Request to save CompteComptable : {}", compteComptableDTO);
        return compteComptableMapper.toDto(compteComptableRepository.save(compteComptableMapper.toEntity(compteComptableDTO)));
    }

    public CompteComptableDTO update(CompteComptableDTO compteComptableDTO) {
        LOG.debug("Request to update CompteComptable : {}", compteComptableDTO);
        return compteComptableMapper.toDto(compteComptableRepository.save(compteComptableMapper.toEntity(compteComptableDTO)));
    }

    public Optional<CompteComptableDTO> partialUpdate(CompteComptableDTO compteComptableDTO) {
        LOG.debug("Request to partially update CompteComptable : {}", compteComptableDTO);
        return compteComptableRepository
            .findById(compteComptableDTO.getId())
            .map(existing -> {
                compteComptableMapper.partialUpdate(existing, compteComptableDTO);
                return existing;
            })
            .map(compteComptableRepository::save)
            .map(compteComptableMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<CompteComptableDTO> findOne(Long id) {
        LOG.debug("Request to get CompteComptable : {}", id);
        return compteComptableRepository.findById(id).map(compteComptableMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<CompteComptableDTO> findAll() {
        LOG.debug("Request to get all CompteComptables");
        return compteComptableMapper.toDto(compteComptableRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<CompteComptableDTO> findByCriteria(CompteComptableCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get CompteComptables by criteria : {}", criteria);
        return compteComptableRepository.findAll(pageable).map(compteComptableMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(CompteComptableCriteria criteria) {
        LOG.debug("Request to count CompteComptables by criteria : {}", criteria);
        return compteComptableRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all CompteComptables");
        return compteComptableRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete CompteComptable : {}", id);
        compteComptableRepository.deleteById(id);
    }
}
