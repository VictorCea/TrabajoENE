package com.aiep.ene.service.impl;

import com.aiep.ene.domain.PedidosProveedores;
import com.aiep.ene.repository.PedidosProveedoresRepository;
import com.aiep.ene.service.PedidosProveedoresService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.ene.domain.PedidosProveedores}.
 */
@Service
@Transactional
public class PedidosProveedoresServiceImpl implements PedidosProveedoresService {

    private static final Logger LOG = LoggerFactory.getLogger(PedidosProveedoresServiceImpl.class);

    private final PedidosProveedoresRepository pedidosProveedoresRepository;

    public PedidosProveedoresServiceImpl(PedidosProveedoresRepository pedidosProveedoresRepository) {
        this.pedidosProveedoresRepository = pedidosProveedoresRepository;
    }

    @Override
    public PedidosProveedores save(PedidosProveedores pedidosProveedores) {
        LOG.debug("Request to save PedidosProveedores : {}", pedidosProveedores);
        return pedidosProveedoresRepository.save(pedidosProveedores);
    }

    @Override
    public PedidosProveedores update(PedidosProveedores pedidosProveedores) {
        LOG.debug("Request to update PedidosProveedores : {}", pedidosProveedores);
        return pedidosProveedoresRepository.save(pedidosProveedores);
    }

    @Override
    public Optional<PedidosProveedores> partialUpdate(PedidosProveedores pedidosProveedores) {
        LOG.debug("Request to partially update PedidosProveedores : {}", pedidosProveedores);

        return pedidosProveedoresRepository
            .findById(pedidosProveedores.getId())
            .map(existingPedidosProveedores -> {
                if (pedidosProveedores.getProducto() != null) {
                    existingPedidosProveedores.setProducto(pedidosProveedores.getProducto());
                }
                if (pedidosProveedores.getCantidad() != null) {
                    existingPedidosProveedores.setCantidad(pedidosProveedores.getCantidad());
                }
                if (pedidosProveedores.getValor() != null) {
                    existingPedidosProveedores.setValor(pedidosProveedores.getValor());
                }

                return existingPedidosProveedores;
            })
            .map(pedidosProveedoresRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidosProveedores> findAll() {
        LOG.debug("Request to get all PedidosProveedores");
        return pedidosProveedoresRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PedidosProveedores> findOne(Long id) {
        LOG.debug("Request to get PedidosProveedores : {}", id);
        return pedidosProveedoresRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PedidosProveedores : {}", id);
        pedidosProveedoresRepository.deleteById(id);
    }
}
