package com.aiep.ene.web.rest;

import com.aiep.ene.domain.PedidosProveedores;
import com.aiep.ene.repository.PedidosProveedoresRepository;
import com.aiep.ene.service.PedidosProveedoresService;
import com.aiep.ene.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aiep.ene.domain.PedidosProveedores}.
 */
@RestController
@RequestMapping("/api/pedidos-proveedores")
public class PedidosProveedoresResource {

    private static final Logger LOG = LoggerFactory.getLogger(PedidosProveedoresResource.class);

    private static final String ENTITY_NAME = "pedidosProveedores";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PedidosProveedoresService pedidosProveedoresService;

    private final PedidosProveedoresRepository pedidosProveedoresRepository;

    public PedidosProveedoresResource(
        PedidosProveedoresService pedidosProveedoresService,
        PedidosProveedoresRepository pedidosProveedoresRepository
    ) {
        this.pedidosProveedoresService = pedidosProveedoresService;
        this.pedidosProveedoresRepository = pedidosProveedoresRepository;
    }

    /**
     * {@code POST  /pedidos-proveedores} : Create a new pedidosProveedores.
     *
     * @param pedidosProveedores the pedidosProveedores to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pedidosProveedores, or with status {@code 400 (Bad Request)} if the pedidosProveedores has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PedidosProveedores> createPedidosProveedores(@RequestBody PedidosProveedores pedidosProveedores)
        throws URISyntaxException {
        LOG.debug("REST request to save PedidosProveedores : {}", pedidosProveedores);
        if (pedidosProveedores.getId() != null) {
            throw new BadRequestAlertException("A new pedidosProveedores cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pedidosProveedores = pedidosProveedoresService.save(pedidosProveedores);
        return ResponseEntity.created(new URI("/api/pedidos-proveedores/" + pedidosProveedores.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pedidosProveedores.getId().toString()))
            .body(pedidosProveedores);
    }

    /**
     * {@code PUT  /pedidos-proveedores/:id} : Updates an existing pedidosProveedores.
     *
     * @param id the id of the pedidosProveedores to save.
     * @param pedidosProveedores the pedidosProveedores to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pedidosProveedores,
     * or with status {@code 400 (Bad Request)} if the pedidosProveedores is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pedidosProveedores couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PedidosProveedores> updatePedidosProveedores(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PedidosProveedores pedidosProveedores
    ) throws URISyntaxException {
        LOG.debug("REST request to update PedidosProveedores : {}, {}", id, pedidosProveedores);
        if (pedidosProveedores.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pedidosProveedores.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pedidosProveedoresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pedidosProveedores = pedidosProveedoresService.update(pedidosProveedores);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pedidosProveedores.getId().toString()))
            .body(pedidosProveedores);
    }

    /**
     * {@code PATCH  /pedidos-proveedores/:id} : Partial updates given fields of an existing pedidosProveedores, field will ignore if it is null
     *
     * @param id the id of the pedidosProveedores to save.
     * @param pedidosProveedores the pedidosProveedores to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pedidosProveedores,
     * or with status {@code 400 (Bad Request)} if the pedidosProveedores is not valid,
     * or with status {@code 404 (Not Found)} if the pedidosProveedores is not found,
     * or with status {@code 500 (Internal Server Error)} if the pedidosProveedores couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PedidosProveedores> partialUpdatePedidosProveedores(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PedidosProveedores pedidosProveedores
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PedidosProveedores partially : {}, {}", id, pedidosProveedores);
        if (pedidosProveedores.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pedidosProveedores.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pedidosProveedoresRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PedidosProveedores> result = pedidosProveedoresService.partialUpdate(pedidosProveedores);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pedidosProveedores.getId().toString())
        );
    }

    /**
     * {@code GET  /pedidos-proveedores} : get all the pedidosProveedores.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pedidosProveedores in body.
     */
    @GetMapping("")
    public List<PedidosProveedores> getAllPedidosProveedores() {
        LOG.debug("REST request to get all PedidosProveedores");
        return pedidosProveedoresService.findAll();
    }

    /**
     * {@code GET  /pedidos-proveedores/:id} : get the "id" pedidosProveedores.
     *
     * @param id the id of the pedidosProveedores to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pedidosProveedores, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidosProveedores> getPedidosProveedores(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PedidosProveedores : {}", id);
        Optional<PedidosProveedores> pedidosProveedores = pedidosProveedoresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pedidosProveedores);
    }

    /**
     * {@code DELETE  /pedidos-proveedores/:id} : delete the "id" pedidosProveedores.
     *
     * @param id the id of the pedidosProveedores to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedidosProveedores(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PedidosProveedores : {}", id);
        pedidosProveedoresService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
