package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.repository.principal.EcritureComptableRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.EcritureComptableCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.EcritureComptableDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.EcritureComptableMapper;
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
 * {@link com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable}.
 */
@Service
@Transactional
public class EcritureComptableService {

    private static final Logger LOG = LoggerFactory.getLogger(EcritureComptableService.class);

    private final EcritureComptableRepository ecritureComptableRepository;
    private final EcritureComptableMapper ecritureComptableMapper;

    public EcritureComptableService(
        EcritureComptableRepository ecritureComptableRepository,
        EcritureComptableMapper ecritureComptableMapper
    ) {
        this.ecritureComptableRepository = ecritureComptableRepository;
        this.ecritureComptableMapper = ecritureComptableMapper;
    }

    public EcritureComptableDTO save(EcritureComptableDTO ecritureComptableDTO) {
        LOG.debug("Request to save EcritureComptable : {}", ecritureComptableDTO);
        return ecritureComptableMapper.toDto(ecritureComptableRepository.save(ecritureComptableMapper.toEntity(ecritureComptableDTO)));
    }

    public EcritureComptableDTO update(EcritureComptableDTO ecritureComptableDTO) {
        LOG.debug("Request to update EcritureComptable : {}", ecritureComptableDTO);
        return ecritureComptableMapper.toDto(ecritureComptableRepository.save(ecritureComptableMapper.toEntity(ecritureComptableDTO)));
    }

    public Optional<EcritureComptableDTO> partialUpdate(EcritureComptableDTO ecritureComptableDTO) {
        LOG.debug("Request to partially update EcritureComptable : {}", ecritureComptableDTO);
        return ecritureComptableRepository
            .findById(ecritureComptableDTO.getId())
            .map(existing -> {
                ecritureComptableMapper.partialUpdate(existing, ecritureComptableDTO);
                return existing;
            })
            .map(ecritureComptableRepository::save)
            .map(ecritureComptableMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<EcritureComptableDTO> findOne(Long id) {
        LOG.debug("Request to get EcritureComptable : {}", id);
        return ecritureComptableRepository.findById(id).map(ecritureComptableMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<EcritureComptableDTO> findAll() {
        LOG.debug("Request to get all EcritureComptables");
        return ecritureComptableMapper.toDto(ecritureComptableRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<EcritureComptableDTO> findByCriteria(EcritureComptableCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get EcritureComptables by criteria : {}", criteria);
        return ecritureComptableRepository.findAll(pageable).map(ecritureComptableMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(EcritureComptableCriteria criteria) {
        LOG.debug("Request to count EcritureComptables by criteria : {}", criteria);
        return ecritureComptableRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all EcritureComptables");
        return ecritureComptableRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete EcritureComptable : {}", id);
        ecritureComptableRepository.deleteById(id);
    }
}
