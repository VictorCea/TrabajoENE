package com.aiep.ene.service;

import com.aiep.ene.domain.Menu;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiep.ene.domain.Menu}.
 */
public interface MenuService {
    /**
     * Save a menu.
     *
     * @param menu the entity to save.
     * @return the persisted entity.
     */
    Menu save(Menu menu);

    /**
     * Updates a menu.
     *
     * @param menu the entity to update.
     * @return the persisted entity.
     */
    Menu update(Menu menu);

    /**
     * Partially updates a menu.
     *
     * @param menu the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Menu> partialUpdate(Menu menu);

    /**
     * Get all the menus.
     *
     * @return the list of entities.
     */
    List<Menu> findAll();

    /**
     * Get the "id" menu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Menu> findOne(Long id);

    /**
     * Delete the "id" menu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
