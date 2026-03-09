package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.CompteComptable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CompteComptable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteComptableRepository extends JpaRepository<CompteComptable, Long>, JpaSpecificationExecutor<CompteComptable> {
    Page<CompteComptable> findAllBy(Pageable pageable);
}
