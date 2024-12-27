package com.aiep.ene.service.impl;

import com.aiep.ene.domain.IngresosDiarios;
import com.aiep.ene.repository.IngresosDiariosRepository;
import com.aiep.ene.service.IngresosDiariosService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.ene.domain.IngresosDiarios}.
 */
@Service
@Transactional
public class IngresosDiariosServiceImpl implements IngresosDiariosService {

    private static final Logger LOG = LoggerFactory.getLogger(IngresosDiariosServiceImpl.class);

    private final IngresosDiariosRepository ingresosDiariosRepository;

    public IngresosDiariosServiceImpl(IngresosDiariosRepository ingresosDiariosRepository) {
        this.ingresosDiariosRepository = ingresosDiariosRepository;
    }

    @Override
    public IngresosDiarios save(IngresosDiarios ingresosDiarios) {
        LOG.debug("Request to save IngresosDiarios : {}", ingresosDiarios);
        return ingresosDiariosRepository.save(ingresosDiarios);
    }

    @Override
    public IngresosDiarios update(IngresosDiarios ingresosDiarios) {
        LOG.debug("Request to update IngresosDiarios : {}", ingresosDiarios);
        return ingresosDiariosRepository.save(ingresosDiarios);
    }

    @Override
    public Optional<IngresosDiarios> partialUpdate(IngresosDiarios ingresosDiarios) {
        LOG.debug("Request to partially update IngresosDiarios : {}", ingresosDiarios);

        return ingresosDiariosRepository
            .findById(ingresosDiarios.getId())
            .map(existingIngresosDiarios -> {
                if (ingresosDiarios.getVentaNumero() != null) {
                    existingIngresosDiarios.setVentaNumero(ingresosDiarios.getVentaNumero());
                }
                if (ingresosDiarios.getValorVenta() != null) {
                    existingIngresosDiarios.setValorVenta(ingresosDiarios.getValorVenta());
                }

                return existingIngresosDiarios;
            })
            .map(ingresosDiariosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngresosDiarios> findAll() {
        LOG.debug("Request to get all IngresosDiarios");
        return ingresosDiariosRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IngresosDiarios> findOne(Long id) {
        LOG.debug("Request to get IngresosDiarios : {}", id);
        return ingresosDiariosRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete IngresosDiarios : {}", id);
        ingresosDiariosRepository.deleteById(id);
    }
}
