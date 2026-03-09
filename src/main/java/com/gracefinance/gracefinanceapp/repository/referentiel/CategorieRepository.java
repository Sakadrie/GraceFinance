package com.gracefinance.gracefinanceapp.repository.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Categorie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long>, JpaSpecificationExecutor<Categorie> {
    @Query("select c from Categorie c where c.entiteFinanciere.id = :id")
    List<Categorie> findByEntiteFinanciere(Long id);

    @Query("select c from Categorie c where c.entiteFinanciere is null")
    List<Categorie> findAllWhereEntiteFinanciereIsNull();

    Page<Categorie> findAllBy(Pageable pageable);
}
