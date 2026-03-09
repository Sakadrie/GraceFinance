package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.repository.principal.EcritureComptableRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.EcritureComptableCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.EcritureComptableDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.EcritureComptableMapper;
import com.gracefinance.gracefinanceapp.service.principal.EcritureComptableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable}.
 */
@Service
@Transactional
public class EcritureComptableServiceImpl implements EcritureComptableService {

    private static final Logger LOG = LoggerFactory.getLogger(EcritureComptableServiceImpl.class);

    private final EcritureComptableRepository ecritureComptableRepository;

    private final EcritureComptableMapper ecritureComptableMapper;

    public EcritureComptableServiceImpl(
        EcritureComptableRepository ecritureComptableRepository,
        EcritureComptableMapper ecritureComptableMapper
    ) {
        this.ecritureComptableRepository = ecritureComptableRepository;
        this.ecritureComptableMapper = ecritureComptableMapper;
    }

    @Override
    public Mono<EcritureComptableDTO> save(EcritureComptableDTO ecritureComptableDTO) {
        LOG.debug("Request to save EcritureComptable : {}", ecritureComptableDTO);
        return ecritureComptableRepository.save(ecritureComptableMapper.toEntity(ecritureComptableDTO)).map(ecritureComptableMapper::toDto);
    }

    @Override
    public Mono<EcritureComptableDTO> update(EcritureComptableDTO ecritureComptableDTO) {
        LOG.debug("Request to update EcritureComptable : {}", ecritureComptableDTO);
        return ecritureComptableRepository.save(ecritureComptableMapper.toEntity(ecritureComptableDTO)).map(ecritureComptableMapper::toDto);
    }

    @Override
    public Mono<EcritureComptableDTO> partialUpdate(EcritureComptableDTO ecritureComptableDTO) {
        LOG.debug("Request to partially update EcritureComptable : {}", ecritureComptableDTO);

        return ecritureComptableRepository
            .findById(ecritureComptableDTO.getId())
            .map(existingEcritureComptable -> {
                ecritureComptableMapper.partialUpdate(existingEcritureComptable, ecritureComptableDTO);

                return existingEcritureComptable;
            })
            .flatMap(ecritureComptableRepository::save)
            .map(ecritureComptableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<EcritureComptableDTO> findByCriteria(EcritureComptableCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all EcritureComptables by Criteria");
        return ecritureComptableRepository.findByCriteria(criteria, pageable).map(ecritureComptableMapper::toDto);
    }

    /**
     * Find the count of ecritureComptables by criteria.
     * @param criteria filtering criteria
     * @return the count of ecritureComptables
     */
    public Mono<Long> countByCriteria(EcritureComptableCriteria criteria) {
        LOG.debug("Request to get the count of all EcritureComptables by Criteria");
        return ecritureComptableRepository.countByCriteria(criteria);
    }

    public Mono<Long> countAll() {
        return ecritureComptableRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<EcritureComptableDTO> findOne(Long id) {
        LOG.debug("Request to get EcritureComptable : {}", id);
        return ecritureComptableRepository.findById(id).map(ecritureComptableMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete EcritureComptable : {}", id);
        return ecritureComptableRepository.deleteById(id);
    }
}
