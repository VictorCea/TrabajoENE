package com.aiep.ene.web.rest;

import com.aiep.ene.domain.IngresosDiarios;
import com.aiep.ene.repository.IngresosDiariosRepository;
import com.aiep.ene.service.IngresosDiariosService;
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
 * REST controller for managing {@link com.aiep.ene.domain.IngresosDiarios}.
 */
@RestController
@RequestMapping("/api/ingresos-diarios")
public class IngresosDiariosResource {

    private static final Logger LOG = LoggerFactory.getLogger(IngresosDiariosResource.class);

    private static final String ENTITY_NAME = "ingresosDiarios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IngresosDiariosService ingresosDiariosService;

    private final IngresosDiariosRepository ingresosDiariosRepository;

    public IngresosDiariosResource(IngresosDiariosService ingresosDiariosService, IngresosDiariosRepository ingresosDiariosRepository) {
        this.ingresosDiariosService = ingresosDiariosService;
        this.ingresosDiariosRepository = ingresosDiariosRepository;
    }

    /**
     * {@code POST  /ingresos-diarios} : Create a new ingresosDiarios.
     *
     * @param ingresosDiarios the ingresosDiarios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ingresosDiarios, or with status {@code 400 (Bad Request)} if the ingresosDiarios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<IngresosDiarios> createIngresosDiarios(@RequestBody IngresosDiarios ingresosDiarios) throws URISyntaxException {
        LOG.debug("REST request to save IngresosDiarios : {}", ingresosDiarios);
        if (ingresosDiarios.getId() != null) {
            throw new BadRequestAlertException("A new ingresosDiarios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ingresosDiarios = ingresosDiariosService.save(ingresosDiarios);
        return ResponseEntity.created(new URI("/api/ingresos-diarios/" + ingresosDiarios.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ingresosDiarios.getId().toString()))
            .body(ingresosDiarios);
    }

    /**
     * {@code PUT  /ingresos-diarios/:id} : Updates an existing ingresosDiarios.
     *
     * @param id the id of the ingresosDiarios to save.
     * @param ingresosDiarios the ingresosDiarios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingresosDiarios,
     * or with status {@code 400 (Bad Request)} if the ingresosDiarios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ingresosDiarios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<IngresosDiarios> updateIngresosDiarios(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IngresosDiarios ingresosDiarios
    ) throws URISyntaxException {
        LOG.debug("REST request to update IngresosDiarios : {}, {}", id, ingresosDiarios);
        if (ingresosDiarios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingresosDiarios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingresosDiariosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ingresosDiarios = ingresosDiariosService.update(ingresosDiarios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ingresosDiarios.getId().toString()))
            .body(ingresosDiarios);
    }

    /**
     * {@code PATCH  /ingresos-diarios/:id} : Partial updates given fields of an existing ingresosDiarios, field will ignore if it is null
     *
     * @param id the id of the ingresosDiarios to save.
     * @param ingresosDiarios the ingresosDiarios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ingresosDiarios,
     * or with status {@code 400 (Bad Request)} if the ingresosDiarios is not valid,
     * or with status {@code 404 (Not Found)} if the ingresosDiarios is not found,
     * or with status {@code 500 (Internal Server Error)} if the ingresosDiarios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IngresosDiarios> partialUpdateIngresosDiarios(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody IngresosDiarios ingresosDiarios
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update IngresosDiarios partially : {}, {}", id, ingresosDiarios);
        if (ingresosDiarios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ingresosDiarios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ingresosDiariosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IngresosDiarios> result = ingresosDiariosService.partialUpdate(ingresosDiarios);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ingresosDiarios.getId().toString())
        );
    }

    /**
     * {@code GET  /ingresos-diarios} : get all the ingresosDiarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ingresosDiarios in body.
     */
    @GetMapping("")
    public List<IngresosDiarios> getAllIngresosDiarios() {
        LOG.debug("REST request to get all IngresosDiarios");
        return ingresosDiariosService.findAll();
    }

    /**
     * {@code GET  /ingresos-diarios/:id} : get the "id" ingresosDiarios.
     *
     * @param id the id of the ingresosDiarios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ingresosDiarios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<IngresosDiarios> getIngresosDiarios(@PathVariable("id") Long id) {
        LOG.debug("REST request to get IngresosDiarios : {}", id);
        Optional<IngresosDiarios> ingresosDiarios = ingresosDiariosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ingresosDiarios);
    }

    /**
     * {@code DELETE  /ingresos-diarios/:id} : delete the "id" ingresosDiarios.
     *
     * @param id the id of the ingresosDiarios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngresosDiarios(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete IngresosDiarios : {}", id);
        ingresosDiariosService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
