package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.repository.principal.EntiteFinanciereRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.EntiteFinanciereMapper;
import com.gracefinance.gracefinanceapp.service.principal.EntiteFinanciereService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere}.
 */
@Service
@Transactional
public class EntiteFinanciereServiceImpl implements EntiteFinanciereService {

    private static final Logger LOG = LoggerFactory.getLogger(EntiteFinanciereServiceImpl.class);

    private final EntiteFinanciereRepository entiteFinanciereRepository;

    private final EntiteFinanciereMapper entiteFinanciereMapper;

    public EntiteFinanciereServiceImpl(
        EntiteFinanciereRepository entiteFinanciereRepository,
        EntiteFinanciereMapper entiteFinanciereMapper
    ) {
        this.entiteFinanciereRepository = entiteFinanciereRepository;
        this.entiteFinanciereMapper = entiteFinanciereMapper;
    }

    @Override
    public Mono<EntiteFinanciereDTO> save(EntiteFinanciereDTO entiteFinanciereDTO) {
        LOG.debug("Request to save EntiteFinanciere : {}", entiteFinanciereDTO);
        return entiteFinanciereRepository.save(entiteFinanciereMapper.toEntity(entiteFinanciereDTO)).map(entiteFinanciereMapper::toDto);
    }

    @Override
    public Mono<EntiteFinanciereDTO> update(EntiteFinanciereDTO entiteFinanciereDTO) {
        LOG.debug("Request to update EntiteFinanciere : {}", entiteFinanciereDTO);
        return entiteFinanciereRepository.save(entiteFinanciereMapper.toEntity(entiteFinanciereDTO)).map(entiteFinanciereMapper::toDto);
    }

    @Override
    public Mono<EntiteFinanciereDTO> partialUpdate(EntiteFinanciereDTO entiteFinanciereDTO) {
        LOG.debug("Request to partially update EntiteFinanciere : {}", entiteFinanciereDTO);

        return entiteFinanciereRepository
            .findById(entiteFinanciereDTO.getId())
            .map(existingEntiteFinanciere -> {
                entiteFinanciereMapper.partialUpdate(existingEntiteFinanciere, entiteFinanciereDTO);

                return existingEntiteFinanciere;
            })
            .flatMap(entiteFinanciereRepository::save)
            .map(entiteFinanciereMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<EntiteFinanciereDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all EntiteFinancieres");
        return entiteFinanciereRepository.findAllBy(pageable).map(entiteFinanciereMapper::toDto);
    }

    public Flux<EntiteFinanciereDTO> findAllWithEagerRelationships(Pageable pageable) {
        return entiteFinanciereRepository.findAllWithEagerRelationships(pageable).map(entiteFinanciereMapper::toDto);
    }

    public Mono<Long> countAll() {
        return entiteFinanciereRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<EntiteFinanciereDTO> findOne(Long id) {
        LOG.debug("Request to get EntiteFinanciere : {}", id);
        return entiteFinanciereRepository.findOneWithEagerRelationships(id).map(entiteFinanciereMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete EntiteFinanciere : {}", id);
        return entiteFinanciereRepository.deleteById(id);
    }
}
