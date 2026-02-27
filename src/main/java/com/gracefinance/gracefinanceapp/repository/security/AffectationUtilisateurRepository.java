package com.gracefinance.gracefinanceapp.repository.security;

import com.gracefinance.gracefinanceapp.domain.criteria.AffectationUtilisateurCriteria;
import com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the AffectationUtilisateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffectationUtilisateurRepository
    extends ReactiveCrudRepository<AffectationUtilisateur, Long>, AffectationUtilisateurRepositoryInternal {
    Flux<AffectationUtilisateur> findAllBy(Pageable pageable);

    @Override
    Mono<AffectationUtilisateur> findOneWithEagerRelationships(Long id);

    @Override
    Flux<AffectationUtilisateur> findAllWithEagerRelationships();

    @Override
    Flux<AffectationUtilisateur> findAllWithEagerRelationships(Pageable page);

    @Query("SELECT * FROM affectation_utilisateur entity WHERE entity.user_id = :id")
    Flux<AffectationUtilisateur> findByUser(Long id);

    @Query("SELECT * FROM affectation_utilisateur entity WHERE entity.user_id IS NULL")
    Flux<AffectationUtilisateur> findAllWhereUserIsNull();

    @Query("SELECT * FROM affectation_utilisateur entity WHERE entity.entite_financiere_id = :id")
    Flux<AffectationUtilisateur> findByEntiteFinanciere(Long id);

    @Query("SELECT * FROM affectation_utilisateur entity WHERE entity.entite_financiere_id IS NULL")
    Flux<AffectationUtilisateur> findAllWhereEntiteFinanciereIsNull();

    @Query(
        "SELECT entity.* FROM affectation_utilisateur entity JOIN rel_affectation_utilisateur__profil joinTable ON entity.id = joinTable.profil_id WHERE joinTable.profil_id = :id"
    )
    Flux<AffectationUtilisateur> findByProfil(Long id);

    @Override
    <S extends AffectationUtilisateur> Mono<S> save(S entity);

    @Override
    Flux<AffectationUtilisateur> findAll();

    @Override
    Mono<AffectationUtilisateur> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface AffectationUtilisateurRepositoryInternal {
    <S extends AffectationUtilisateur> Mono<S> save(S entity);

    Flux<AffectationUtilisateur> findAllBy(Pageable pageable);

    Flux<AffectationUtilisateur> findAll();

    Mono<AffectationUtilisateur> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<AffectationUtilisateur> findAllBy(Pageable pageable, Criteria criteria);
    Flux<AffectationUtilisateur> findByCriteria(AffectationUtilisateurCriteria criteria, Pageable pageable);

    Mono<Long> countByCriteria(AffectationUtilisateurCriteria criteria);

    Mono<AffectationUtilisateur> findOneWithEagerRelationships(Long id);

    Flux<AffectationUtilisateur> findAllWithEagerRelationships();

    Flux<AffectationUtilisateur> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
