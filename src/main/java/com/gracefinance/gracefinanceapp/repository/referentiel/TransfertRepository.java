package com.gracefinance.gracefinanceapp.repository.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.Transfert;
import com.gracefinance.gracefinanceapp.service.criteria.referentiel.TransfertCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Transfert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransfertRepository extends ReactiveCrudRepository<Transfert, Long>, TransfertRepositoryInternal {
    Flux<Transfert> findAllBy(Pageable pageable);

    @Query("SELECT * FROM transfert entity WHERE entity.entite_financiere_source_id = :id")
    Flux<Transfert> findByEntiteFinanciereSource(Long id);

    @Query("SELECT * FROM transfert entity WHERE entity.entite_financiere_source_id IS NULL")
    Flux<Transfert> findAllWhereEntiteFinanciereSourceIsNull();

    @Query("SELECT * FROM transfert entity WHERE entity.caisse_source_id = :id")
    Flux<Transfert> findByCaisseSource(Long id);

    @Query("SELECT * FROM transfert entity WHERE entity.caisse_source_id IS NULL")
    Flux<Transfert> findAllWhereCaisseSourceIsNull();

    @Query("SELECT * FROM transfert entity WHERE entity.caisse_destination_id = :id")
    Flux<Transfert> findByCaisseDestination(Long id);

    @Query("SELECT * FROM transfert entity WHERE entity.caisse_destination_id IS NULL")
    Flux<Transfert> findAllWhereCaisseDestinationIsNull();

    @Override
    <S extends Transfert> Mono<S> save(S entity);

    @Override
    Flux<Transfert> findAll();

    @Override
    Mono<Transfert> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface TransfertRepositoryInternal {
    <S extends Transfert> Mono<S> save(S entity);

    Flux<Transfert> findAllBy(Pageable pageable);

    Flux<Transfert> findAll();

    Mono<Transfert> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Transfert> findAllBy(Pageable pageable, Criteria criteria);
    Flux<Transfert> findByCriteria(TransfertCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(TransfertCriteria criteria);
}
