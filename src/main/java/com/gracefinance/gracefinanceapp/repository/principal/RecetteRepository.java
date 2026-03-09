package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.Recette;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Recette entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecetteRepository extends JpaRepository<Recette, Long>, JpaSpecificationExecutor<Recette> {
    @Query("select r from Recette r where r.entiteFinanciere.id = :id")
    List<Recette> findByEntiteFinanciere(Long id);

    @Query("select r from Recette r where r.entiteFinanciere is null")
    List<Recette> findAllWhereEntiteFinanciereIsNull();

    @Query("select r from Recette r where r.caisse.id = :id")
    List<Recette> findByCaisse(Long id);

    @Query("select r from Recette r where r.caisse is null")
    List<Recette> findAllWhereCaisseIsNull();

    @Query("select r from Recette r where r.categorie.id = :id")
    List<Recette> findByCategorie(Long id);

    @Query("select r from Recette r where r.categorie is null")
    List<Recette> findAllWhereCategorieIsNull();

    Page<Recette> findAllBy(Pageable pageable);
}
