package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EntiteFinanciere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntiteFinanciereRepository extends JpaRepository<EntiteFinanciere, Long> {
    Page<EntiteFinanciere> findAllBy(Pageable pageable);

    @Query(
        value = "select e from EntiteFinanciere e left join fetch e.egliseLiees",
        countQuery = "select count(distinct e) from EntiteFinanciere e"
    )
    Page<EntiteFinanciere> findAllWithEagerRelationships(Pageable pageable);

    @Query("select e from EntiteFinanciere e left join fetch e.egliseLiees")
    List<EntiteFinanciere> findAllWithEagerRelationships();

    @Query("select e from EntiteFinanciere e left join fetch e.egliseLiees where e.id = :id")
    Optional<EntiteFinanciere> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select e from EntiteFinanciere e join e.egliseLiees liee where liee.id = :id")
    List<EntiteFinanciere> findByEgliseLiee(@Param("id") Long id);
}
