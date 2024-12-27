package com.aiep.ene.service;

import com.aiep.ene.domain.IngresosDiarios;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.ene.domain.IngresosDiarios}.
 */
public interface IngresosDiariosService {
    /**
     * Save a ingresosDiarios.
     *
     * @param ingresosDiarios the entity to save.
     * @return the persisted entity.
     */
    IngresosDiarios save(IngresosDiarios ingresosDiarios);

    /**
     * Updates a ingresosDiarios.
     *
     * @param ingresosDiarios the entity to update.
     * @return the persisted entity.
     */
    IngresosDiarios update(IngresosDiarios ingresosDiarios);

    /**
     * Partially updates a ingresosDiarios.
     *
     * @param ingresosDiarios the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IngresosDiarios> partialUpdate(IngresosDiarios ingresosDiarios);

    /**
     * Get all the ingresosDiarios.
     *
     * @return the list of entities.
     */
    List<IngresosDiarios> findAll();

    /**
     * Get the "id" ingresosDiarios.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IngresosDiarios> findOne(Long id);

    /**
     * Delete the "id" ingresosDiarios.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
