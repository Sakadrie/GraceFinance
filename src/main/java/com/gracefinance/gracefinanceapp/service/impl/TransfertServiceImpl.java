package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.domain.criteria.TransfertCriteria;
import com.gracefinance.gracefinanceapp.repository.referentiel.TransfertRepository;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.TransfertDTO;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.TransfertMapper;
import com.gracefinance.gracefinanceapp.service.referentiel.TransfertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.referentiel.Transfert}.
 */
@Service
@Transactional
public class TransfertServiceImpl implements TransfertService {

    private static final Logger LOG = LoggerFactory.getLogger(TransfertServiceImpl.class);

    private final TransfertRepository transfertRepository;

    private final TransfertMapper transfertMapper;

    public TransfertServiceImpl(TransfertRepository transfertRepository, TransfertMapper transfertMapper) {
        this.transfertRepository = transfertRepository;
        this.transfertMapper = transfertMapper;
    }

    @Override
    public Mono<TransfertDTO> save(TransfertDTO transfertDTO) {
        LOG.debug("Request to save Transfert : {}", transfertDTO);
        return transfertRepository.save(transfertMapper.toEntity(transfertDTO)).map(transfertMapper::toDto);
    }

    @Override
    public Mono<TransfertDTO> update(TransfertDTO transfertDTO) {
        LOG.debug("Request to update Transfert : {}", transfertDTO);
        return transfertRepository.save(transfertMapper.toEntity(transfertDTO)).map(transfertMapper::toDto);
    }

    @Override
    public Mono<TransfertDTO> partialUpdate(TransfertDTO transfertDTO) {
        LOG.debug("Request to partially update Transfert : {}", transfertDTO);

        return transfertRepository
            .findById(transfertDTO.getId())
            .map(existingTransfert -> {
                transfertMapper.partialUpdate(existingTransfert, transfertDTO);

                return existingTransfert;
            })
            .flatMap(transfertRepository::save)
            .map(transfertMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<TransfertDTO> findByCriteria(TransfertCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all Transferts by Criteria");
        return transfertRepository.findByCriteria(criteria, pageable).map(transfertMapper::toDto);
    }

    /**
     * Find the count of transferts by criteria.
     * @param criteria filtering criteria
     * @return the count of transferts
     */
    public Mono<Long> countByCriteria(TransfertCriteria criteria) {
        LOG.debug("Request to get the count of all Transferts by Criteria");
        return transfertRepository.countByCriteria(criteria);
    }

    public Mono<Long> countAll() {
        return transfertRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<TransfertDTO> findOne(Long id) {
        LOG.debug("Request to get Transfert : {}", id);
        return transfertRepository.findById(id).map(transfertMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Transfert : {}", id);
        return transfertRepository.deleteById(id);
    }
}
