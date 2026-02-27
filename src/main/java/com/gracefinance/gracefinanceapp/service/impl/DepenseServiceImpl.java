package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.domain.criteria.DepenseCriteria;
import com.gracefinance.gracefinanceapp.repository.principal.DepenseRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.DepenseDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.DepenseMapper;
import com.gracefinance.gracefinanceapp.service.principal.DepenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.principal.Depense}.
 */
@Service
@Transactional
public class DepenseServiceImpl implements DepenseService {

    private static final Logger LOG = LoggerFactory.getLogger(DepenseServiceImpl.class);

    private final DepenseRepository depenseRepository;

    private final DepenseMapper depenseMapper;

    public DepenseServiceImpl(DepenseRepository depenseRepository, DepenseMapper depenseMapper) {
        this.depenseRepository = depenseRepository;
        this.depenseMapper = depenseMapper;
    }

    @Override
    public Mono<DepenseDTO> save(DepenseDTO depenseDTO) {
        LOG.debug("Request to save Depense : {}", depenseDTO);
        return depenseRepository.save(depenseMapper.toEntity(depenseDTO)).map(depenseMapper::toDto);
    }

    @Override
    public Mono<DepenseDTO> update(DepenseDTO depenseDTO) {
        LOG.debug("Request to update Depense : {}", depenseDTO);
        return depenseRepository.save(depenseMapper.toEntity(depenseDTO)).map(depenseMapper::toDto);
    }

    @Override
    public Mono<DepenseDTO> partialUpdate(DepenseDTO depenseDTO) {
        LOG.debug("Request to partially update Depense : {}", depenseDTO);

        return depenseRepository
            .findById(depenseDTO.getId())
            .map(existingDepense -> {
                depenseMapper.partialUpdate(existingDepense, depenseDTO);

                return existingDepense;
            })
            .flatMap(depenseRepository::save)
            .map(depenseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<DepenseDTO> findByCriteria(DepenseCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all Depenses by Criteria");
        return depenseRepository.findByCriteria(criteria, pageable).map(depenseMapper::toDto);
    }

    /**
     * Find the count of depenses by criteria.
     * @param criteria filtering criteria
     * @return the count of depenses
     */
    public Mono<Long> countByCriteria(DepenseCriteria criteria) {
        LOG.debug("Request to get the count of all Depenses by Criteria");
        return depenseRepository.countByCriteria(criteria);
    }

    public Mono<Long> countAll() {
        return depenseRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<DepenseDTO> findOne(Long id) {
        LOG.debug("Request to get Depense : {}", id);
        return depenseRepository.findById(id).map(depenseMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Depense : {}", id);
        return depenseRepository.deleteById(id);
    }
}
