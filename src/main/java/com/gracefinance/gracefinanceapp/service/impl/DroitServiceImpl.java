package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.repository.security.DroitRepository;
import com.gracefinance.gracefinanceapp.service.dto.security.DroitDTO;
import com.gracefinance.gracefinanceapp.service.mapper.security.DroitMapper;
import com.gracefinance.gracefinanceapp.service.security.DroitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.security.Droit}.
 */
@Service
@Transactional
public class DroitServiceImpl implements DroitService {

    private static final Logger LOG = LoggerFactory.getLogger(DroitServiceImpl.class);

    private final DroitRepository droitRepository;

    private final DroitMapper droitMapper;

    public DroitServiceImpl(DroitRepository droitRepository, DroitMapper droitMapper) {
        this.droitRepository = droitRepository;
        this.droitMapper = droitMapper;
    }

    @Override
    public Mono<DroitDTO> save(DroitDTO droitDTO) {
        LOG.debug("Request to save Droit : {}", droitDTO);
        return droitRepository.save(droitMapper.toEntity(droitDTO)).map(droitMapper::toDto);
    }

    @Override
    public Mono<DroitDTO> update(DroitDTO droitDTO) {
        LOG.debug("Request to update Droit : {}", droitDTO);
        return droitRepository.save(droitMapper.toEntity(droitDTO)).map(droitMapper::toDto);
    }

    @Override
    public Mono<DroitDTO> partialUpdate(DroitDTO droitDTO) {
        LOG.debug("Request to partially update Droit : {}", droitDTO);

        return droitRepository
            .findById(droitDTO.getId())
            .map(existingDroit -> {
                droitMapper.partialUpdate(existingDroit, droitDTO);

                return existingDroit;
            })
            .flatMap(droitRepository::save)
            .map(droitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<DroitDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Droits");
        return droitRepository.findAllBy(pageable).map(droitMapper::toDto);
    }

    public Mono<Long> countAll() {
        return droitRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<DroitDTO> findOne(Long id) {
        LOG.debug("Request to get Droit : {}", id);
        return droitRepository.findById(id).map(droitMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Droit : {}", id);
        return droitRepository.deleteById(id);
    }
}
