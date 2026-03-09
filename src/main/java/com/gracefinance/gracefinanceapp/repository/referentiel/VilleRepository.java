package com.gracefinance.gracefinanceapp.repository.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Long>, JpaSpecificationExecutor<Ville> {}
