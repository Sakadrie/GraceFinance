package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.repository.principal.CaisseRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.CaisseCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.CaisseDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.CaisseMapper;
import com.gracefinance.gracefinanceapp.service.principal.CaisseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.principal.Caisse}.
 */
@Service
@Transactional
public class CaisseServiceImpl implements CaisseService {

    private static final Logger LOG = LoggerFactory.getLogger(CaisseServiceImpl.class);

    private final CaisseRepository caisseRepository;

    private final CaisseMapper caisseMapper;

    public CaisseServiceImpl(CaisseRepository caisseRepository, CaisseMapper caisseMapper) {
        this.caisseRepository = caisseRepository;
        this.caisseMapper = caisseMapper;
    }

    @Override
    public Mono<CaisseDTO> save(CaisseDTO caisseDTO) {
        LOG.debug("Request to save Caisse : {}", caisseDTO);
        return caisseRepository.save(caisseMapper.toEntity(caisseDTO)).map(caisseMapper::toDto);
    }

    @Override
    public Mono<CaisseDTO> update(CaisseDTO caisseDTO) {
        LOG.debug("Request to update Caisse : {}", caisseDTO);
        return caisseRepository.save(caisseMapper.toEntity(caisseDTO)).map(caisseMapper::toDto);
    }

    @Override
    public Mono<CaisseDTO> partialUpdate(CaisseDTO caisseDTO) {
        LOG.debug("Request to partially update Caisse : {}", caisseDTO);

        return caisseRepository
            .findById(caisseDTO.getId())
            .map(existingCaisse -> {
                caisseMapper.partialUpdate(existingCaisse, caisseDTO);

                return existingCaisse;
            })
            .flatMap(caisseRepository::save)
            .map(caisseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<CaisseDTO> findByCriteria(CaisseCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get all Caisses by Criteria");
        return caisseRepository.findByCriteria(criteria, pageable).map(caisseMapper::toDto);
    }

    /**
     * Find the count of caisses by criteria.
     * @param criteria filtering criteria
     * @return the count of caisses
     */
    public Mono<Long> countByCriteria(CaisseCriteria criteria) {
        LOG.debug("Request to get the count of all Caisses by Criteria");
        return caisseRepository.countByCriteria(criteria);
    }

    public Mono<Long> countAll() {
        return caisseRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<CaisseDTO> findOne(Long id) {
        LOG.debug("Request to get Caisse : {}", id);
        return caisseRepository.findById(id).map(caisseMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Caisse : {}", id);
        return caisseRepository.deleteById(id);
    }
}
