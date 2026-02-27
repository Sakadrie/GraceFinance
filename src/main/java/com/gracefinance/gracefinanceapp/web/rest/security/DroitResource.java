package com.gracefinance.gracefinanceapp.web.rest.security;

import com.gracefinance.gracefinanceapp.repository.security.DroitRepository;
import com.gracefinance.gracefinanceapp.service.dto.security.DroitDTO;
import com.gracefinance.gracefinanceapp.service.security.DroitService;
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
 * REST controller for managing {@link com.gracefinance.gracefinanceapp.domain.security.Droit}.
 */
@RestController
@RequestMapping("/api/droits")
public class DroitResource {

    private static final Logger LOG = LoggerFactory.getLogger(DroitResource.class);

    private static final String ENTITY_NAME = "droit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DroitService droitService;

    private final DroitRepository droitRepository;

    public DroitResource(DroitService droitService, DroitRepository droitRepository) {
        this.droitService = droitService;
        this.droitRepository = droitRepository;
    }

    /**
     * {@code POST  /droits} : Create a new droit.
     *
     * @param droitDTO the droitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new droitDTO, or with status {@code 400 (Bad Request)} if the droit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<DroitDTO>> createDroit(@Valid @RequestBody DroitDTO droitDTO) throws URISyntaxException {
        LOG.debug("REST request to save Droit : {}", droitDTO);
        if (droitDTO.getId() != null) {
            throw new BadRequestAlertException("A new droit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return droitService
            .save(droitDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/droits/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /droits/:id} : Updates an existing droit.
     *
     * @param id the id of the droitDTO to save.
     * @param droitDTO the droitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitDTO,
     * or with status {@code 400 (Bad Request)} if the droitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the droitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<DroitDTO>> updateDroit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DroitDTO droitDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Droit : {}, {}", id, droitDTO);
        if (droitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return droitRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return droitService
                    .update(droitDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /droits/:id} : Partial updates given fields of an existing droit, field will ignore if it is null
     *
     * @param id the id of the droitDTO to save.
     * @param droitDTO the droitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated droitDTO,
     * or with status {@code 400 (Bad Request)} if the droitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the droitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the droitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<DroitDTO>> partialUpdateDroit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DroitDTO droitDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Droit partially : {}, {}", id, droitDTO);
        if (droitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, droitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return droitRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<DroitDTO> result = droitService.partialUpdate(droitDTO);

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
     * {@code GET  /droits} : get all the droits.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of droits in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<DroitDTO>>> getAllDroits(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get a page of Droits");
        return droitService
            .countAll()
            .zipWith(droitService.findAll(pageable).collectList())
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
     * {@code GET  /droits/:id} : get the "id" droit.
     *
     * @param id the id of the droitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the droitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<DroitDTO>> getDroit(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Droit : {}", id);
        Mono<DroitDTO> droitDTO = droitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(droitDTO);
    }

    /**
     * {@code DELETE  /droits/:id} : delete the "id" droit.
     *
     * @param id the id of the droitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteDroit(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Droit : {}", id);
        return droitService
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
