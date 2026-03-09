package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.repository.principal.EntiteFinanciereRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.EntiteFinanciereCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.EntiteFinanciereMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere}.
 */
@Service
@Transactional
public class EntiteFinanciereService {

    private static final Logger LOG = LoggerFactory.getLogger(EntiteFinanciereService.class);

    private final EntiteFinanciereRepository entiteFinanciereRepository;
    private final EntiteFinanciereMapper entiteFinanciereMapper;

    public EntiteFinanciereService(EntiteFinanciereRepository entiteFinanciereRepository, EntiteFinanciereMapper entiteFinanciereMapper) {
        this.entiteFinanciereRepository = entiteFinanciereRepository;
        this.entiteFinanciereMapper = entiteFinanciereMapper;
    }

    public EntiteFinanciereDTO save(EntiteFinanciereDTO entiteFinanciereDTO) {
        LOG.debug("Request to save EntiteFinanciere : {}", entiteFinanciereDTO);
        return entiteFinanciereMapper.toDto(entiteFinanciereRepository.save(entiteFinanciereMapper.toEntity(entiteFinanciereDTO)));
    }

    public EntiteFinanciereDTO update(EntiteFinanciereDTO entiteFinanciereDTO) {
        LOG.debug("Request to update EntiteFinanciere : {}", entiteFinanciereDTO);
        return entiteFinanciereMapper.toDto(entiteFinanciereRepository.save(entiteFinanciereMapper.toEntity(entiteFinanciereDTO)));
    }

    public Optional<EntiteFinanciereDTO> partialUpdate(EntiteFinanciereDTO entiteFinanciereDTO) {
        LOG.debug("Request to partially update EntiteFinanciere : {}", entiteFinanciereDTO);
        return entiteFinanciereRepository
            .findById(entiteFinanciereDTO.getId())
            .map(existing -> {
                entiteFinanciereMapper.partialUpdate(existing, entiteFinanciereDTO);
                return existing;
            })
            .map(entiteFinanciereRepository::save)
            .map(entiteFinanciereMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<EntiteFinanciereDTO> findOne(Long id) {
        LOG.debug("Request to get EntiteFinanciere : {}", id);
        return entiteFinanciereRepository.findById(id).map(entiteFinanciereMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<EntiteFinanciereDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all EntiteFinancieres");
        return entiteFinanciereRepository.findAllBy(pageable).map(entiteFinanciereMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<EntiteFinanciereDTO> findAllWithEagerRelationships(Pageable pageable) {
        LOG.debug("Request to get all EntiteFinancieres with eager relationships");
        return entiteFinanciereRepository.findAllWithEagerRelationships(pageable).map(entiteFinanciereMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<EntiteFinanciereDTO> findByCriteria(EntiteFinanciereCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get EntiteFinancieres by criteria : {}", criteria);
        return entiteFinanciereRepository.findAll(pageable).map(entiteFinanciereMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(EntiteFinanciereCriteria criteria) {
        LOG.debug("Request to count EntiteFinancieres by criteria : {}", criteria);
        return entiteFinanciereRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all EntiteFinancieres");
        return entiteFinanciereRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete EntiteFinanciere : {}", id);
        entiteFinanciereRepository.deleteById(id);
    }
}
