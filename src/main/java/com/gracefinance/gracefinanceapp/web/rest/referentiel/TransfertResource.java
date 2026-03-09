package com.gracefinance.gracefinanceapp.web.rest.referentiel;

import com.gracefinance.gracefinanceapp.repository.referentiel.TransfertRepository;
import com.gracefinance.gracefinanceapp.service.criteria.referentiel.TransfertCriteria;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.TransfertDTO;
import com.gracefinance.gracefinanceapp.service.referentiel.TransfertService;
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
 * REST controller for managing {@link com.gracefinance.gracefinanceapp.domain.referentiel.Transfert}.
 */
@RestController
@RequestMapping("/api/transferts")
public class TransfertResource {

    private static final Logger LOG = LoggerFactory.getLogger(TransfertResource.class);
    private static final String ENTITY_NAME = "transfert";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransfertService transfertService;
    private final TransfertRepository transfertRepository;

    public TransfertResource(TransfertService transfertService, TransfertRepository transfertRepository) {
        this.transfertService = transfertService;
        this.transfertRepository = transfertRepository;
    }

    @PostMapping("")
    public ResponseEntity<TransfertDTO> createTransfert(@Valid @RequestBody TransfertDTO transfertDTO) throws URISyntaxException {
        LOG.debug("REST request to save Transfert : {}", transfertDTO);
        if (transfertDTO.getId() != null) {
            throw new BadRequestAlertException("A new transfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransfertDTO result = transfertService.save(transfertDTO);
        return ResponseEntity.created(new URI("/api/transferts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransfertDTO> updateTransfert(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TransfertDTO transfertDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Transfert : {}, {}", id, transfertDTO);
        if (transfertDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transfertDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!transfertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        TransfertDTO result = transfertService.update(transfertDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TransfertDTO> partialUpdateTransfert(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TransfertDTO transfertDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Transfert partially : {}, {}", id, transfertDTO);
        if (transfertDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, transfertDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!transfertRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<TransfertDTO> result = transfertService.partialUpdate(transfertDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transfertDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<TransfertDTO>> getAllTransferts(
        TransfertCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        LOG.debug("REST request to get Transferts by criteria: {}", criteria);
        Page<TransfertDTO> page = transfertService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countTransferts(TransfertCriteria criteria) {
        LOG.debug("REST request to count Transferts by criteria: {}", criteria);
        return ResponseEntity.ok(transfertService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransfertDTO> getTransfert(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Transfert : {}", id);
        Optional<TransfertDTO> transfertDTO = transfertService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transfertDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransfert(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Transfert : {}", id);
        transfertService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
