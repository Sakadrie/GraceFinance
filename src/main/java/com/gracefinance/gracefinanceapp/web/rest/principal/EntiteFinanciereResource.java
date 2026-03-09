package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.repository.principal.EntiteFinanciereRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.EntiteFinanciereCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import com.gracefinance.gracefinanceapp.service.principal.EntiteFinanciereService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing
 * {@link com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere}.
 */
@RestController
@RequestMapping("/api/entites-financieres")
public class EntiteFinanciereResource {

    private static final Logger LOG = LoggerFactory.getLogger(EntiteFinanciereResource.class);
    private static final String ENTITY_NAME = "entiteFinanciere";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntiteFinanciereService entiteFinanciereService;
    private final EntiteFinanciereRepository entiteFinanciereRepository;

    public EntiteFinanciereResource(
        EntiteFinanciereService entiteFinanciereService,
        EntiteFinanciereRepository entiteFinanciereRepository
    ) {
        this.entiteFinanciereService = entiteFinanciereService;
        this.entiteFinanciereRepository = entiteFinanciereRepository;
    }

    @PostMapping("")
    public ResponseEntity<EntiteFinanciereDTO> createEntiteFinanciere(@Valid @RequestBody EntiteFinanciereDTO entiteFinanciereDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save EntiteFinanciere : {}", entiteFinanciereDTO);
        if (entiteFinanciereDTO.getId() != null) {
            throw new BadRequestAlertException("A new EntiteFinanciere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntiteFinanciereDTO result = entiteFinanciereService.save(entiteFinanciereDTO);
        return ResponseEntity.created(new URI("/api/entites-financieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntiteFinanciereDTO> updateEntiteFinanciere(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EntiteFinanciereDTO EntiteFinanciereDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update EntiteFinanciere : {}, {}", id, EntiteFinanciereDTO);
        if (EntiteFinanciereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, EntiteFinanciereDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!entiteFinanciereRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        EntiteFinanciereDTO result = entiteFinanciereService.update(EntiteFinanciereDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntiteFinanciereDTO> partialUpdateEntiteFinanciere(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EntiteFinanciereDTO EntiteFinanciereDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EntiteFinanciere partially : {}, {}", id, EntiteFinanciereDTO);
        if (EntiteFinanciereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, EntiteFinanciereDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!entiteFinanciereRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<EntiteFinanciereDTO> result = entiteFinanciereService.partialUpdate(EntiteFinanciereDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, EntiteFinanciereDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<EntiteFinanciereDTO>> getAllEntiteFinancieres(
        EntiteFinanciereCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        LOG.debug("REST request to get EntiteFinancieres by criteria: {}", criteria);
        Page<EntiteFinanciereDTO> page = entiteFinanciereService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countEntiteFinancieres(EntiteFinanciereCriteria criteria) {
        LOG.debug("REST request to count EntiteFinancieres by criteria: {}", criteria);
        return ResponseEntity.ok(entiteFinanciereService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntiteFinanciereDTO> getEntiteFinanciere(@PathVariable("id") Long id) {
        LOG.debug("REST request to get EntiteFinanciere : {}", id);
        Optional<EntiteFinanciereDTO> EntiteFinanciereDTO = entiteFinanciereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(EntiteFinanciereDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntiteFinanciere(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete EntiteFinanciere : {}", id);
        entiteFinanciereService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
