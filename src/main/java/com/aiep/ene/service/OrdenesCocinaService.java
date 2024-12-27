package com.aiep.ene.service;

import com.aiep.ene.domain.OrdenesCocina;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.ene.domain.OrdenesCocina}.
 */
public interface OrdenesCocinaService {
    /**
     * Save a ordenesCocina.
     *
     * @param ordenesCocina the entity to save.
     * @return the persisted entity.
     */
    OrdenesCocina save(OrdenesCocina ordenesCocina);

    /**
     * Updates a ordenesCocina.
     *
     * @param ordenesCocina the entity to update.
     * @return the persisted entity.
     */
    OrdenesCocina update(OrdenesCocina ordenesCocina);

    /**
     * Partially updates a ordenesCocina.
     *
     * @param ordenesCocina the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrdenesCocina> partialUpdate(OrdenesCocina ordenesCocina);

    /**
     * Get all the ordenesCocinas.
     *
     * @return the list of entities.
     */
    List<OrdenesCocina> findAll();

    /**
     * Get the "id" ordenesCocina.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrdenesCocina> findOne(Long id);

    /**
     * Delete the "id" ordenesCocina.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
