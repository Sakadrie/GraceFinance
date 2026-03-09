package com.gracefinance.gracefinanceapp.web.rest.referentiel;

import com.gracefinance.gracefinanceapp.repository.referentiel.VilleRepository;
import com.gracefinance.gracefinanceapp.service.criteria.referentiel.VilleCriteria;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.VilleDTO;
import com.gracefinance.gracefinanceapp.service.referentiel.VilleService;
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
@RequestMapping("/api/villes")
public class VilleResource {

    private static final Logger LOG = LoggerFactory.getLogger(VilleResource.class);
    private static final String ENTITY_NAME = "ville";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VilleService villeService;
    private final VilleRepository villeRepository;

    public VilleResource(VilleService villeService, VilleRepository villeRepository) {
        this.villeService = villeService;
        this.villeRepository = villeRepository;
    }

    @PostMapping("")
    public ResponseEntity<VilleDTO> createVille(@Valid @RequestBody VilleDTO villeDTO) throws URISyntaxException {
        if (villeDTO.getId() != null) throw new BadRequestAlertException("A new ville cannot already have an ID", ENTITY_NAME, "idexists");
        VilleDTO result = villeService.save(villeDTO);
        return ResponseEntity.created(new URI("/api/villes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VilleDTO> updateVille(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VilleDTO villeDTO
    ) throws URISyntaxException {
        if (villeDTO.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        if (!Objects.equals(id, villeDTO.getId())) throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        if (!villeRepository.existsById(id)) throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        VilleDTO result = villeService.update(villeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VilleDTO> partialUpdateVille(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VilleDTO villeDTO
    ) throws URISyntaxException {
        if (villeDTO.getId() == null) throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        if (!Objects.equals(id, villeDTO.getId())) throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        if (!villeRepository.existsById(id)) throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        Optional<VilleDTO> result = villeService.partialUpdate(villeDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, villeDTO.getId().toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<VilleDTO>> getAllVilles(
        VilleCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        HttpServletRequest request
    ) {
        Page<VilleDTO> page = villeService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromRequestUri(request), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countVilles(VilleCriteria criteria) {
        return ResponseEntity.ok(villeService.countByCriteria(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VilleDTO> getVille(@PathVariable("id") Long id) {
        return ResponseUtil.wrapOrNotFound(villeService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVille(@PathVariable("id") Long id) {
        villeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
