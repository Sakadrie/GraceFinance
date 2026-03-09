package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Caisse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CaisseRepository extends JpaRepository<Caisse, Long>, JpaSpecificationExecutor<Caisse> {
    @Query("select c from Caisse c where c.entiteFinanciere.id = :id")
    List<Caisse> findByEntiteFinanciere(Long id);

    @Query("select c from Caisse c where c.entiteFinanciere is null")
    List<Caisse> findAllWhereEntiteFinanciereIsNull();

    Page<Caisse> findAllBy(Pageable pageable);
}
