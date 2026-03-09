package com.gracefinance.gracefinanceapp.web.rest.security;

import com.gracefinance.gracefinanceapp.repository.security.DroitRepository;
import com.gracefinance.gracefinanceapp.service.dto.security.DroitDTO;
import com.gracefinance.gracefinanceapp.service.security.DroitService;
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
 * REST controller for managing {@link com.gracefinance.gracefinanceapp.domain.security.Droit}.
 */
@RestController
@RequestMapping("/api/droits")
public class DroitResource {

    private static final Logger LOG = LoggerFactory.getLogger(DroitResource.class);
    private static final String ENTITY_NAME = "droit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DroitService droitService;
    private final DroitRepository droitRepository;

    public DroitResource(DroitService droitService, DroitRepository droitRepository) {
        this.droitService = droitService;
        this.droitRepository = droitRepository;
    }

    @PostMapping("")
    public ResponseEntity<DroitDTO> createDroit(@Valid @RequestBody DroitDTO droitDTO) throws URISyntaxException {
        LOG.debug("REST request to save Droit : {}", droitDTO);
        if (droitDTO.getId() != null) {
            throw new BadRequestAlertException("A new droit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DroitDTO result = droitService.save(droitDTO);
        return ResponseEntity.created(new URI("/api/droits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DroitDTO> updateDroit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DroitDTO droitDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Droit : {}, {}", id, droitDTO);
        if (droitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!droitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        DroitDTO result = droitService.update(droitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DroitDTO> partialUpdateDroit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DroitDTO droitDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Droit partially : {}, {}", id, droitDTO);
        if (droitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!droitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<DroitDTO> result = droitService.partialUpdate(droitDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, droitDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<DroitDTO>> getAllDroits(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        LOG.debug("REST request to get a page of Droits");
        Page<DroitDTO> page = droitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DroitDTO> getDroit(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Droit : {}", id);
        Optional<DroitDTO> droitDTO = droitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(droitDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDroit(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Droit : {}", id);
        droitService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
