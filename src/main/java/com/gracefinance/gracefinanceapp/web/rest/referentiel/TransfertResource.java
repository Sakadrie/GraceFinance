package com.gracefinance.gracefinanceapp.web.rest.referentiel;

import com.gracefinance.gracefinanceapp.domain.criteria.TransfertCriteria;
import com.gracefinance.gracefinanceapp.repository.referentiel.TransfertRepository;
import com.gracefinance.gracefinanceapp.service.dto.referentiel.TransfertDTO;
import com.gracefinance.gracefinanceapp.service.referentiel.TransfertService;
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

    /**
     * {@code POST  /transferts} : Create a new transfert.
     *
     * @param transfertDTO the transfertDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transfertDTO, or with status {@code 400 (Bad Request)} if the transfert has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<TransfertDTO>> createTransfert(@Valid @RequestBody TransfertDTO transfertDTO) throws URISyntaxException {
        LOG.debug("REST request to save Transfert : {}", transfertDTO);
        if (transfertDTO.getId() != null) {
            throw new BadRequestAlertException("A new transfert cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return transfertService
            .save(transfertDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/transferts/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /transferts/:id} : Updates an existing transfert.
     *
     * @param id the id of the transfertDTO to save.
     * @param transfertDTO the transfertDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transfertDTO,
     * or with status {@code 400 (Bad Request)} if the transfertDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transfertDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<TransfertDTO>> updateTransfert(
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

        return transfertRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return transfertService
                    .update(transfertDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /transferts/:id} : Partial updates given fields of an existing transfert, field will ignore if it is null
     *
     * @param id the id of the transfertDTO to save.
     * @param transfertDTO the transfertDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transfertDTO,
     * or with status {@code 400 (Bad Request)} if the transfertDTO is not valid,
     * or with status {@code 404 (Not Found)} if the transfertDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the transfertDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<TransfertDTO>> partialUpdateTransfert(
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

        return transfertRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<TransfertDTO> result = transfertService.partialUpdate(transfertDTO);

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
     * {@code GET  /transferts} : get all the transferts.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transferts in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<TransfertDTO>>> getAllTransferts(
        TransfertCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get Transferts by criteria: {}", criteria);
        return transfertService
            .countByCriteria(criteria)
            .zipWith(transfertService.findByCriteria(criteria, pageable).collectList())
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
     * {@code GET  /transferts/count} : count all the transferts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> countTransferts(TransfertCriteria criteria) {
        LOG.debug("REST request to count Transferts by criteria: {}", criteria);
        return transfertService.countByCriteria(criteria).map(count -> ResponseEntity.status(HttpStatus.OK).body(count));
    }

    /**
     * {@code GET  /transferts/:id} : get the "id" transfert.
     *
     * @param id the id of the transfertDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transfertDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransfertDTO>> getTransfert(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Transfert : {}", id);
        Mono<TransfertDTO> transfertDTO = transfertService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transfertDTO);
    }

    /**
     * {@code DELETE  /transferts/:id} : delete the "id" transfert.
     *
     * @param id the id of the transfertDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTransfert(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Transfert : {}", id);
        return transfertService
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
