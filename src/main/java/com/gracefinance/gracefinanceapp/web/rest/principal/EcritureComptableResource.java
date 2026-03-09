package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.repository.principal.EcritureComptableRepository;
import com.gracefinance.gracefinanceapp.service.criteria.principal.EcritureComptableCriteria;
import com.gracefinance.gracefinanceapp.service.dto.principal.EcritureComptableDTO;
import com.gracefinance.gracefinanceapp.service.principal.EcritureComptableService;
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
 * REST controller for managing {@link com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable}.
 */
@RestController
@RequestMapping("/api/ecriture-comptables")
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

    /**
     * {@code POST  /ecriture-comptables} : Create a new ecritureComptable.
     *
     * @param ecritureComptableDTO the ecritureComptableDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ecritureComptableDTO, or with status {@code 400 (Bad Request)} if the ecritureComptable has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<EcritureComptableDTO>> createEcritureComptable(
        @Valid @RequestBody EcritureComptableDTO ecritureComptableDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save EcritureComptable : {}", ecritureComptableDTO);
        if (ecritureComptableDTO.getId() != null) {
            throw new BadRequestAlertException("A new ecritureComptable cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return ecritureComptableService
            .save(ecritureComptableDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/ecriture-comptables/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /ecriture-comptables/:id} : Updates an existing ecritureComptable.
     *
     * @param id the id of the ecritureComptableDTO to save.
     * @param ecritureComptableDTO the ecritureComptableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecritureComptableDTO,
     * or with status {@code 400 (Bad Request)} if the ecritureComptableDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ecritureComptableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<EcritureComptableDTO>> updateEcritureComptable(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EcritureComptableDTO ecritureComptableDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update EcritureComptable : {}, {}", id, ecritureComptableDTO);
        if (ecritureComptableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecritureComptableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return ecritureComptableRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return ecritureComptableService
                    .update(ecritureComptableDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /ecriture-comptables/:id} : Partial updates given fields of an existing ecritureComptable, field will ignore if it is null
     *
     * @param id the id of the ecritureComptableDTO to save.
     * @param ecritureComptableDTO the ecritureComptableDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ecritureComptableDTO,
     * or with status {@code 400 (Bad Request)} if the ecritureComptableDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ecritureComptableDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ecritureComptableDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<EcritureComptableDTO>> partialUpdateEcritureComptable(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EcritureComptableDTO ecritureComptableDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EcritureComptable partially : {}, {}", id, ecritureComptableDTO);
        if (ecritureComptableDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ecritureComptableDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return ecritureComptableRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<EcritureComptableDTO> result = ecritureComptableService.partialUpdate(ecritureComptableDTO);

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
     * {@code GET  /ecriture-comptables} : get all the ecritureComptables.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ecritureComptables in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<EcritureComptableDTO>>> getAllEcritureComptables(
        EcritureComptableCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get EcritureComptables by criteria: {}", criteria);
        return ecritureComptableService
            .countByCriteria(criteria)
            .zipWith(ecritureComptableService.findByCriteria(criteria, pageable).collectList())
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
     * {@code GET  /ecriture-comptables/count} : count all the ecritureComptables.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> countEcritureComptables(EcritureComptableCriteria criteria) {
        LOG.debug("REST request to count EcritureComptables by criteria: {}", criteria);
        return ecritureComptableService.countByCriteria(criteria).map(count -> ResponseEntity.status(HttpStatus.OK).body(count));
    }

    /**
     * {@code GET  /ecriture-comptables/:id} : get the "id" ecritureComptable.
     *
     * @param id the id of the ecritureComptableDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ecritureComptableDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<EcritureComptableDTO>> getEcritureComptable(@PathVariable("id") Long id) {
        LOG.debug("REST request to get EcritureComptable : {}", id);
        Mono<EcritureComptableDTO> ecritureComptableDTO = ecritureComptableService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ecritureComptableDTO);
    }

    /**
     * {@code DELETE  /ecriture-comptables/:id} : delete the "id" ecritureComptable.
     *
     * @param id the id of the ecritureComptableDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteEcritureComptable(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete EcritureComptable : {}", id);
        return ecritureComptableService
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
