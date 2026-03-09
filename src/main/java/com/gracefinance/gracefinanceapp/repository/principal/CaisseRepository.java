package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.service.criteria.principal.CaisseCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Caisse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaisseRepository extends ReactiveCrudRepository<Caisse, Long>, CaisseRepositoryInternal {
    Flux<Caisse> findAllBy(Pageable pageable);

    @Query("SELECT * FROM caisse entity WHERE entity.entite_financiere_id = :id")
    Flux<Caisse> findByEntiteFinanciere(Long id);

    @Query("SELECT * FROM caisse entity WHERE entity.entite_financiere_id IS NULL")
    Flux<Caisse> findAllWhereEntiteFinanciereIsNull();

    @Override
    <S extends Caisse> Mono<S> save(S entity);

    @Override
    Flux<Caisse> findAll();

    @Override
    Mono<Caisse> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface CaisseRepositoryInternal {
    <S extends Caisse> Mono<S> save(S entity);

    Flux<Caisse> findAllBy(Pageable pageable);

    Flux<Caisse> findAll();

    Mono<Caisse> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Caisse> findAllBy(Pageable pageable, Criteria criteria);
    Flux<Caisse> findByCriteria(CaisseCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(CaisseCriteria criteria);
}
