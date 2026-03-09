package com.gracefinance.gracefinanceapp.service.referentiel;

import com.gracefinance.gracefinanceapp.repository.referentiel.VilleRepository;
import com.gracefinance.gracefinanceapp.service.criteria.referentiel.VilleCriteria;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.VilleDTO;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.VilleMapper;
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
 * {@link com.gracefinance.gracefinanceapp.domain.referentiel.Ville}.
 */
@Service
@Transactional
public class VilleService {

    private static final Logger LOG = LoggerFactory.getLogger(VilleService.class);

    private final VilleRepository villeRepository;
    private final VilleMapper villeMapper;

    public VilleService(VilleRepository villeRepository, VilleMapper villeMapper) {
        this.villeRepository = villeRepository;
        this.villeMapper = villeMapper;
    }

    public VilleDTO save(VilleDTO villeDTO) {
        LOG.debug("Request to save Ville : {}", villeDTO);
        return villeMapper.toDto(villeRepository.save(villeMapper.toEntity(villeDTO)));
    }

    public VilleDTO update(VilleDTO villeDTO) {
        LOG.debug("Request to update Ville : {}", villeDTO);
        return villeMapper.toDto(villeRepository.save(villeMapper.toEntity(villeDTO)));
    }

    public Optional<VilleDTO> partialUpdate(VilleDTO villeDTO) {
        LOG.debug("Request to partially update Ville : {}", villeDTO);
        return villeRepository
            .findById(villeDTO.getId())
            .map(existing -> {
                villeMapper.partialUpdate(existing, villeDTO);
                return existing;
            })
            .map(villeRepository::save)
            .map(villeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<VilleDTO> findOne(Long id) {
        LOG.debug("Request to get Ville : {}", id);
        return villeRepository.findById(id).map(villeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<VilleDTO> findAll() {
        LOG.debug("Request to get all Villes");
        return villeMapper.toDto(villeRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<VilleDTO> findByCriteria(VilleCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get Villes by criteria : {}", criteria);
        return villeRepository.findAll(pageable).map(villeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(VilleCriteria criteria) {
        LOG.debug("Request to count Villes by criteria : {}", criteria);
        return villeRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all Villes");
        return villeRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Ville : {}", id);
        villeRepository.deleteById(id);
    }
}
