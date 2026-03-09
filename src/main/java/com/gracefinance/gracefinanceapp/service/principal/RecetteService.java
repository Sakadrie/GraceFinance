package com.gracefinance.gracefinanceapp.service.principal;

import com.gracefinance.gracefinanceapp.repository.principal.RecetteRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.RecetteCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.RecetteDTO;
import com.gracefinance.gracefinanceapp.service.mapper.principal.RecetteMapper;
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
 * {@link com.gracefinance.gracefinanceapp.domain.principal.Recette}.
 */
@Service
@Transactional
public class RecetteService {

    private static final Logger LOG = LoggerFactory.getLogger(RecetteService.class);

    private final RecetteRepository recetteRepository;
    private final RecetteMapper recetteMapper;

    public RecetteService(RecetteRepository recetteRepository, RecetteMapper recetteMapper) {
        this.recetteRepository = recetteRepository;
        this.recetteMapper = recetteMapper;
    }

    public RecetteDTO save(RecetteDTO recetteDTO) {
        LOG.debug("Request to save Recette : {}", recetteDTO);
        return recetteMapper.toDto(recetteRepository.save(recetteMapper.toEntity(recetteDTO)));
    }

    public RecetteDTO update(RecetteDTO recetteDTO) {
        LOG.debug("Request to update Recette : {}", recetteDTO);
        return recetteMapper.toDto(recetteRepository.save(recetteMapper.toEntity(recetteDTO)));
    }

    public Optional<RecetteDTO> partialUpdate(RecetteDTO recetteDTO) {
        LOG.debug("Request to partially update Recette : {}", recetteDTO);
        return recetteRepository
            .findById(recetteDTO.getId())
            .map(existing -> {
                recetteMapper.partialUpdate(existing, recetteDTO);
                return existing;
            })
            .map(recetteRepository::save)
            .map(recetteMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<RecetteDTO> findOne(Long id) {
        LOG.debug("Request to get Recette : {}", id);
        return recetteRepository.findById(id).map(recetteMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<RecetteDTO> findAll() {
        LOG.debug("Request to get all Recettes");
        return recetteMapper.toDto(recetteRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<RecetteDTO> findByCriteria(RecetteCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get Recettes by criteria : {}", criteria);
        return recetteRepository.findAll(pageable).map(recetteMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(RecetteCriteria criteria) {
        LOG.debug("Request to count Recettes by criteria : {}", criteria);
        return recetteRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all Recettes");
        return recetteRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Recette : {}", id);
        recetteRepository.deleteById(id);
    }
}
