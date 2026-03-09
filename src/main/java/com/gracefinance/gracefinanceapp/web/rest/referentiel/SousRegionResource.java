package com.gracefinance.gracefinanceapp.web.rest.referentiel;

import com.gracefinance.gracefinanceapp.repository.referentiel.SousRegionRepository;
import com.gracefinance.gracefinanceapp.service.criteria.referentiel.SousRegionCriteria;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.SousRegionDTO;
import com.gracefinance.gracefinanceapp.service.referentiel.SousRegionService;
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

@RestController
@RequestMapping("/api/sous-regions")
public class SousRegionResource {

    private static final Logger LOG = LoggerFactory.getLogger(SousRegionResource.class);
    private static final String ENTITY_NAME = "sousRegion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SousRegionService sousRegionService;
    private final SousRegionRepository sousRegionRepository;

    public SousRegionResource(SousRegionService sousRegionService, SousRegionRepository sousRegionRepository) {
        this.sousRegionService = sousRegionService;
        this.sousRegionRepository = sousRegionRepository;
    }

    @PostMapping("")
    public ResponseEntity<SousRegionDTO> createSousRegion(@Valid @RequestBody SousRegionDTO sousRegionDTO) throws URISyntaxException {
        LOG.debug("REST request to save SousRegion : {}", sousRegionDTO);
        if (sousRegionDTO.getId() != null) throw new BadRequestAlertException(
            "A new sousRegion cannot already have an ID",
            ENTITY_NAME,
            "idexists"
        );
        SousRegionDTO result = sousRegionService.save(sousRegionDTO);
        return ResponseEntity.created(new URI("/api/sous-regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SousRegionDTO> updateSousRegion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SousRegionDTO sousRegionDTO
    ) throws URISyntaxException {
        if (sousRegionDTO.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        if (!Objects.equals(id, sousRegionDTO.getId())) throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        if (!sousRegionRepository.existsById(id)) throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        SousRegionDTO result = sousRegionService.update(sousRegionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SousRegionDTO> partialUpdateSousRegion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SousRegionDTO sousRegionDTO
    ) throws URISyntaxException {
        if (sousRegionDTO.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        if (!Objects.equals(id, sousRegionDTO.getId())) throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        if (!sousRegionRepository.existsById(id)) throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        Optional<SousRegionDTO> result = sousRegionService.partialUpdate(sousRegionDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sousRegionDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<SousRegionDTO>> getAllSousRegions(
        SousRegionCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        LOG.debug("REST request to get SousRegions by criteria: {}", criteria);
        Page<SousRegionDTO> page = sousRegionService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countSousRegions(SousRegionCriteria criteria) {
        return ResponseEntity.ok(sousRegionService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SousRegionDTO> getSousRegion(@PathVariable("id") Long id) {
        return ResponseUtil.wrapOrNotFound(sousRegionService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSousRegion(@PathVariable("id") Long id) {
        sousRegionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
