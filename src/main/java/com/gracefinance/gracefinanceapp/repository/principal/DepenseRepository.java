package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.Depense;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Depense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long>, JpaSpecificationExecutor<Depense> {
    @Query("select d from Depense d where d.entiteFinanciere.id = :id")
    List<Depense> findByEntiteFinanciere(Long id);

    @Query("select d from Depense d where d.entiteFinanciere is null")
    List<Depense> findAllWhereEntiteFinanciereIsNull();

    @Query("select d from Depense d where d.caisse.id = :id")
    List<Depense> findByCaisse(Long id);

    @Query("select d from Depense d where d.caisse is null")
    List<Depense> findAllWhereCaisseIsNull();

    @Query("select d from Depense d where d.categorie.id = :id")
    List<Depense> findByCategorie(Long id);

    @Query("select d from Depense d where d.categorie is null")
    List<Depense> findAllWhereCategorieIsNull();

    Page<Depense> findAllBy(Pageable pageable);
}
