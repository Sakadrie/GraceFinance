package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.repository.principal.EcritureComptableRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.EcritureComptableCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.EcritureComptableDTO;
import com.gracefinance.gracefinanceapp.service.principal.EcritureComptableService;
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
 * {@link com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable}.
 */
@RestController
@RequestMapping("/api/ecritures-comptables")
public class EcritureComptableResource {

    private static final Logger LOG = LoggerFactory.getLogger(EcritureComptableResource.class);
    private static final String ENTITY_NAME = "ecritureComptable";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EcritureComptableService ecritureComptableService;
    private final EcritureComptableRepository ecritureComptableRepository;

    public EcritureComptableResource(
        EcritureComptableService ecritureComptableService,
        EcritureComptableRepository ecritureComptableRepository
    ) {
        this.ecritureComptableService = ecritureComptableService;
        this.ecritureComptableRepository = ecritureComptableRepository;
    }

    @PostMapping("")
    public ResponseEntity<EcritureComptableDTO> createEcritureComptable(@Valid @RequestBody EcritureComptableDTO ecritureComptableDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save EcritureComptable : {}", ecritureComptableDTO);
        if (ecritureComptableDTO.getId() != null) {
            throw new BadRequestAlertException("A new EcritureComptable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EcritureComptableDTO result = ecritureComptableService.save(ecritureComptableDTO);
        return ResponseEntity.created(new URI("/api/ecritures-comptables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EcritureComptableDTO> updateEcritureComptable(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EcritureComptableDTO EcritureComptableDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update EcritureComptable : {}, {}", id, EcritureComptableDTO);
        if (EcritureComptableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, EcritureComptableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!ecritureComptableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        EcritureComptableDTO result = ecritureComptableService.update(EcritureComptableDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EcritureComptableDTO> partialUpdateEcritureComptable(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EcritureComptableDTO EcritureComptableDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EcritureComptable partially : {}, {}", id, EcritureComptableDTO);
        if (EcritureComptableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, EcritureComptableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!ecritureComptableRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<EcritureComptableDTO> result = ecritureComptableService.partialUpdate(EcritureComptableDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, EcritureComptableDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<EcritureComptableDTO>> getAllEcritureComptables(
        EcritureComptableCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        LOG.debug("REST request to get EcritureComptables by criteria: {}", criteria);
        Page<EcritureComptableDTO> page = ecritureComptableService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countEcritureComptables(EcritureComptableCriteria criteria) {
        LOG.debug("REST request to count EcritureComptables by criteria: {}", criteria);
        return ResponseEntity.ok(ecritureComptableService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EcritureComptableDTO> getEcritureComptable(@PathVariable("id") Long id) {
        LOG.debug("REST request to get EcritureComptable : {}", id);
        Optional<EcritureComptableDTO> ecritureComptableDTO = ecritureComptableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ecritureComptableDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEcritureComptable(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete EcritureComptable : {}", id);
        ecritureComptableService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
