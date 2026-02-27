package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.domain.criteria.CompteComptableCriteria;
import com.gracefinance.gracefinanceapp.repository.principal.CompteComptableRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.CompteComptableDTO;
import com.gracefinance.gracefinanceapp.service.principal.CompteComptableService;
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
 * REST controller for managing {@link com.gracefinance.gracefinanceapp.domain.principal.CompteComptable}.
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

    /**
     * {@code POST  /compte-comptables} : Create a new compteComptable.
     *
     * @param compteComptableDTO the compteComptableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compteComptableDTO, or with status {@code 400 (Bad Request)} if the compteComptable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<CompteComptableDTO>> createCompteComptable(@Valid @RequestBody CompteComptableDTO compteComptableDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CompteComptable : {}", compteComptableDTO);
        if (compteComptableDTO.getId() != null) {
            throw new BadRequestAlertException("A new compteComptable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return compteComptableService
            .save(compteComptableDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/compte-comptables/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /compte-comptables/:id} : Updates an existing compteComptable.
     *
     * @param id the id of the compteComptableDTO to save.
     * @param compteComptableDTO the compteComptableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compteComptableDTO,
     * or with status {@code 400 (Bad Request)} if the compteComptableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compteComptableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<CompteComptableDTO>> updateCompteComptable(
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

        return compteComptableRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return compteComptableService
                    .update(compteComptableDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /compte-comptables/:id} : Partial updates given fields of an existing compteComptable, field will ignore if it is null
     *
     * @param id the id of the compteComptableDTO to save.
     * @param compteComptableDTO the compteComptableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compteComptableDTO,
     * or with status {@code 400 (Bad Request)} if the compteComptableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the compteComptableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the compteComptableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<CompteComptableDTO>> partialUpdateCompteComptable(
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

        return compteComptableRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<CompteComptableDTO> result = compteComptableService.partialUpdate(compteComptableDTO);

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
     * {@code GET  /compte-comptables} : get all the compteComptables.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compteComptables in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<CompteComptableDTO>>> getAllCompteComptables(
        CompteComptableCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get CompteComptables by criteria: {}", criteria);
        return compteComptableService
            .countByCriteria(criteria)
            .zipWith(compteComptableService.findByCriteria(criteria, pageable).collectList())
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
     * {@code GET  /compte-comptables/count} : count all the compteComptables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> countCompteComptables(CompteComptableCriteria criteria) {
        LOG.debug("REST request to count CompteComptables by criteria: {}", criteria);
        return compteComptableService.countByCriteria(criteria).map(count -> ResponseEntity.status(HttpStatus.OK).body(count));
    }

    /**
     * {@code GET  /compte-comptables/:id} : get the "id" compteComptable.
     *
     * @param id the id of the compteComptableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compteComptableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<CompteComptableDTO>> getCompteComptable(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CompteComptable : {}", id);
        Mono<CompteComptableDTO> compteComptableDTO = compteComptableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compteComptableDTO);
    }

    /**
     * {@code DELETE  /compte-comptables/:id} : delete the "id" compteComptable.
     *
     * @param id the id of the compteComptableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCompteComptable(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CompteComptable : {}", id);
        return compteComptableService
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
