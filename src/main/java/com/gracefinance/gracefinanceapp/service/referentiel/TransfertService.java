package com.gracefinance.gracefinanceapp.service.referentiel;

import com.gracefinance.gracefinanceapp.repository.referentiel.TransfertRepository;
import com.gracefinance.gracefinanceapp.service.criteria.referentiel.TransfertCriteria;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.TransfertDTO;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.TransfertMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link com.gracefinance.gracefinanceapp.domain.referentiel.Transfert}.
 */
@Service
@Transactional
public class TransfertService {

    private static final Logger LOG = LoggerFactory.getLogger(TransfertService.class);

    private final TransfertRepository transfertRepository;
    private final TransfertMapper transfertMapper;

    public TransfertService(TransfertRepository transfertRepository, TransfertMapper transfertMapper) {
        this.transfertRepository = transfertRepository;
        this.transfertMapper = transfertMapper;
    }

    public TransfertDTO save(TransfertDTO transfertDTO) {
        LOG.debug("Request to save Transfert : {}", transfertDTO);
        return transfertMapper.toDto(transfertRepository.save(transfertMapper.toEntity(transfertDTO)));
    }

    public TransfertDTO update(TransfertDTO transfertDTO) {
        LOG.debug("Request to update Transfert : {}", transfertDTO);
        return transfertMapper.toDto(transfertRepository.save(transfertMapper.toEntity(transfertDTO)));
    }

    public Optional<TransfertDTO> partialUpdate(TransfertDTO transfertDTO) {
        LOG.debug("Request to partially update Transfert : {}", transfertDTO);
        return transfertRepository
            .findById(transfertDTO.getId())
            .map(existing -> {
                transfertMapper.partialUpdate(existing, transfertDTO);
                return existing;
            })
            .map(transfertRepository::save)
            .map(transfertMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<TransfertDTO> findOne(Long id) {
        LOG.debug("Request to get Transfert : {}", id);
        return transfertRepository.findById(id).map(transfertMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<TransfertDTO> findAll() {
        LOG.debug("Request to get all Transferts");
        return transfertMapper.toDto(transfertRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<TransfertDTO> findByCriteria(TransfertCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get Transferts by criteria : {}", criteria);
        return transfertRepository.findAll(pageable).map(transfertMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(TransfertCriteria criteria) {
        LOG.debug("Request to count Transferts by criteria : {}", criteria);
        return transfertRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all Transferts");
        return transfertRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Transfert : {}", id);
        transfertRepository.deleteById(id);
    }
}
