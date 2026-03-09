package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.repository.principal.RecetteRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.RecetteCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.RecetteDTO;
import com.gracefinance.gracefinanceapp.service.principal.RecetteService;
import com.gracefinance.gracefinanceapp.web.rest.errors.BadRequestAlertException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.gracefinance.gracefinanceapp.domain.principal.Recette}.
 */
@RestController
@RequestMapping("/api/recettes")
public class RecetteResource {

    private static final Logger LOG = LoggerFactory.getLogger(RecetteResource.class);
    private static final String ENTITY_NAME = "recette";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecetteService recetteService;
    private final RecetteRepository recetteRepository;

    public RecetteResource(RecetteService recetteService, RecetteRepository recetteRepository) {
        this.recetteService = recetteService;
        this.recetteRepository = recetteRepository;
    }

    @PostMapping("")
    public ResponseEntity<RecetteDTO> createRecette(@Valid @RequestBody RecetteDTO recetteDTO) throws URISyntaxException {
        LOG.debug("REST request to save Recette : {}", recetteDTO);
        if (recetteDTO.getId() != null) {
            throw new BadRequestAlertException("A new Recette cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecetteDTO result = recetteService.save(recetteDTO);
        return ResponseEntity.created(new URI("/api/recettes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecetteDTO> updateRecette(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecetteDTO recetteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Recette : {}, {}", id, recetteDTO);
        if (recetteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recetteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!recetteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RecetteDTO result = recetteService.update(recetteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RecetteDTO> partialUpdateRecette(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecetteDTO recetteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Recette partially : {}, {}", id, recetteDTO);
        if (recetteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recetteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!recetteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<RecetteDTO> result = recetteService.partialUpdate(recetteDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, recetteDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<RecetteDTO>> getAllRecettes(
        RecetteCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        LOG.debug("REST request to get Recettes by criteria: {}", criteria);
        Page<RecetteDTO> page = recetteService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countRecettes(RecetteCriteria criteria) {
        LOG.debug("REST request to count Recettes by criteria: {}", criteria);
        return ResponseEntity.ok(recetteService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetteDTO> getRecette(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Recette : {}", id);
        Optional<RecetteDTO> RecetteDTO = recetteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(RecetteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecette(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Recette : {}", id);
        recetteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
