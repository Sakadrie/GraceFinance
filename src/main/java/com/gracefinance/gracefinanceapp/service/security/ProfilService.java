package com.gracefinance.gracefinanceapp.service.security;

import com.gracefinance.gracefinanceapp.repository.security.ProfilRepository;
import com.gracefinance.gracefinanceapp.service.dto.security.ProfilDTO;
import com.gracefinance.gracefinanceapp.service.mapper.security.ProfilMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.gracefinance.gracefinanceapp.domain.security.Profil}.
 */
@Service
@Transactional
public class ProfilService {

    private static final Logger LOG = LoggerFactory.getLogger(ProfilService.class);

    private final ProfilRepository profilRepository;
    private final ProfilMapper profilMapper;

    public ProfilService(ProfilRepository profilRepository, ProfilMapper profilMapper) {
        this.profilRepository = profilRepository;
        this.profilMapper = profilMapper;
    }

    public ProfilDTO save(ProfilDTO profilDTO) {
        LOG.debug("Request to save Profil : {}", profilDTO);
        return profilMapper.toDto(profilRepository.save(profilMapper.toEntity(profilDTO)));
    }

    public ProfilDTO update(ProfilDTO profilDTO) {
        LOG.debug("Request to update Profil : {}", profilDTO);
        return profilMapper.toDto(profilRepository.save(profilMapper.toEntity(profilDTO)));
    }

    public Optional<ProfilDTO> partialUpdate(ProfilDTO profilDTO) {
        LOG.debug("Request to partially update Profil : {}", profilDTO);
        return profilRepository
            .findById(profilDTO.getId())
            .map(existing -> {
                profilMapper.partialUpdate(existing, profilDTO);
                return existing;
            })
            .map(profilRepository::save)
            .map(profilMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProfilDTO> findOne(Long id) {
        LOG.debug("Request to get Profil : {}", id);
        return profilRepository.findById(id).map(profilMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProfilDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Profils");
        return profilRepository.findAllBy(pageable).map(profilMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<ProfilDTO> findAllWithEagerRelationships(Pageable pageable) {
        LOG.debug("Request to get all Profils with eager relationships");
        return profilRepository.findAllWithEagerRelationships(pageable).map(profilMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all Profils");
        return profilRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Profil : {}", id);
        profilRepository.deleteById(id);
    }
}
