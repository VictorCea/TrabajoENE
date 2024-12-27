package com.aiep.ene.service.impl;

import com.aiep.ene.domain.OrdenesCocina;
import com.aiep.ene.repository.OrdenesCocinaRepository;
import com.aiep.ene.service.OrdenesCocinaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.ene.domain.OrdenesCocina}.
 */
@Service
@Transactional
public class OrdenesCocinaServiceImpl implements OrdenesCocinaService {

    private static final Logger LOG = LoggerFactory.getLogger(OrdenesCocinaServiceImpl.class);

    private final OrdenesCocinaRepository ordenesCocinaRepository;

    public OrdenesCocinaServiceImpl(OrdenesCocinaRepository ordenesCocinaRepository) {
        this.ordenesCocinaRepository = ordenesCocinaRepository;
    }

    @Override
    public OrdenesCocina save(OrdenesCocina ordenesCocina) {
        LOG.debug("Request to save OrdenesCocina : {}", ordenesCocina);
        return ordenesCocinaRepository.save(ordenesCocina);
    }

    @Override
    public OrdenesCocina update(OrdenesCocina ordenesCocina) {
        LOG.debug("Request to update OrdenesCocina : {}", ordenesCocina);
        return ordenesCocinaRepository.save(ordenesCocina);
    }

    @Override
    public Optional<OrdenesCocina> partialUpdate(OrdenesCocina ordenesCocina) {
        LOG.debug("Request to partially update OrdenesCocina : {}", ordenesCocina);

        return ordenesCocinaRepository
            .findById(ordenesCocina.getId())
            .map(existingOrdenesCocina -> {
                if (ordenesCocina.getNombrePlato() != null) {
                    existingOrdenesCocina.setNombrePlato(ordenesCocina.getNombrePlato());
                }
                if (ordenesCocina.getCantidadPlato() != null) {
                    existingOrdenesCocina.setCantidadPlato(ordenesCocina.getCantidadPlato());
                }

                return existingOrdenesCocina;
            })
            .map(ordenesCocinaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenesCocina> findAll() {
        LOG.debug("Request to get all OrdenesCocinas");
        return ordenesCocinaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrdenesCocina> findOne(Long id) {
        LOG.debug("Request to get OrdenesCocina : {}", id);
        return ordenesCocinaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete OrdenesCocina : {}", id);
        ordenesCocinaRepository.deleteById(id);
    }
}
