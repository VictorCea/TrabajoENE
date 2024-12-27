package com.aiep.ene.service.impl;

import com.aiep.ene.domain.Proveedor;
import com.aiep.ene.repository.ProveedorRepository;
import com.aiep.ene.service.ProveedorService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.ene.domain.Proveedor}.
 */
@Service
@Transactional
public class ProveedorServiceImpl implements ProveedorService {

    private static final Logger LOG = LoggerFactory.getLogger(ProveedorServiceImpl.class);

    private final ProveedorRepository proveedorRepository;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public Proveedor save(Proveedor proveedor) {
        LOG.debug("Request to save Proveedor : {}", proveedor);
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor update(Proveedor proveedor) {
        LOG.debug("Request to update Proveedor : {}", proveedor);
        return proveedorRepository.save(proveedor);
    }

    @Override
    public Optional<Proveedor> partialUpdate(Proveedor proveedor) {
        LOG.debug("Request to partially update Proveedor : {}", proveedor);

        return proveedorRepository
            .findById(proveedor.getId())
            .map(existingProveedor -> {
                if (proveedor.getNombre() != null) {
                    existingProveedor.setNombre(proveedor.getNombre());
                }
                if (proveedor.getDireccion() != null) {
                    existingProveedor.setDireccion(proveedor.getDireccion());
                }
                if (proveedor.getEmail() != null) {
                    existingProveedor.setEmail(proveedor.getEmail());
                }
                if (proveedor.getFono() != null) {
                    existingProveedor.setFono(proveedor.getFono());
                }

                return existingProveedor;
            })
            .map(proveedorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> findAll() {
        LOG.debug("Request to get all Proveedors");
        return proveedorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Proveedor> findOne(Long id) {
        LOG.debug("Request to get Proveedor : {}", id);
        return proveedorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Proveedor : {}", id);
        proveedorRepository.deleteById(id);
    }
}
