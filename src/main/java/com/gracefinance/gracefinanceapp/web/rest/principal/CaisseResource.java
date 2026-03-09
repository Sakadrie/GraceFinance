package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.repository.principal.CaisseRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.CaisseCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.CaisseDTO;
import com.gracefinance.gracefinanceapp.service.principal.CaisseService;
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
 * REST controller for managing {@link com.gracefinance.gracefinanceapp.domain.principal.Caisse}.
 */
@RestController
@RequestMapping("/api/caisses")
public class CaisseResource {

    private static final Logger LOG = LoggerFactory.getLogger(CaisseResource.class);
    private static final String ENTITY_NAME = "caisse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CaisseService caisseService;
    private final CaisseRepository caisseRepository;

    public CaisseResource(CaisseService caisseService, CaisseRepository caisseRepository) {
        this.caisseService = caisseService;
        this.caisseRepository = caisseRepository;
    }

    @PostMapping("")
    public ResponseEntity<CaisseDTO> createCaisse(@Valid @RequestBody CaisseDTO caisseDTO) throws URISyntaxException {
        LOG.debug("REST request to save Caisse : {}", caisseDTO);
        if (caisseDTO.getId() != null) {
            throw new BadRequestAlertException("A new caisse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CaisseDTO result = caisseService.save(caisseDTO);
        return ResponseEntity.created(new URI("/api/caisses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CaisseDTO> updateCaisse(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CaisseDTO caisseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Caisse : {}, {}", id, caisseDTO);
        if (caisseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caisseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!caisseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        CaisseDTO result = caisseService.update(caisseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CaisseDTO> partialUpdateCaisse(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CaisseDTO caisseDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Caisse partially : {}, {}", id, caisseDTO);
        if (caisseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, caisseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!caisseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<CaisseDTO> result = caisseService.partialUpdate(caisseDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, caisseDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<CaisseDTO>> getAllCaisses(
        CaisseCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        LOG.debug("REST request to get Caisses by criteria: {}", criteria);
        Page<CaisseDTO> page = caisseService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countCaisses(CaisseCriteria criteria) {
        LOG.debug("REST request to count Caisses by criteria: {}", criteria);
        return ResponseEntity.ok(caisseService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaisseDTO> getCaisse(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Caisse : {}", id);
        Optional<CaisseDTO> caisseDTO = caisseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(caisseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCaisse(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Caisse : {}", id);
        caisseService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
