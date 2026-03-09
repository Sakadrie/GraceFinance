package com.gracefinance.gracefinanceapp.repository.security;

import com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AffectationUtilisateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffectationUtilisateurRepository
    extends JpaRepository<AffectationUtilisateur, Long>, JpaSpecificationExecutor<AffectationUtilisateur> {
    Page<AffectationUtilisateur> findAllBy(Pageable pageable);

    @Query(
        value = "select a from AffectationUtilisateur a left join fetch a.profils",
        countQuery = "select count(distinct a) from AffectationUtilisateur a"
    )
    Page<AffectationUtilisateur> findAllWithEagerRelationships(Pageable pageable);

    @Query("select a from AffectationUtilisateur a left join fetch a.profils")
    List<AffectationUtilisateur> findAllWithEagerRelationships();

    @Query("select a from AffectationUtilisateur a left join fetch a.profils where a.id = :id")
    Optional<AffectationUtilisateur> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select a from AffectationUtilisateur a where a.user.id = :id")
    List<AffectationUtilisateur> findByUser(@Param("id") Long id);

    @Query("select a from AffectationUtilisateur a where a.user is null")
    List<AffectationUtilisateur> findAllWhereUserIsNull();

    @Query("select a from AffectationUtilisateur a where a.entiteFinanciere.id = :id")
    List<AffectationUtilisateur> findByEntiteFinanciere(@Param("id") Long id);

    @Query("select a from AffectationUtilisateur a where a.entiteFinanciere is null")
    List<AffectationUtilisateur> findAllWhereEntiteFinanciereIsNull();

    @Query("select a from AffectationUtilisateur a join a.profils p where p.id = :id")
    List<AffectationUtilisateur> findByProfil(@Param("id") Long id);
}
