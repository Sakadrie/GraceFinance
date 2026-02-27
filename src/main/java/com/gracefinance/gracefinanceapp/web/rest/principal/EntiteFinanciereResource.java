package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.repository.principal.EntiteFinanciereRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.EntiteFinanciereDTO;
import com.gracefinance.gracefinanceapp.service.principal.EntiteFinanciereService;
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
 * REST controller for managing {@link com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere}.
 */
@RestController
@RequestMapping("/api/entite-financieres")
public class EntiteFinanciereResource {

    private static final Logger LOG = LoggerFactory.getLogger(EntiteFinanciereResource.class);

    private static final String ENTITY_NAME = "entiteFinanciere";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntiteFinanciereService entiteFinanciereService;

    private final EntiteFinanciereRepository entiteFinanciereRepository;

    public EntiteFinanciereResource(
        EntiteFinanciereService entiteFinanciereService,
        EntiteFinanciereRepository entiteFinanciereRepository
    ) {
        this.entiteFinanciereService = entiteFinanciereService;
        this.entiteFinanciereRepository = entiteFinanciereRepository;
    }

    /**
     * {@code POST  /entite-financieres} : Create a new entiteFinanciere.
     *
     * @param entiteFinanciereDTO the entiteFinanciereDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entiteFinanciereDTO, or with status {@code 400 (Bad Request)} if the entiteFinanciere has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<EntiteFinanciereDTO>> createEntiteFinanciere(@Valid @RequestBody EntiteFinanciereDTO entiteFinanciereDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save EntiteFinanciere : {}", entiteFinanciereDTO);
        if (entiteFinanciereDTO.getId() != null) {
            throw new BadRequestAlertException("A new entiteFinanciere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return entiteFinanciereService
            .save(entiteFinanciereDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/entite-financieres/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /entite-financieres/:id} : Updates an existing entiteFinanciere.
     *
     * @param id the id of the entiteFinanciereDTO to save.
     * @param entiteFinanciereDTO the entiteFinanciereDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entiteFinanciereDTO,
     * or with status {@code 400 (Bad Request)} if the entiteFinanciereDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entiteFinanciereDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<EntiteFinanciereDTO>> updateEntiteFinanciere(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EntiteFinanciereDTO entiteFinanciereDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update EntiteFinanciere : {}, {}", id, entiteFinanciereDTO);
        if (entiteFinanciereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entiteFinanciereDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return entiteFinanciereRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return entiteFinanciereService
                    .update(entiteFinanciereDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /entite-financieres/:id} : Partial updates given fields of an existing entiteFinanciere, field will ignore if it is null
     *
     * @param id the id of the entiteFinanciereDTO to save.
     * @param entiteFinanciereDTO the entiteFinanciereDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entiteFinanciereDTO,
     * or with status {@code 400 (Bad Request)} if the entiteFinanciereDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entiteFinanciereDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entiteFinanciereDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<EntiteFinanciereDTO>> partialUpdateEntiteFinanciere(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EntiteFinanciereDTO entiteFinanciereDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EntiteFinanciere partially : {}, {}", id, entiteFinanciereDTO);
        if (entiteFinanciereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entiteFinanciereDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return entiteFinanciereRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<EntiteFinanciereDTO> result = entiteFinanciereService.partialUpdate(entiteFinanciereDTO);

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
     * {@code GET  /entite-financieres} : get all the entiteFinancieres.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entiteFinancieres in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<EntiteFinanciereDTO>>> getAllEntiteFinancieres(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of EntiteFinancieres");
        return entiteFinanciereService
            .countAll()
            .zipWith(entiteFinanciereService.findAll(pageable).collectList())
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
     * {@code GET  /entite-financieres/:id} : get the "id" entiteFinanciere.
     *
     * @param id the id of the entiteFinanciereDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entiteFinanciereDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<EntiteFinanciereDTO>> getEntiteFinanciere(@PathVariable("id") Long id) {
        LOG.debug("REST request to get EntiteFinanciere : {}", id);
        Mono<EntiteFinanciereDTO> entiteFinanciereDTO = entiteFinanciereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entiteFinanciereDTO);
    }

    /**
     * {@code DELETE  /entite-financieres/:id} : delete the "id" entiteFinanciere.
     *
     * @param id the id of the entiteFinanciereDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteEntiteFinanciere(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete EntiteFinanciere : {}", id);
        return entiteFinanciereService
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
