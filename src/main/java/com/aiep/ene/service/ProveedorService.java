package com.aiep.ene.service;

import com.aiep.ene.domain.Proveedor;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.ene.domain.Proveedor}.
 */
public interface ProveedorService {
    /**
     * Save a proveedor.
     *
     * @param proveedor the entity to save.
     * @return the persisted entity.
     */
    Proveedor save(Proveedor proveedor);

    /**
     * Updates a proveedor.
     *
     * @param proveedor the entity to update.
     * @return the persisted entity.
     */
    Proveedor update(Proveedor proveedor);

    /**
     * Partially updates a proveedor.
     *
     * @param proveedor the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Proveedor> partialUpdate(Proveedor proveedor);

    /**
     * Get all the proveedors.
     *
     * @return the list of entities.
     */
    List<Proveedor> findAll();

    /**
     * Get the "id" proveedor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Proveedor> findOne(Long id);

    /**
     * Delete the "id" proveedor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
