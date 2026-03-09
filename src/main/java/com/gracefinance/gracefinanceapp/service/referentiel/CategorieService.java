package com.gracefinance.gracefinanceapp.service.referentiel;

import com.gracefinance.gracefinanceapp.repository.referentiel.CategorieRepository;
import com.gracefinance.gracefinanceapp.service.criteria.referentiel.CategorieCriteria;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.CategorieDTO;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.CategorieMapper;
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
 * {@link com.gracefinance.gracefinanceapp.domain.referentiel.Categorie}.
 */
@Service
@Transactional
public class CategorieService {

    private static final Logger LOG = LoggerFactory.getLogger(CategorieService.class);

    private final CategorieRepository categorieRepository;
    private final CategorieMapper categorieMapper;

    public CategorieService(CategorieRepository categorieRepository, CategorieMapper categorieMapper) {
        this.categorieRepository = categorieRepository;
        this.categorieMapper = categorieMapper;
    }

    public CategorieDTO save(CategorieDTO categorieDTO) {
        LOG.debug("Request to save Categorie : {}", categorieDTO);
        return categorieMapper.toDto(categorieRepository.save(categorieMapper.toEntity(categorieDTO)));
    }

    public CategorieDTO update(CategorieDTO categorieDTO) {
        LOG.debug("Request to update Categorie : {}", categorieDTO);
        return categorieMapper.toDto(categorieRepository.save(categorieMapper.toEntity(categorieDTO)));
    }

    public Optional<CategorieDTO> partialUpdate(CategorieDTO categorieDTO) {
        LOG.debug("Request to partially update Categorie : {}", categorieDTO);
        return categorieRepository
            .findById(categorieDTO.getId())
            .map(existing -> {
                categorieMapper.partialUpdate(existing, categorieDTO);
                return existing;
            })
            .map(categorieRepository::save)
            .map(categorieMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<CategorieDTO> findOne(Long id) {
        LOG.debug("Request to get Categorie : {}", id);
        return categorieRepository.findById(id).map(categorieMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<CategorieDTO> findAll() {
        LOG.debug("Request to get all Categories");
        return categorieMapper.toDto(categorieRepository.findAll());
    }

    @Transactional(readOnly = true)
    public Page<CategorieDTO> findByCriteria(CategorieCriteria criteria, Pageable pageable) {
        LOG.debug("Request to get Categories by criteria : {}", criteria);
        return categorieRepository.findAll(pageable).map(categorieMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(CategorieCriteria criteria) {
        LOG.debug("Request to count Categories by criteria : {}", criteria);
        return categorieRepository.count();
    }

    @Transactional(readOnly = true)
    public long countAll() {
        LOG.debug("Request to count all Categories");
        return categorieRepository.count();
    }

    public void delete(Long id) {
        LOG.debug("Request to delete Categorie : {}", id);
        categorieRepository.deleteById(id);
    }
}
