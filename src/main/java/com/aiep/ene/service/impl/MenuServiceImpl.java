package com.aiep.ene.service.impl;

import com.aiep.ene.domain.Menu;
import com.aiep.ene.repository.MenuRepository;
import com.aiep.ene.service.MenuService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.ene.domain.Menu}.
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private static final Logger LOG = LoggerFactory.getLogger(MenuServiceImpl.class);

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public Menu save(Menu menu) {
        LOG.debug("Request to save Menu : {}", menu);
        return menuRepository.save(menu);
    }

    @Override
    public Menu update(Menu menu) {
        LOG.debug("Request to update Menu : {}", menu);
        return menuRepository.save(menu);
    }

    @Override
    public Optional<Menu> partialUpdate(Menu menu) {
        LOG.debug("Request to partially update Menu : {}", menu);

        return menuRepository
            .findById(menu.getId())
            .map(existingMenu -> {
                if (menu.getNombrePlato() != null) {
                    existingMenu.setNombrePlato(menu.getNombrePlato());
                }
                if (menu.getValorPlato() != null) {
                    existingMenu.setValorPlato(menu.getValorPlato());
                }
                if (menu.getDescripcionPlato() != null) {
                    existingMenu.setDescripcionPlato(menu.getDescripcionPlato());
                }

                return existingMenu;
            })
            .map(menuRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        LOG.debug("Request to get all Menus");
        return menuRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Menu> findOne(Long id) {
        LOG.debug("Request to get Menu : {}", id);
        return menuRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Menu : {}", id);
        menuRepository.deleteById(id);
    }
}
