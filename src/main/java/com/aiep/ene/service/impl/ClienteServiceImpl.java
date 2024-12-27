package com.aiep.ene.service.impl;

import com.aiep.ene.domain.Cliente;
import com.aiep.ene.repository.ClienteRepository;
import com.aiep.ene.service.ClienteService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.ene.domain.Cliente}.
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private static final Logger LOG = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {
        LOG.debug("Request to save Cliente : {}", cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente update(Cliente cliente) {
        LOG.debug("Request to update Cliente : {}", cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> partialUpdate(Cliente cliente) {
        LOG.debug("Request to partially update Cliente : {}", cliente);

        return clienteRepository
            .findById(cliente.getId())
            .map(existingCliente -> {
                if (cliente.getMesa() != null) {
                    existingCliente.setMesa(cliente.getMesa());
                }
                if (cliente.getOrden() != null) {
                    existingCliente.setOrden(cliente.getOrden());
                }
                if (cliente.getCantidadClientes() != null) {
                    existingCliente.setCantidadClientes(cliente.getCantidadClientes());
                }

                return existingCliente;
            })
            .map(clienteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        LOG.debug("Request to get all Clientes");
        return clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findOne(Long id) {
        LOG.debug("Request to get Cliente : {}", id);
        return clienteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Cliente : {}", id);
        clienteRepository.deleteById(id);
    }
}
