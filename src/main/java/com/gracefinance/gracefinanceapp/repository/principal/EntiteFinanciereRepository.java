package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the EntiteFinanciere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntiteFinanciereRepository extends ReactiveCrudRepository<EntiteFinanciere, Long>, EntiteFinanciereRepositoryInternal {
    Flux<EntiteFinanciere> findAllBy(Pageable pageable);

    @Override
    Mono<EntiteFinanciere> findOneWithEagerRelationships(Long id);

    @Override
    Flux<EntiteFinanciere> findAllWithEagerRelationships();

    @Override
    Flux<EntiteFinanciere> findAllWithEagerRelationships(Pageable page);

    @Query(
        "SELECT entity.* FROM entite_financiere entity JOIN rel_entite_financiere__eglise_liee joinTable ON entity.id = joinTable.eglise_liee_id WHERE joinTable.eglise_liee_id = :id"
    )
    Flux<EntiteFinanciere> findByEgliseLiee(Long id);

    @Override
    <S extends EntiteFinanciere> Mono<S> save(S entity);

    @Override
    Flux<EntiteFinanciere> findAll();

    @Override
    Mono<EntiteFinanciere> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface EntiteFinanciereRepositoryInternal {
    <S extends EntiteFinanciere> Mono<S> save(S entity);

    Flux<EntiteFinanciere> findAllBy(Pageable pageable);

    Flux<EntiteFinanciere> findAll();

    Mono<EntiteFinanciere> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<EntiteFinanciere> findAllBy(Pageable pageable, Criteria criteria);

    Mono<EntiteFinanciere> findOneWithEagerRelationships(Long id);

    Flux<EntiteFinanciere> findAllWithEagerRelationships();

    Flux<EntiteFinanciere> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
