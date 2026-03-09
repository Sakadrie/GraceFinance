package com.gracefinance.gracefinanceapp.repository.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.Transfert;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transfert entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransfertRepository extends JpaRepository<Transfert, Long>, JpaSpecificationExecutor<Transfert> {
    @Query("select t from Transfert t where t.entiteFinanciereSource.id = :id")
    List<Transfert> findByEntiteFinanciereSource(Long id);

    @Query("select t from Transfert t where t.entiteFinanciereSource is null")
    List<Transfert> findAllWhereEntiteFinanciereSourceIsNull();

    @Query("select t from Transfert t where t.caisseSource.id = :id")
    List<Transfert> findByCaisseSource(Long id);

    @Query("select t from Transfert t where t.caisseSource is null")
    List<Transfert> findAllWhereCaisseSourceIsNull();

    @Query("select t from Transfert t where t.caisseDestination.id = :id")
    List<Transfert> findByCaisseDestination(Long id);

    @Query("select t from Transfert t where t.caisseDestination is null")
    List<Transfert> findAllWhereCaisseDestinationIsNull();

    Page<Transfert> findAllBy(Pageable pageable);
}
