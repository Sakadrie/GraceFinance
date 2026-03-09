package com.gracefinance.gracefinanceapp.repository.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long>, JpaSpecificationExecutor<District> {}
