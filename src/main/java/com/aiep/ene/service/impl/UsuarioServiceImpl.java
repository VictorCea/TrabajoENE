package com.aiep.ene.service.impl;

import com.aiep.ene.domain.Usuario;
import com.aiep.ene.repository.UsuarioRepository;
import com.aiep.ene.service.UsuarioService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.aiep.ene.domain.Usuario}.
 */
@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        LOG.debug("Request to save Usuario : {}", usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        LOG.debug("Request to update Usuario : {}", usuario);
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> partialUpdate(Usuario usuario) {
        LOG.debug("Request to partially update Usuario : {}", usuario);

        return usuarioRepository
            .findById(usuario.getId())
            .map(existingUsuario -> {
                if (usuario.getNombre() != null) {
                    existingUsuario.setNombre(usuario.getNombre());
                }
                if (usuario.getRol() != null) {
                    existingUsuario.setRol(usuario.getRol());
                }
                if (usuario.getRut() != null) {
                    existingUsuario.setRut(usuario.getRut());
                }
                if (usuario.getAdmin() != null) {
                    existingUsuario.setAdmin(usuario.getAdmin());
                }

                return existingUsuario;
            })
            .map(usuarioRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        LOG.debug("Request to get all Usuarios");
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findOne(Long id) {
        LOG.debug("Request to get Usuario : {}", id);
        return usuarioRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Usuario : {}", id);
        usuarioRepository.deleteById(id);
    }
}
