package com.gracefinance.gracefinanceapp.web.rest.security;

import com.gracefinance.gracefinanceapp.repository.security.ProfilRepository;
import com.gracefinance.gracefinanceapp.service.criteria.security.ProfilCriteria;
import com.gracefinance.gracefinanceapp.service.dto.security.ProfilDTO;
import com.gracefinance.gracefinanceapp.service.security.ProfilService;
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
 * REST controller for managing {@link com.gracefinance.gracefinanceapp.domain.security.Profil}.
 */
@RestController
@RequestMapping("/api/profils")
public class ProfilResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProfilResource.class);
    private static final String ENTITY_NAME = "profil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfilService profilService;
    private final ProfilRepository profilRepository;

    public ProfilResource(ProfilService profilService, ProfilRepository profilRepository) {
        this.profilService = profilService;
        this.profilRepository = profilRepository;
    }

    @PostMapping("")
    public ResponseEntity<ProfilDTO> createProfil(@Valid @RequestBody ProfilDTO profilDTO) throws URISyntaxException {
        LOG.debug("REST request to save Profil : {}", profilDTO);
        if (profilDTO.getId() != null) {
            throw new BadRequestAlertException("A new profil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfilDTO result = profilService.save(profilDTO);
        return ResponseEntity.created(new URI("/api/profils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfilDTO> updateProfil(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProfilDTO profilDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Profil : {}, {}", id, profilDTO);
        if (profilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!profilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        ProfilDTO result = profilService.update(profilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProfilDTO> partialUpdateProfil(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProfilDTO profilDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Profil partially : {}, {}", id, profilDTO);
        if (profilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        if (!profilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<ProfilDTO> result = profilService.partialUpdate(profilDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profilDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<ProfilDTO>> getAllProfils(
        ProfilCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        LOG.debug("REST request to get Profils by criteria: {}", criteria);
        Page<ProfilDTO> page = profilService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countProfils(ProfilCriteria criteria) {
        LOG.debug("REST request to count Profils by criteria: {}", criteria);
        return ResponseEntity.ok(profilService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfilDTO> getProfil(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Profil : {}", id);
        Optional<ProfilDTO> profilDTO = profilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profilDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfil(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Profil : {}", id);
        profilService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
