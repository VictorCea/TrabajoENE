package com.aiep.ene.web.rest;

import com.aiep.ene.domain.Producto;
import com.aiep.ene.repository.ProductoRepository;
import com.aiep.ene.service.ProductoService;
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
 * REST controller for managing {@link com.aiep.ene.domain.Producto}.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoResource {

    private static final Logger LOG = LoggerFactory.getLogger(ProductoResource.class);

    private static final String ENTITY_NAME = "producto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoService productoService;

    private final ProductoRepository productoRepository;

    public ProductoResource(ProductoService productoService, ProductoRepository productoRepository) {
        this.productoService = productoService;
        this.productoRepository = productoRepository;
    }

    /**
     * {@code POST  /productos} : Create a new producto.
     *
     * @param producto the producto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new producto, or with status {@code 400 (Bad Request)} if the producto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) throws URISyntaxException {
        LOG.debug("REST request to save Producto : {}", producto);
        if (producto.getId() != null) {
            throw new BadRequestAlertException("A new producto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        producto = productoService.save(producto);
        return ResponseEntity.created(new URI("/api/productos/" + producto.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, producto.getId().toString()))
            .body(producto);
    }

    /**
     * {@code PUT  /productos/:id} : Updates an existing producto.
     *
     * @param id the id of the producto to save.
     * @param producto the producto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated producto,
     * or with status {@code 400 (Bad Request)} if the producto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the producto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Producto producto
    ) throws URISyntaxException {
        LOG.debug("REST request to update Producto : {}, {}", id, producto);
        if (producto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, producto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        producto = productoService.update(producto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, producto.getId().toString()))
            .body(producto);
    }

    /**
     * {@code PATCH  /productos/:id} : Partial updates given fields of an existing producto, field will ignore if it is null
     *
     * @param id the id of the producto to save.
     * @param producto the producto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated producto,
     * or with status {@code 400 (Bad Request)} if the producto is not valid,
     * or with status {@code 404 (Not Found)} if the producto is not found,
     * or with status {@code 500 (Internal Server Error)} if the producto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Producto> partialUpdateProducto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Producto producto
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Producto partially : {}, {}", id, producto);
        if (producto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, producto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Producto> result = productoService.partialUpdate(producto);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, producto.getId().toString())
        );
    }

    /**
     * {@code GET  /productos} : get all the productos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productos in body.
     */
    @GetMapping("")
    public List<Producto> getAllProductos() {
        LOG.debug("REST request to get all Productos");
        return productoService.findAll();
    }

    /**
     * {@code GET  /productos/:id} : get the "id" producto.
     *
     * @param id the id of the producto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the producto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Producto : {}", id);
        Optional<Producto> producto = productoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(producto);
    }

    /**
     * {@code DELETE  /productos/:id} : delete the "id" producto.
     *
     * @param id the id of the producto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Producto : {}", id);
        productoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
