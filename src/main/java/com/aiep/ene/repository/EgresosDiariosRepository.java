package com.aiep.ene.repository;

import com.aiep.ene.domain.EgresosDiarios;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EgresosDiarios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EgresosDiariosRepository extends JpaRepository<EgresosDiarios, Long> {}
