package com.aiep.ene.service;

import com.aiep.ene.domain.Mesa;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.ene.domain.Mesa}.
 */
public interface MesaService {
    /**
     * Save a mesa.
     *
     * @param mesa the entity to save.
     * @return the persisted entity.
     */
    Mesa save(Mesa mesa);

    /**
     * Updates a mesa.
     *
     * @param mesa the entity to update.
     * @return the persisted entity.
     */
    Mesa update(Mesa mesa);

    /**
     * Partially updates a mesa.
     *
     * @param mesa the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Mesa> partialUpdate(Mesa mesa);

    /**
     * Get all the mesas.
     *
     * @return the list of entities.
     */
    List<Mesa> findAll();

    /**
     * Get the "id" mesa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Mesa> findOne(Long id);

    /**
     * Delete the "id" mesa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
