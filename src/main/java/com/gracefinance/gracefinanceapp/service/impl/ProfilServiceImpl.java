package com.gracefinance.gracefinanceapp.service.impl;

import com.gracefinance.gracefinanceapp.repository.security.ProfilRepository;
import com.gracefinance.gracefinanceapp.service.dto.security.ProfilDTO;
import com.gracefinance.gracefinanceapp.service.mapper.security.ProfilMapper;
import com.gracefinance.gracefinanceapp.service.security.ProfilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.gracefinance.gracefinanceapp.domain.security.Profil}.
 */
@Service
@Transactional
public class ProfilServiceImpl implements ProfilService {

    private static final Logger LOG = LoggerFactory.getLogger(ProfilServiceImpl.class);

    private final ProfilRepository profilRepository;

    private final ProfilMapper profilMapper;

    public ProfilServiceImpl(ProfilRepository profilRepository, ProfilMapper profilMapper) {
        this.profilRepository = profilRepository;
        this.profilMapper = profilMapper;
    }

    @Override
    public Mono<ProfilDTO> save(ProfilDTO profilDTO) {
        LOG.debug("Request to save Profil : {}", profilDTO);
        return profilRepository.save(profilMapper.toEntity(profilDTO)).map(profilMapper::toDto);
    }

    @Override
    public Mono<ProfilDTO> update(ProfilDTO profilDTO) {
        LOG.debug("Request to update Profil : {}", profilDTO);
        return profilRepository.save(profilMapper.toEntity(profilDTO)).map(profilMapper::toDto);
    }

    @Override
    public Mono<ProfilDTO> partialUpdate(ProfilDTO profilDTO) {
        LOG.debug("Request to partially update Profil : {}", profilDTO);

        return profilRepository
            .findById(profilDTO.getId())
            .map(existingProfil -> {
                profilMapper.partialUpdate(existingProfil, profilDTO);

                return existingProfil;
            })
            .flatMap(profilRepository::save)
            .map(profilMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<ProfilDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Profils");
        return profilRepository.findAllBy(pageable).map(profilMapper::toDto);
    }

    public Flux<ProfilDTO> findAllWithEagerRelationships(Pageable pageable) {
        return profilRepository.findAllWithEagerRelationships(pageable).map(profilMapper::toDto);
    }

    public Mono<Long> countAll() {
        return profilRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<ProfilDTO> findOne(Long id) {
        LOG.debug("Request to get Profil : {}", id);
        return profilRepository.findOneWithEagerRelationships(id).map(profilMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Profil : {}", id);
        return profilRepository.deleteById(id);
    }
}
