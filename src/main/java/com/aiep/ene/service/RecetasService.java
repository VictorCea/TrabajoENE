package com.aiep.ene.service;

import com.aiep.ene.domain.Recetas;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.ene.domain.Recetas}.
 */
public interface RecetasService {
    /**
     * Save a recetas.
     *
     * @param recetas the entity to save.
     * @return the persisted entity.
     */
    Recetas save(Recetas recetas);

    /**
     * Updates a recetas.
     *
     * @param recetas the entity to update.
     * @return the persisted entity.
     */
    Recetas update(Recetas recetas);

    /**
     * Partially updates a recetas.
     *
     * @param recetas the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Recetas> partialUpdate(Recetas recetas);

    /**
     * Get all the recetas.
     *
     * @return the list of entities.
     */
    List<Recetas> findAll();

    /**
     * Get the "id" recetas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Recetas> findOne(Long id);

    /**
     * Delete the "id" recetas.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
