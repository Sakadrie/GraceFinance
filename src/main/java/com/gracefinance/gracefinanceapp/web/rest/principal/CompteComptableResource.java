package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.repository.principal.CompteComptableRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.CompteComptableCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.CompteComptableDTO;
import com.gracefinance.gracefinanceapp.service.principal.CompteComptableService;
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
 * {@link com.gracefinance.gracefinanceapp.domain.principal.CompteComptable}.
 */
@RestController
@RequestMapping("/api/compte-comptables")
public class CompteComptableResource {

    private static final Logger LOG = LoggerFactory.getLogger(CompteComptableResource.class);
    private static final String ENTITY_NAME = "compteComptable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompteComptableService compteComptableService;
    private final CompteComptableRepository compteComptableRepository;

    public CompteComptableResource(CompteComptableService compteComptableService, CompteComptableRepository compteComptableRepository) {
        this.compteComptableService = compteComptableService;
        this.compteComptableRepository = compteComptableRepository;
    }

    @PostMapping("")
    public ResponseEntity<CompteComptableDTO> createCompteComptable(@Valid @RequestBody CompteComptableDTO compteComptableDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CompteComptable : {}", compteComptableDTO);
        if (compteComptableDTO.getId() != null) {
            throw new BadRequestAlertException("A new compteComptable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompteComptableDTO result = compteComptableService.save(compteComptableDTO);
        return ResponseEntity.created(new URI("/api/compte-comptables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompteComptableDTO> updateCompteComptable(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompteComptableDTO compteComptableDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CompteComptable : {}, {}", id, compteComptableDTO);
        if (compteComptableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compteComptableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!compteComptableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        CompteComptableDTO result = compteComptableService.update(compteComptableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompteComptableDTO> partialUpdateCompteComptable(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompteComptableDTO compteComptableDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CompteComptable partially : {}, {}", id, compteComptableDTO);
        if (compteComptableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compteComptableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!compteComptableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<CompteComptableDTO> result = compteComptableService.partialUpdate(compteComptableDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compteComptableDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<CompteComptableDTO>> getAllCompteComptables(
        CompteComptableCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        LOG.debug("REST request to get CompteComptables by criteria: {}", criteria);
        Page<CompteComptableDTO> page = compteComptableService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countCompteComptables(CompteComptableCriteria criteria) {
        LOG.debug("REST request to count CompteComptables by criteria: {}", criteria);
        return ResponseEntity.ok(compteComptableService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompteComptableDTO> getCompteComptable(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CompteComptable : {}", id);
        Optional<CompteComptableDTO> compteComptableDTO = compteComptableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compteComptableDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompteComptable(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CompteComptable : {}", id);
        compteComptableService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
