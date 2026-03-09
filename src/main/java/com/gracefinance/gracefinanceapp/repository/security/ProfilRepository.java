package com.gracefinance.gracefinanceapp.repository.security;

import com.gracefinance.gracefinanceapp.domain.security.Profil;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Profil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
    Page<Profil> findAllBy(Pageable pageable);

    @Query(value = "select p from Profil p left join fetch p.droits", countQuery = "select count(distinct p) from Profil p")
    Page<Profil> findAllWithEagerRelationships(Pageable pageable);

    @Query("select p from Profil p left join fetch p.droits")
    List<Profil> findAllWithEagerRelationships();

    @Query("select p from Profil p left join fetch p.droits where p.id = :id")
    Optional<Profil> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select p from Profil p join p.droits d where d.id = :id")
    List<Profil> findByDroit(@Param("id") Long id);
}
