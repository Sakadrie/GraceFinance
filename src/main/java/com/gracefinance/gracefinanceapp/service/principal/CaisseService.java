package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.repository.principal.CaisseRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.CaisseCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.CaisseDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.CaisseMapper;
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
 * {@link com.gracefinance.gracefinanceapp.domain.principal.Caisse}.
 */
@Service
@Transactional
public class CaisseService {

    private static final Logger LOG = LoggerFactory.getLogger(CaisseService.class);

    private final CaisseRepository caisseRepository;
    private final CaisseMapper caisseMapper;

    public CaisseService(CaisseRepository caisseRepository, CaisseMapper caisseMapper) {
        this.caisseRepository = caisseRepository;
        this.caisseMapper = caisseMapper;
    }

    public CaisseDTO save(CaisseDTO caisseDTO) {
        LOG.debug("Request to save Caisse : {}", caisseDTO);
        return caisseMapper.toDto(caisseRepository.save(caisseMapper.toEntity(caisseDTO)));
    }

    public CaisseDTO update(CaisseDTO caisseDTO) {
        LOG.debug("Request to update Caisse : {}", caisseDTO);
        return caisseMapper.toDto(caisseRepository.save(caisseMapper.toEntity(caisseDTO)));
    }

    public Optional<CaisseDTO> partialUpdate(CaisseDTO caisseDTO) {
        LOG.debug("Request to partially update Caisse : {}", caisseDTO);
        return caisseRepository
            .findById(caisseDTO.getId())
            .map(existingCaisse -> {
                caisseMapper.partialUpdate(existingCaisse, caisseDTO);
                return existingCaisse;
            })
            .map(caisseRepository::save)
            .map(caisseMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<CaisseDTO> findOne(Long id) {
        LOG.debug("Request to get Caisse : {}", id);
        return caisseRepository.findById(id).map(caisseMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<CaisseDTO> findAll() {
        LOG.debug("Request to get all Caisses");
        return caisseMapper.toDto(caisseRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<CaisseDTO> findByCriteria(CaisseCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get Caisses by criteria : {}", criteria);
        return caisseRepository.findAll(pageable).map(caisseMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(CaisseCriteria criteria) {
        LOG.debug("Request to count Caisses by criteria : {}", criteria);
        return caisseRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all Caisses");
        return caisseRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Caisse : {}", id);
        caisseRepository.deleteById(id);
    }
}
