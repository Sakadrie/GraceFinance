package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.Depense;
import com.gracefinance.gracefinanceapp.service.criteria.principal.DepenseCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Depense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepenseRepository extends ReactiveCrudRepository<Depense, Long>, DepenseRepositoryInternal {
    Flux<Depense> findAllBy(Pageable pageable);

    @Query("SELECT * FROM depense entity WHERE entity.entite_financiere_id = :id")
    Flux<Depense> findByEntiteFinanciere(Long id);

    @Query("SELECT * FROM depense entity WHERE entity.entite_financiere_id IS NULL")
    Flux<Depense> findAllWhereEntiteFinanciereIsNull();

    @Query("SELECT * FROM depense entity WHERE entity.caisse_id = :id")
    Flux<Depense> findByCaisse(Long id);

    @Query("SELECT * FROM depense entity WHERE entity.caisse_id IS NULL")
    Flux<Depense> findAllWhereCaisseIsNull();

    @Query("SELECT * FROM depense entity WHERE entity.categorie_id = :id")
    Flux<Depense> findByCategorie(Long id);

    @Query("SELECT * FROM depense entity WHERE entity.categorie_id IS NULL")
    Flux<Depense> findAllWhereCategorieIsNull();

    @Override
    <S extends Depense> Mono<S> save(S entity);

    @Override
    Flux<Depense> findAll();

    @Override
    Mono<Depense> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface DepenseRepositoryInternal {
    <S extends Depense> Mono<S> save(S entity);

    Flux<Depense> findAllBy(Pageable pageable);

    Flux<Depense> findAll();

    Mono<Depense> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Depense> findAllBy(Pageable pageable, Criteria criteria);
    Flux<Depense> findByCriteria(DepenseCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(DepenseCriteria criteria);
}
