package com.aiep.ene.service.impl;

import com.aiep.ene.domain.EgresosDiarios;
import com.aiep.ene.repository.EgresosDiariosRepository;
import com.aiep.ene.service.EgresosDiariosService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.ene.domain.EgresosDiarios}.
 */
@Service
@Transactional
public class EgresosDiariosServiceImpl implements EgresosDiariosService {

    private static final Logger LOG = LoggerFactory.getLogger(EgresosDiariosServiceImpl.class);

    private final EgresosDiariosRepository egresosDiariosRepository;

    public EgresosDiariosServiceImpl(EgresosDiariosRepository egresosDiariosRepository) {
        this.egresosDiariosRepository = egresosDiariosRepository;
    }

    @Override
    public EgresosDiarios save(EgresosDiarios egresosDiarios) {
        LOG.debug("Request to save EgresosDiarios : {}", egresosDiarios);
        return egresosDiariosRepository.save(egresosDiarios);
    }

    @Override
    public EgresosDiarios update(EgresosDiarios egresosDiarios) {
        LOG.debug("Request to update EgresosDiarios : {}", egresosDiarios);
        return egresosDiariosRepository.save(egresosDiarios);
    }

    @Override
    public Optional<EgresosDiarios> partialUpdate(EgresosDiarios egresosDiarios) {
        LOG.debug("Request to partially update EgresosDiarios : {}", egresosDiarios);

        return egresosDiariosRepository
            .findById(egresosDiarios.getId())
            .map(existingEgresosDiarios -> {
                if (egresosDiarios.getGastoDiario() != null) {
                    existingEgresosDiarios.setGastoDiario(egresosDiarios.getGastoDiario());
                }
                if (egresosDiarios.getValorGasto() != null) {
                    existingEgresosDiarios.setValorGasto(egresosDiarios.getValorGasto());
                }

                return existingEgresosDiarios;
            })
            .map(egresosDiariosRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EgresosDiarios> findAll() {
        LOG.debug("Request to get all EgresosDiarios");
        return egresosDiariosRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EgresosDiarios> findOne(Long id) {
        LOG.debug("Request to get EgresosDiarios : {}", id);
        return egresosDiariosRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete EgresosDiarios : {}", id);
        egresosDiariosRepository.deleteById(id);
    }
}
