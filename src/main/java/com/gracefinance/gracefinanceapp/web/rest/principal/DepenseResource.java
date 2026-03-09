package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.repository.principal.DepenseRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.DepenseCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.DepenseDTO;
import com.gracefinance.gracefinanceapp.service.principal.DepenseService;
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
 * REST controller for managing {@link com.gracefinance.gracefinanceapp.domain.principal.Depense}.
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

    /**
     * {@code POST  /depenses} : Create a new depense.
     *
     * @param depenseDTO the depenseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depenseDTO, or with status {@code 400 (Bad Request)} if the depense has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<DepenseDTO>> createDepense(@Valid @RequestBody DepenseDTO depenseDTO) throws URISyntaxException {
        LOG.debug("REST request to save Depense : {}", depenseDTO);
        if (depenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new depense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return depenseService
            .save(depenseDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/depenses/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /depenses/:id} : Updates an existing depense.
     *
     * @param id the id of the depenseDTO to save.
     * @param depenseDTO the depenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depenseDTO,
     * or with status {@code 400 (Bad Request)} if the depenseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<DepenseDTO>> updateDepense(
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

        return depenseRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return depenseService
                    .update(depenseDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /depenses/:id} : Partial updates given fields of an existing depense, field will ignore if it is null
     *
     * @param id the id of the depenseDTO to save.
     * @param depenseDTO the depenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depenseDTO,
     * or with status {@code 400 (Bad Request)} if the depenseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the depenseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the depenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<DepenseDTO>> partialUpdateDepense(
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

        return depenseRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<DepenseDTO> result = depenseService.partialUpdate(depenseDTO);

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
     * {@code GET  /depenses} : get all the depenses.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of depenses in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<DepenseDTO>>> getAllDepenses(
        DepenseCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get Depenses by criteria: {}", criteria);
        return depenseService
            .countByCriteria(criteria)
            .zipWith(depenseService.findByCriteria(criteria, pageable).collectList())
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
     * {@code GET  /depenses/count} : count all the depenses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> countDepenses(DepenseCriteria criteria) {
        LOG.debug("REST request to count Depenses by criteria: {}", criteria);
        return depenseService.countByCriteria(criteria).map(count -> ResponseEntity.status(HttpStatus.OK).body(count));
    }

    /**
     * {@code GET  /depenses/:id} : get the "id" depense.
     *
     * @param id the id of the depenseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depenseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<DepenseDTO>> getDepense(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Depense : {}", id);
        Mono<DepenseDTO> depenseDTO = depenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depenseDTO);
    }

    /**
     * {@code DELETE  /depenses/:id} : delete the "id" depense.
     *
     * @param id the id of the depenseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteDepense(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Depense : {}", id);
        return depenseService
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
