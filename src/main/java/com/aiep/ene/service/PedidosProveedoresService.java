package com.aiep.ene.service;

import com.aiep.ene.domain.PedidosProveedores;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.ene.domain.PedidosProveedores}.
 */
public interface PedidosProveedoresService {
    /**
     * Save a pedidosProveedores.
     *
     * @param pedidosProveedores the entity to save.
     * @return the persisted entity.
     */
    PedidosProveedores save(PedidosProveedores pedidosProveedores);

    /**
     * Updates a pedidosProveedores.
     *
     * @param pedidosProveedores the entity to update.
     * @return the persisted entity.
     */
    PedidosProveedores update(PedidosProveedores pedidosProveedores);

    /**
     * Partially updates a pedidosProveedores.
     *
     * @param pedidosProveedores the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PedidosProveedores> partialUpdate(PedidosProveedores pedidosProveedores);

    /**
     * Get all the pedidosProveedores.
     *
     * @return the list of entities.
     */
    List<PedidosProveedores> findAll();

    /**
     * Get the "id" pedidosProveedores.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PedidosProveedores> findOne(Long id);

    /**
     * Delete the "id" pedidosProveedores.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
