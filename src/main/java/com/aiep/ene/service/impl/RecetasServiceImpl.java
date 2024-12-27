package com.aiep.ene.service.impl;

import com.aiep.ene.domain.Recetas;
import com.aiep.ene.repository.RecetasRepository;
import com.aiep.ene.service.RecetasService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.ene.domain.Recetas}.
 */
@Service
@Transactional
public class RecetasServiceImpl implements RecetasService {

    private static final Logger LOG = LoggerFactory.getLogger(RecetasServiceImpl.class);

    private final RecetasRepository recetasRepository;

    public RecetasServiceImpl(RecetasRepository recetasRepository) {
        this.recetasRepository = recetasRepository;
    }

    @Override
    public Recetas save(Recetas recetas) {
        LOG.debug("Request to save Recetas : {}", recetas);
        return recetasRepository.save(recetas);
    }

    @Override
    public Recetas update(Recetas recetas) {
        LOG.debug("Request to update Recetas : {}", recetas);
        return recetasRepository.save(recetas);
    }

    @Override
    public Optional<Recetas> partialUpdate(Recetas recetas) {
        LOG.debug("Request to partially update Recetas : {}", recetas);

        return recetasRepository
            .findById(recetas.getId())
            .map(existingRecetas -> {
                if (recetas.getNombrePlato() != null) {
                    existingRecetas.setNombrePlato(recetas.getNombrePlato());
                }
                if (recetas.getIngredientesPlato() != null) {
                    existingRecetas.setIngredientesPlato(recetas.getIngredientesPlato());
                }
                if (recetas.getTiempoPreparacion() != null) {
                    existingRecetas.setTiempoPreparacion(recetas.getTiempoPreparacion());
                }

                return existingRecetas;
            })
            .map(recetasRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recetas> findAll() {
        LOG.debug("Request to get all Recetas");
        return recetasRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Recetas> findOne(Long id) {
        LOG.debug("Request to get Recetas : {}", id);
        return recetasRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Recetas : {}", id);
        recetasRepository.deleteById(id);
    }
}
