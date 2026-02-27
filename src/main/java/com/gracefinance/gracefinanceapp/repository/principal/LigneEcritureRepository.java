package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.criteria.LigneEcritureCriteria;
import com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the LigneEcriture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneEcritureRepository extends ReactiveCrudRepository<LigneEcriture, Long>, LigneEcritureRepositoryInternal {
    Flux<LigneEcriture> findAllBy(Pageable pageable);

    @Query("SELECT * FROM ligne_ecriture entity WHERE entity.ecriture_id = :id")
    Flux<LigneEcriture> findByEcriture(Long id);

    @Query("SELECT * FROM ligne_ecriture entity WHERE entity.ecriture_id IS NULL")
    Flux<LigneEcriture> findAllWhereEcritureIsNull();

    @Query("SELECT * FROM ligne_ecriture entity WHERE entity.compte_id = :id")
    Flux<LigneEcriture> findByCompte(Long id);

    @Query("SELECT * FROM ligne_ecriture entity WHERE entity.compte_id IS NULL")
    Flux<LigneEcriture> findAllWhereCompteIsNull();

    @Override
    <S extends LigneEcriture> Mono<S> save(S entity);

    @Override
    Flux<LigneEcriture> findAll();

    @Override
    Mono<LigneEcriture> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface LigneEcritureRepositoryInternal {
    <S extends LigneEcriture> Mono<S> save(S entity);

    Flux<LigneEcriture> findAllBy(Pageable pageable);

    Flux<LigneEcriture> findAll();

    Mono<LigneEcriture> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<LigneEcriture> findAllBy(Pageable pageable, Criteria criteria);
    Flux<LigneEcriture> findByCriteria(LigneEcritureCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(LigneEcritureCriteria criteria);
}
