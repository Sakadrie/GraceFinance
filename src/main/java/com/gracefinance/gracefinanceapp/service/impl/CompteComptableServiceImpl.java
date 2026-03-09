package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.repository.principal.CompteComptableRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.CompteComptableCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.CompteComptableDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.CompteComptableMapper;
import com.gracefinance.gracefinanceapp.service.principal.CompteComptableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.principal.CompteComptable}.
 */
@Service
@Transactional
public class CompteComptableServiceImpl implements CompteComptableService {

    private static final Logger LOG = LoggerFactory.getLogger(CompteComptableServiceImpl.class);

    private final CompteComptableRepository compteComptableRepository;

    private final CompteComptableMapper compteComptableMapper;

    public CompteComptableServiceImpl(CompteComptableRepository compteComptableRepository, CompteComptableMapper compteComptableMapper) {
        this.compteComptableRepository = compteComptableRepository;
        this.compteComptableMapper = compteComptableMapper;
    }

    @Override
    public Mono<CompteComptableDTO> save(CompteComptableDTO compteComptableDTO) {
        LOG.debug("Request to save CompteComptable : {}", compteComptableDTO);
        return compteComptableRepository.save(compteComptableMapper.toEntity(compteComptableDTO)).map(compteComptableMapper::toDto);
    }

    @Override
    public Mono<CompteComptableDTO> update(CompteComptableDTO compteComptableDTO) {
        LOG.debug("Request to update CompteComptable : {}", compteComptableDTO);
        return compteComptableRepository.save(compteComptableMapper.toEntity(compteComptableDTO)).map(compteComptableMapper::toDto);
    }

    @Override
    public Mono<CompteComptableDTO> partialUpdate(CompteComptableDTO compteComptableDTO) {
        LOG.debug("Request to partially update CompteComptable : {}", compteComptableDTO);

        return compteComptableRepository
            .findById(compteComptableDTO.getId())
            .map(existingCompteComptable -> {
                compteComptableMapper.partialUpdate(existingCompteComptable, compteComptableDTO);

                return existingCompteComptable;
            })
            .flatMap(compteComptableRepository::save)
            .map(compteComptableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<CompteComptableDTO> findByCriteria(CompteComptableCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all CompteComptables by Criteria");
        return compteComptableRepository.findByCriteria(criteria, pageable).map(compteComptableMapper::toDto);
    }

    /**
     * Find the count of compteComptables by criteria.
     * @param criteria filtering criteria
     * @return the count of compteComptables
     */
    public Mono<Long> countByCriteria(CompteComptableCriteria criteria) {
        LOG.debug("Request to get the count of all CompteComptables by Criteria");
        return compteComptableRepository.countByCriteria(criteria);
    }

    public Mono<Long> countAll() {
        return compteComptableRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<CompteComptableDTO> findOne(Long id) {
        LOG.debug("Request to get CompteComptable : {}", id);
        return compteComptableRepository.findById(id).map(compteComptableMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete CompteComptable : {}", id);
        return compteComptableRepository.deleteById(id);
    }
}
