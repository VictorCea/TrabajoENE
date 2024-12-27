package com.aiep.ene.repository;

import com.aiep.ene.domain.IngresosDiarios;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IngresosDiarios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IngresosDiariosRepository extends JpaRepository<IngresosDiarios, Long> {}
