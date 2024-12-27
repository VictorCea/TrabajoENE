package com.aiep.ene.repository;

import com.aiep.ene.domain.PedidosProveedores;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PedidosProveedores entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidosProveedoresRepository extends JpaRepository<PedidosProveedores, Long> {}
