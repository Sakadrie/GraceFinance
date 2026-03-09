package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.repository.principal.LigneEcritureRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.LigneEcritureCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.LigneEcritureDTO;
import com.gracefinance.gracefinanceapp.service.principal.LigneEcritureService;
import com.gracefinance.gracefinanceapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ForwardedHeaderUtils;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture}.
 */
@RestController
@RequestMapping("/api/ligne-ecritures")
public class LigneEcritureResource {

    private static final Logger LOG = LoggerFactory.getLogger(LigneEcritureResource.class);

    private static final String ENTITY_NAME = "ligneEcriture";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneEcritureService ligneEcritureService;

    private final LigneEcritureRepository ligneEcritureRepository;

    public LigneEcritureResource(LigneEcritureService ligneEcritureService, LigneEcritureRepository ligneEcritureRepository) {
        this.ligneEcritureService = ligneEcritureService;
        this.ligneEcritureRepository = ligneEcritureRepository;
    }

    /**
     * {@code POST  /ligne-ecritures} : Create a new ligneEcriture.
     *
     * @param ligneEcritureDTO the ligneEcritureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligneEcritureDTO, or with status {@code 400 (Bad Request)} if the ligneEcriture has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<LigneEcritureDTO>> createLigneEcriture(@Valid @RequestBody LigneEcritureDTO ligneEcritureDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save LigneEcriture : {}", ligneEcritureDTO);
        if (ligneEcritureDTO.getId() != null) {
            throw new BadRequestAlertException("A new ligneEcriture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return ligneEcritureService
            .save(ligneEcritureDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/ligne-ecritures/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /ligne-ecritures/:id} : Updates an existing ligneEcriture.
     *
     * @param id the id of the ligneEcritureDTO to save.
     * @param ligneEcritureDTO the ligneEcritureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneEcritureDTO,
     * or with status {@code 400 (Bad Request)} if the ligneEcritureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligneEcritureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<LigneEcritureDTO>> updateLigneEcriture(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LigneEcritureDTO ligneEcritureDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update LigneEcriture : {}, {}", id, ligneEcritureDTO);
        if (ligneEcritureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneEcritureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return ligneEcritureRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return ligneEcritureService
                    .update(ligneEcritureDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /ligne-ecritures/:id} : Partial updates given fields of an existing ligneEcriture, field will ignore if it is null
     *
     * @param id the id of the ligneEcritureDTO to save.
     * @param ligneEcritureDTO the ligneEcritureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneEcritureDTO,
     * or with status {@code 400 (Bad Request)} if the ligneEcritureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ligneEcritureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ligneEcritureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<LigneEcritureDTO>> partialUpdateLigneEcriture(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LigneEcritureDTO ligneEcritureDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update LigneEcriture partially : {}, {}", id, ligneEcritureDTO);
        if (ligneEcritureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneEcritureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return ligneEcritureRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<LigneEcritureDTO> result = ligneEcritureService.partialUpdate(ligneEcritureDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /ligne-ecritures} : get all the ligneEcritures.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligneEcritures in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<LigneEcritureDTO>>> getAllLigneEcritures(
        LigneEcritureCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get LigneEcritures by criteria: {}", criteria);
        return ligneEcritureService
            .countByCriteria(criteria)
            .zipWith(ligneEcritureService.findByCriteria(criteria, pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity.ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            ForwardedHeaderUtils.adaptFromForwardedHeaders(request.getURI(), request.getHeaders()),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /ligne-ecritures/count} : count all the ligneEcritures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> countLigneEcritures(LigneEcritureCriteria criteria) {
        LOG.debug("REST request to count LigneEcritures by criteria: {}", criteria);
        return ligneEcritureService.countByCriteria(criteria).map(count -> ResponseEntity.status(HttpStatus.OK).body(count));
    }

    /**
     * {@code GET  /ligne-ecritures/:id} : get the "id" ligneEcriture.
     *
     * @param id the id of the ligneEcritureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligneEcritureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<LigneEcritureDTO>> getLigneEcriture(@PathVariable("id") Long id) {
        LOG.debug("REST request to get LigneEcriture : {}", id);
        Mono<LigneEcritureDTO> ligneEcritureDTO = ligneEcritureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ligneEcritureDTO);
    }

    /**
     * {@code DELETE  /ligne-ecritures/:id} : delete the "id" ligneEcriture.
     *
     * @param id the id of the ligneEcritureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteLigneEcriture(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete LigneEcriture : {}", id);
        return ligneEcritureService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
