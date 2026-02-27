package com.gracefinance.gracefinanceapp.web.rest.principal;

import com.gracefinance.gracefinanceapp.domain.criteria.RecetteCriteria;
import com.gracefinance.gracefinanceapp.repository.principal.RecetteRepository;
import com.gracefinance.gracefinanceapp.service.dto.principal.RecetteDTO;
import com.gracefinance.gracefinanceapp.service.principal.RecetteService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ForwardedHeaderUtils;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.gracefinance.gracefinanceapp.domain.principal.Recette}.
 */
@RestController
@RequestMapping("/api/recettes")
public class RecetteResource {

    private static final Logger LOG = LoggerFactory.getLogger(RecetteResource.class);

    private static final String ENTITY_NAME = "recette";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RecetteService recetteService;

    private final RecetteRepository recetteRepository;

    public RecetteResource(RecetteService recetteService, RecetteRepository recetteRepository) {
        this.recetteService = recetteService;
        this.recetteRepository = recetteRepository;
    }

    /**
     * {@code POST  /recettes} : Create a new recette.
     *
     * @param recetteDTO the recetteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recetteDTO, or with status {@code 400 (Bad Request)} if the recette has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<RecetteDTO>> createRecette(@Valid @RequestBody RecetteDTO recetteDTO) throws URISyntaxException {
        LOG.debug("REST request to save Recette : {}", recetteDTO);
        if (recetteDTO.getId() != null) {
            throw new BadRequestAlertException("A new recette cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return recetteService
            .save(recetteDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/recettes/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /recettes/:id} : Updates an existing recette.
     *
     * @param id the id of the recetteDTO to save.
     * @param recetteDTO the recetteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recetteDTO,
     * or with status {@code 400 (Bad Request)} if the recetteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recetteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<RecetteDTO>> updateRecette(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RecetteDTO recetteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Recette : {}, {}", id, recetteDTO);
        if (recetteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recetteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return recetteRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return recetteService
                    .update(recetteDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /recettes/:id} : Partial updates given fields of an existing recette, field will ignore if it is null
     *
     * @param id the id of the recetteDTO to save.
     * @param recetteDTO the recetteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recetteDTO,
     * or with status {@code 400 (Bad Request)} if the recetteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recetteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recetteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<RecetteDTO>> partialUpdateRecette(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RecetteDTO recetteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Recette partially : {}, {}", id, recetteDTO);
        if (recetteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recetteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return recetteRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<RecetteDTO> result = recetteService.partialUpdate(recetteDTO);

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
     * {@code GET  /recettes} : get all the recettes.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recettes in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<RecetteDTO>>> getAllRecettes(
        RecetteCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get Recettes by criteria: {}", criteria);
        return recetteService
            .countByCriteria(criteria)
            .zipWith(recetteService.findByCriteria(criteria, pageable).collectList())
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
     * {@code GET  /recettes/count} : count all the recettes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public Mono<ResponseEntity<Long>> countRecettes(RecetteCriteria criteria) {
        LOG.debug("REST request to count Recettes by criteria: {}", criteria);
        return recetteService.countByCriteria(criteria).map(count -> ResponseEntity.status(HttpStatus.OK).body(count));
    }

    /**
     * {@code GET  /recettes/:id} : get the "id" recette.
     *
     * @param id the id of the recetteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recetteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<RecetteDTO>> getRecette(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Recette : {}", id);
        Mono<RecetteDTO> recetteDTO = recetteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recetteDTO);
    }

    /**
     * {@code DELETE  /recettes/:id} : delete the "id" recette.
     *
     * @param id the id of the recetteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteRecette(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Recette : {}", id);
        return recetteService
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
