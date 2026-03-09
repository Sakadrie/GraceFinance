package com.gracefinance.gracefinanceapp.repository.security;

import com.gracefinance.gracefinanceapp.domain.security.Droit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Droit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroitRepository extends JpaRepository<Droit, Long> {
    Page<Droit> findAllBy(Pageable pageable);
}
