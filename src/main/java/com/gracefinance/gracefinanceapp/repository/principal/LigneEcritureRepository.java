package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LigneEcriture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigneEcritureRepository extends JpaRepository<LigneEcriture, Long>, JpaSpecificationExecutor<LigneEcriture> {
    @Query("select l from LigneEcriture l where l.ecriture.id = :id")
    List<LigneEcriture> findByEcriture(Long id);

    @Query("select l from LigneEcriture l where l.ecriture is null")
    List<LigneEcriture> findAllWhereEcritureIsNull();

    @Query("select l from LigneEcriture l where l.compte.id = :id")
    List<LigneEcriture> findByCompte(Long id);

    @Query("select l from LigneEcriture l where l.compte is null")
    List<LigneEcriture> findAllWhereCompteIsNull();

    Page<LigneEcriture> findAllBy(Pageable pageable);
}
