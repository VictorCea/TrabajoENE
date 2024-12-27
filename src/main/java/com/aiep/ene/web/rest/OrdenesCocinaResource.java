package com.aiep.ene.web.rest;

import com.aiep.ene.domain.OrdenesCocina;
import com.aiep.ene.repository.OrdenesCocinaRepository;
import com.aiep.ene.service.OrdenesCocinaService;
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
 * REST controller for managing {@link com.aiep.ene.domain.OrdenesCocina}.
 */
@RestController
@RequestMapping("/api/ordenes-cocinas")
public class OrdenesCocinaResource {

    private static final Logger LOG = LoggerFactory.getLogger(OrdenesCocinaResource.class);

    private static final String ENTITY_NAME = "ordenesCocina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdenesCocinaService ordenesCocinaService;

    private final OrdenesCocinaRepository ordenesCocinaRepository;

    public OrdenesCocinaResource(OrdenesCocinaService ordenesCocinaService, OrdenesCocinaRepository ordenesCocinaRepository) {
        this.ordenesCocinaService = ordenesCocinaService;
        this.ordenesCocinaRepository = ordenesCocinaRepository;
    }

    /**
     * {@code POST  /ordenes-cocinas} : Create a new ordenesCocina.
     *
     * @param ordenesCocina the ordenesCocina to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ordenesCocina, or with status {@code 400 (Bad Request)} if the ordenesCocina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OrdenesCocina> createOrdenesCocina(@RequestBody OrdenesCocina ordenesCocina) throws URISyntaxException {
        LOG.debug("REST request to save OrdenesCocina : {}", ordenesCocina);
        if (ordenesCocina.getId() != null) {
            throw new BadRequestAlertException("A new ordenesCocina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ordenesCocina = ordenesCocinaService.save(ordenesCocina);
        return ResponseEntity.created(new URI("/api/ordenes-cocinas/" + ordenesCocina.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ordenesCocina.getId().toString()))
            .body(ordenesCocina);
    }

    /**
     * {@code PUT  /ordenes-cocinas/:id} : Updates an existing ordenesCocina.
     *
     * @param id the id of the ordenesCocina to save.
     * @param ordenesCocina the ordenesCocina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordenesCocina,
     * or with status {@code 400 (Bad Request)} if the ordenesCocina is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ordenesCocina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OrdenesCocina> updateOrdenesCocina(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdenesCocina ordenesCocina
    ) throws URISyntaxException {
        LOG.debug("REST request to update OrdenesCocina : {}, {}", id, ordenesCocina);
        if (ordenesCocina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordenesCocina.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordenesCocinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ordenesCocina = ordenesCocinaService.update(ordenesCocina);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordenesCocina.getId().toString()))
            .body(ordenesCocina);
    }

    /**
     * {@code PATCH  /ordenes-cocinas/:id} : Partial updates given fields of an existing ordenesCocina, field will ignore if it is null
     *
     * @param id the id of the ordenesCocina to save.
     * @param ordenesCocina the ordenesCocina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ordenesCocina,
     * or with status {@code 400 (Bad Request)} if the ordenesCocina is not valid,
     * or with status {@code 404 (Not Found)} if the ordenesCocina is not found,
     * or with status {@code 500 (Internal Server Error)} if the ordenesCocina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrdenesCocina> partialUpdateOrdenesCocina(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrdenesCocina ordenesCocina
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update OrdenesCocina partially : {}, {}", id, ordenesCocina);
        if (ordenesCocina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ordenesCocina.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ordenesCocinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrdenesCocina> result = ordenesCocinaService.partialUpdate(ordenesCocina);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ordenesCocina.getId().toString())
        );
    }

    /**
     * {@code GET  /ordenes-cocinas} : get all the ordenesCocinas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ordenesCocinas in body.
     */
    @GetMapping("")
    public List<OrdenesCocina> getAllOrdenesCocinas() {
        LOG.debug("REST request to get all OrdenesCocinas");
        return ordenesCocinaService.findAll();
    }

    /**
     * {@code GET  /ordenes-cocinas/:id} : get the "id" ordenesCocina.
     *
     * @param id the id of the ordenesCocina to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ordenesCocina, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrdenesCocina> getOrdenesCocina(@PathVariable("id") Long id) {
        LOG.debug("REST request to get OrdenesCocina : {}", id);
        Optional<OrdenesCocina> ordenesCocina = ordenesCocinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ordenesCocina);
    }

    /**
     * {@code DELETE  /ordenes-cocinas/:id} : delete the "id" ordenesCocina.
     *
     * @param id the id of the ordenesCocina to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdenesCocina(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete OrdenesCocina : {}", id);
        ordenesCocinaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
