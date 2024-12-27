package com.aiep.ene.service.impl;

import com.aiep.ene.domain.Producto;
import com.aiep.ene.repository.ProductoRepository;
import com.aiep.ene.service.ProductoService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.ene.domain.Producto}.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository productoRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public Producto save(Producto producto) {
        LOG.debug("Request to save Producto : {}", producto);
        return productoRepository.save(producto);
    }

    @Override
    public Producto update(Producto producto) {
        LOG.debug("Request to update Producto : {}", producto);
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> partialUpdate(Producto producto) {
        LOG.debug("Request to partially update Producto : {}", producto);

        return productoRepository
            .findById(producto.getId())
            .map(existingProducto -> {
                if (producto.getNombre() != null) {
                    existingProducto.setNombre(producto.getNombre());
                }
                if (producto.getStock() != null) {
                    existingProducto.setStock(producto.getStock());
                }

                return existingProducto;
            })
            .map(productoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        LOG.debug("Request to get all Productos");
        return productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> findOne(Long id) {
        LOG.debug("Request to get Producto : {}", id);
        return productoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Producto : {}", id);
        productoRepository.deleteById(id);
    }
}
