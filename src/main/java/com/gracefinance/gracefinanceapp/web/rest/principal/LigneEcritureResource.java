package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.repository.principal.LigneEcritureRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.LigneEcritureCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.LigneEcritureDTO;
import com.gracefinance.gracefinanceapp.service.principal.LigneEcritureService;
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
 * {@link com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture}.
 */
@RestController
@RequestMapping("/api/ligne-ecritures")
public class LigneEcritureResource {

    private static final Logger LOG = LoggerFactory.getLogger(LigneEcritureResource.class);
    private static final String ENTITY_NAME = "ligneEcriture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneEcritureService ligneEcritureService;
    private final LigneEcritureRepository ligneEcritureRepository;

    public LigneEcritureResource(LigneEcritureService ligneEcritureService, LigneEcritureRepository ligneEcritureRepository) {
        this.ligneEcritureService = ligneEcritureService;
        this.ligneEcritureRepository = ligneEcritureRepository;
    }

    @PostMapping("")
    public ResponseEntity<LigneEcritureDTO> createLigneEcriture(@Valid @RequestBody LigneEcritureDTO ligneEcritureDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save LigneEcriture : {}", ligneEcritureDTO);
        if (ligneEcritureDTO.getId() != null) {
            throw new BadRequestAlertException("A new LigneEcriture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneEcritureDTO result = ligneEcritureService.save(ligneEcritureDTO);
        return ResponseEntity.created(new URI("/api/ligne-ecritures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LigneEcritureDTO> updateLigneEcriture(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LigneEcritureDTO ligneEcritureDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update LigneEcriture : {}, {}", id, ligneEcritureDTO);
        if (ligneEcritureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneEcritureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!ligneEcritureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        LigneEcritureDTO result = ligneEcritureService.update(ligneEcritureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LigneEcritureDTO> partialUpdateLigneEcriture(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LigneEcritureDTO ligneEcritureDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update LigneEcriture partially : {}, {}", id, ligneEcritureDTO);
        if (ligneEcritureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneEcritureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!ligneEcritureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<LigneEcritureDTO> result = ligneEcritureService.partialUpdate(ligneEcritureDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneEcritureDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<LigneEcritureDTO>> getAllLigneEcritures(
        LigneEcritureCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        LOG.debug("REST request to get LigneEcritures by criteria: {}", criteria);
        Page<LigneEcritureDTO> page = ligneEcritureService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countLigneEcritures(LigneEcritureCriteria criteria) {
        LOG.debug("REST request to count LigneEcritures by criteria: {}", criteria);
        return ResponseEntity.ok(ligneEcritureService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneEcritureDTO> getLigneEcriture(@PathVariable("id") Long id) {
        LOG.debug("REST request to get LigneEcriture : {}", id);
        Optional<LigneEcritureDTO> LigneEcritureDTO = ligneEcritureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(LigneEcritureDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLigneEcriture(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete LigneEcriture : {}", id);
        ligneEcritureService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
