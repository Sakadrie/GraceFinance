package com.gracefinance.gracefinanceapp.repository.principal;

import com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EcritureComptable entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EcritureComptableRepository extends JpaRepository<EcritureComptable, Long>, JpaSpecificationExecutor<EcritureComptable> {
    Page<EcritureComptable> findAllBy(Pageable pageable);
}
