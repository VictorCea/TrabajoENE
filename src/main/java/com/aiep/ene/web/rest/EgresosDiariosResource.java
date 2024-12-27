package com.aiep.ene.web.rest;

import com.aiep.ene.domain.EgresosDiarios;
import com.aiep.ene.repository.EgresosDiariosRepository;
import com.aiep.ene.service.EgresosDiariosService;
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
 * REST controller for managing {@link com.aiep.ene.domain.EgresosDiarios}.
 */
@RestController
@RequestMapping("/api/egresos-diarios")
public class EgresosDiariosResource {

    private static final Logger LOG = LoggerFactory.getLogger(EgresosDiariosResource.class);

    private static final String ENTITY_NAME = "egresosDiarios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EgresosDiariosService egresosDiariosService;

    private final EgresosDiariosRepository egresosDiariosRepository;

    public EgresosDiariosResource(EgresosDiariosService egresosDiariosService, EgresosDiariosRepository egresosDiariosRepository) {
        this.egresosDiariosService = egresosDiariosService;
        this.egresosDiariosRepository = egresosDiariosRepository;
    }

    /**
     * {@code POST  /egresos-diarios} : Create a new egresosDiarios.
     *
     * @param egresosDiarios the egresosDiarios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new egresosDiarios, or with status {@code 400 (Bad Request)} if the egresosDiarios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EgresosDiarios> createEgresosDiarios(@RequestBody EgresosDiarios egresosDiarios) throws URISyntaxException {
        LOG.debug("REST request to save EgresosDiarios : {}", egresosDiarios);
        if (egresosDiarios.getId() != null) {
            throw new BadRequestAlertException("A new egresosDiarios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        egresosDiarios = egresosDiariosService.save(egresosDiarios);
        return ResponseEntity.created(new URI("/api/egresos-diarios/" + egresosDiarios.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, egresosDiarios.getId().toString()))
            .body(egresosDiarios);
    }

    /**
     * {@code PUT  /egresos-diarios/:id} : Updates an existing egresosDiarios.
     *
     * @param id the id of the egresosDiarios to save.
     * @param egresosDiarios the egresosDiarios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated egresosDiarios,
     * or with status {@code 400 (Bad Request)} if the egresosDiarios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the egresosDiarios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EgresosDiarios> updateEgresosDiarios(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EgresosDiarios egresosDiarios
    ) throws URISyntaxException {
        LOG.debug("REST request to update EgresosDiarios : {}, {}", id, egresosDiarios);
        if (egresosDiarios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, egresosDiarios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!egresosDiariosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        egresosDiarios = egresosDiariosService.update(egresosDiarios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, egresosDiarios.getId().toString()))
            .body(egresosDiarios);
    }

    /**
     * {@code PATCH  /egresos-diarios/:id} : Partial updates given fields of an existing egresosDiarios, field will ignore if it is null
     *
     * @param id the id of the egresosDiarios to save.
     * @param egresosDiarios the egresosDiarios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated egresosDiarios,
     * or with status {@code 400 (Bad Request)} if the egresosDiarios is not valid,
     * or with status {@code 404 (Not Found)} if the egresosDiarios is not found,
     * or with status {@code 500 (Internal Server Error)} if the egresosDiarios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EgresosDiarios> partialUpdateEgresosDiarios(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EgresosDiarios egresosDiarios
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update EgresosDiarios partially : {}, {}", id, egresosDiarios);
        if (egresosDiarios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, egresosDiarios.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!egresosDiariosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EgresosDiarios> result = egresosDiariosService.partialUpdate(egresosDiarios);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, egresosDiarios.getId().toString())
        );
    }

    /**
     * {@code GET  /egresos-diarios} : get all the egresosDiarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of egresosDiarios in body.
     */
    @GetMapping("")
    public List<EgresosDiarios> getAllEgresosDiarios() {
        LOG.debug("REST request to get all EgresosDiarios");
        return egresosDiariosService.findAll();
    }

    /**
     * {@code GET  /egresos-diarios/:id} : get the "id" egresosDiarios.
     *
     * @param id the id of the egresosDiarios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the egresosDiarios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EgresosDiarios> getEgresosDiarios(@PathVariable("id") Long id) {
        LOG.debug("REST request to get EgresosDiarios : {}", id);
        Optional<EgresosDiarios> egresosDiarios = egresosDiariosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(egresosDiarios);
    }

    /**
     * {@code DELETE  /egresos-diarios/:id} : delete the "id" egresosDiarios.
     *
     * @param id the id of the egresosDiarios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEgresosDiarios(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete EgresosDiarios : {}", id);
        egresosDiariosService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
