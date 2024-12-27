package com.aiep.ene.service;

import com.aiep.ene.domain.EgresosDiarios;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.ene.domain.EgresosDiarios}.
 */
public interface EgresosDiariosService {
    /**
     * Save a egresosDiarios.
     *
     * @param egresosDiarios the entity to save.
     * @return the persisted entity.
     */
    EgresosDiarios save(EgresosDiarios egresosDiarios);

    /**
     * Updates a egresosDiarios.
     *
     * @param egresosDiarios the entity to update.
     * @return the persisted entity.
     */
    EgresosDiarios update(EgresosDiarios egresosDiarios);

    /**
     * Partially updates a egresosDiarios.
     *
     * @param egresosDiarios the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EgresosDiarios> partialUpdate(EgresosDiarios egresosDiarios);

    /**
     * Get all the egresosDiarios.
     *
     * @return the list of entities.
     */
    List<EgresosDiarios> findAll();

    /**
     * Get the "id" egresosDiarios.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EgresosDiarios> findOne(Long id);

    /**
     * Delete the "id" egresosDiarios.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
