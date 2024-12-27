package com.aiep.ene.service.impl;

import com.aiep.ene.domain.Mesa;
import com.aiep.ene.repository.MesaRepository;
import com.aiep.ene.service.MesaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.ene.domain.Mesa}.
 */
@Service
@Transactional
public class MesaServiceImpl implements MesaService {

    private static final Logger LOG = LoggerFactory.getLogger(MesaServiceImpl.class);

    private final MesaRepository mesaRepository;

    public MesaServiceImpl(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @Override
    public Mesa save(Mesa mesa) {
        LOG.debug("Request to save Mesa : {}", mesa);
        return mesaRepository.save(mesa);
    }

    @Override
    public Mesa update(Mesa mesa) {
        LOG.debug("Request to update Mesa : {}", mesa);
        return mesaRepository.save(mesa);
    }

    @Override
    public Optional<Mesa> partialUpdate(Mesa mesa) {
        LOG.debug("Request to partially update Mesa : {}", mesa);

        return mesaRepository
            .findById(mesa.getId())
            .map(existingMesa -> {
                if (mesa.getNumero() != null) {
                    existingMesa.setNumero(mesa.getNumero());
                }

                return existingMesa;
            })
            .map(mesaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mesa> findAll() {
        LOG.debug("Request to get all Mesas");
        return mesaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Mesa> findOne(Long id) {
        LOG.debug("Request to get Mesa : {}", id);
        return mesaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Mesa : {}", id);
        mesaRepository.deleteById(id);
    }
}
