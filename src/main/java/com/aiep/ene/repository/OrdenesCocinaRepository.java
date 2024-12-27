package com.aiep.ene.repository;

import com.aiep.ene.domain.OrdenesCocina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrdenesCocina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdenesCocinaRepository extends JpaRepository<OrdenesCocina, Long> {}
