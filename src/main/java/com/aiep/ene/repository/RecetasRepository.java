package com.aiep.ene.repository;

import com.aiep.ene.domain.Recetas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Recetas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecetasRepository extends JpaRepository<Recetas, Long> {}
