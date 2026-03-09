package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.Recette;
import com.gracefinance.gracefinanceapp.service.criteria.principal.RecetteCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Recette entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecetteRepository extends ReactiveCrudRepository<Recette, Long>, RecetteRepositoryInternal {
    Flux<Recette> findAllBy(Pageable pageable);

    @Query("SELECT * FROM recette entity WHERE entity.entite_financiere_id = :id")
    Flux<Recette> findByEntiteFinanciere(Long id);

    @Query("SELECT * FROM recette entity WHERE entity.entite_financiere_id IS NULL")
    Flux<Recette> findAllWhereEntiteFinanciereIsNull();

    @Query("SELECT * FROM recette entity WHERE entity.caisse_id = :id")
    Flux<Recette> findByCaisse(Long id);

    @Query("SELECT * FROM recette entity WHERE entity.caisse_id IS NULL")
    Flux<Recette> findAllWhereCaisseIsNull();

    @Query("SELECT * FROM recette entity WHERE entity.categorie_id = :id")
    Flux<Recette> findByCategorie(Long id);

    @Query("SELECT * FROM recette entity WHERE entity.categorie_id IS NULL")
    Flux<Recette> findAllWhereCategorieIsNull();

    @Override
    <S extends Recette> Mono<S> save(S entity);

    @Override
    Flux<Recette> findAll();

    @Override
    Mono<Recette> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface RecetteRepositoryInternal {
    <S extends Recette> Mono<S> save(S entity);

    Flux<Recette> findAllBy(Pageable pageable);

    Flux<Recette> findAll();

    Mono<Recette> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Recette> findAllBy(Pageable pageable, Criteria criteria);
    Flux<Recette> findByCriteria(RecetteCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(RecetteCriteria criteria);
}
