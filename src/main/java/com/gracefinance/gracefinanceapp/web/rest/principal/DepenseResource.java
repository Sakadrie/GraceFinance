package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.repository.principal.DepenseRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.DepenseCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.DepenseDTO;
import com.gracefinance.gracefinanceapp.service.principal.DepenseService;
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
 * {@link com.gracefinance.gracefinanceapp.domain.principal.Depense}.
 */
@RestController
@RequestMapping("/api/depenses")
public class DepenseResource {

    private static final Logger LOG = LoggerFactory.getLogger(DepenseResource.class);
    private static final String ENTITY_NAME = "depense";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepenseService depenseService;
    private final DepenseRepository depenseRepository;

    public DepenseResource(DepenseService depenseService, DepenseRepository depenseRepository) {
        this.depenseService = depenseService;
        this.depenseRepository = depenseRepository;
    }

    @PostMapping("")
    public ResponseEntity<DepenseDTO> createDepense(@Valid @RequestBody DepenseDTO depenseDTO) throws URISyntaxException {
        LOG.debug("REST request to save Depense : {}", depenseDTO);
        if (depenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new depense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepenseDTO result = depenseService.save(depenseDTO);
        return ResponseEntity.created(new URI("/api/depenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepenseDTO> updateDepense(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DepenseDTO depenseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Depense : {}, {}", id, depenseDTO);
        if (depenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depenseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!depenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        DepenseDTO result = depenseService.update(depenseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DepenseDTO> partialUpdateDepense(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DepenseDTO depenseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Depense partially : {}, {}", id, depenseDTO);
        if (depenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, depenseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!depenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<DepenseDTO> result = depenseService.partialUpdate(depenseDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, depenseDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<DepenseDTO>> getAllDepenses(
        DepenseCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        LOG.debug("REST request to get Depenses by criteria: {}", criteria);
        Page<DepenseDTO> page = depenseService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countDepenses(DepenseCriteria criteria) {
        LOG.debug("REST request to count Depenses by criteria: {}", criteria);
        return ResponseEntity.ok(depenseService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepenseDTO> getDepense(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Depense : {}", id);
        Optional<DepenseDTO> depenseDTO = depenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depenseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepense(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Depense : {}", id);
        depenseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
