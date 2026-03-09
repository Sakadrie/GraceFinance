package com.gracefinance.gracefinanceapp.service.security;

import com.gracefinance.gracefinanceapp.repository.security.DroitRepository;
import com.gracefinance.gracefinanceapp.service.dto.security.DroitDTO;
import com.gracefinance.gracefinanceapp.service.mapper.security.DroitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.gracefinance.gracefinanceapp.domain.security.Droit}.
 */
@Service
@Transactional
public class DroitService {

    private static final Logger LOG = LoggerFactory.getLogger(DroitService.class);

    private final DroitRepository droitRepository;
    private final DroitMapper droitMapper;

    public DroitService(DroitRepository droitRepository, DroitMapper droitMapper) {
        this.droitRepository = droitRepository;
        this.droitMapper = droitMapper;
    }

    public DroitDTO save(DroitDTO droitDTO) {
        LOG.debug("Request to save Droit : {}", droitDTO);
        return droitMapper.toDto(droitRepository.save(droitMapper.toEntity(droitDTO)));
    }

    public DroitDTO update(DroitDTO droitDTO) {
        LOG.debug("Request to update Droit : {}", droitDTO);
        return droitMapper.toDto(droitRepository.save(droitMapper.toEntity(droitDTO)));
    }

    public Optional<DroitDTO> partialUpdate(DroitDTO droitDTO) {
        LOG.debug("Request to partially update Droit : {}", droitDTO);
        return droitRepository
            .findById(droitDTO.getId())
            .map(existing -> {
                droitMapper.partialUpdate(existing, droitDTO);
                return existing;
            })
            .map(droitRepository::save)
            .map(droitMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<DroitDTO> findOne(Long id) {
        LOG.debug("Request to get Droit : {}", id);
        return droitRepository.findById(id).map(droitMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<DroitDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Droits");
        return droitRepository.findAllBy(pageable).map(droitMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all Droits");
        return droitRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Droit : {}", id);
        droitRepository.deleteById(id);
    }
}
