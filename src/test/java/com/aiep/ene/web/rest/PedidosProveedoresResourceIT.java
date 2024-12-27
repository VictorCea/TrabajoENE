package com.aiep.ene.web.rest;

import static com.aiep.ene.domain.PedidosProveedoresAsserts.*;
import static com.aiep.ene.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.ene.IntegrationTest;
import com.aiep.ene.domain.PedidosProveedores;
import com.aiep.ene.repository.PedidosProveedoresRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PedidosProveedoresResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PedidosProveedoresResourceIT {

    private static final String DEFAULT_PRODUCTO = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Integer DEFAULT_VALOR = 1;
    private static final Integer UPDATED_VALOR = 2;

    private static final String ENTITY_API_URL = "/api/pedidos-proveedores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PedidosProveedoresRepository pedidosProveedoresRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPedidosProveedoresMockMvc;

    private PedidosProveedores pedidosProveedores;

    private PedidosProveedores insertedPedidosProveedores;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PedidosProveedores createEntity() {
        return new PedidosProveedores().producto(DEFAULT_PRODUCTO).cantidad(DEFAULT_CANTIDAD).valor(DEFAULT_VALOR);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PedidosProveedores createUpdatedEntity() {
        return new PedidosProveedores().producto(UPDATED_PRODUCTO).cantidad(UPDATED_CANTIDAD).valor(UPDATED_VALOR);
    }

    @BeforeEach
    public void initTest() {
        pedidosProveedores = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPedidosProveedores != null) {
            pedidosProveedoresRepository.delete(insertedPedidosProveedores);
            insertedPedidosProveedores = null;
        }
    }

    @Test
    @Transactional
    void createPedidosProveedores() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PedidosProveedores
        var returnedPedidosProveedores = om.readValue(
            restPedidosProveedoresMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pedidosProveedores)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PedidosProveedores.class
        );

        // Validate the PedidosProveedores in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPedidosProveedoresUpdatableFieldsEquals(
            returnedPedidosProveedores,
            getPersistedPedidosProveedores(returnedPedidosProveedores)
        );

        insertedPedidosProveedores = returnedPedidosProveedores;
    }

    @Test
    @Transactional
    void createPedidosProveedoresWithExistingId() throws Exception {
        // Create the PedidosProveedores with an existing ID
        pedidosProveedores.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPedidosProveedoresMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pedidosProveedores)))
            .andExpect(status().isBadRequest());

        // Validate the PedidosProveedores in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPedidosProveedores() throws Exception {
        // Initialize the database
        insertedPedidosProveedores = pedidosProveedoresRepository.saveAndFlush(pedidosProveedores);

        // Get all the pedidosProveedoresList
        restPedidosProveedoresMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pedidosProveedores.getId().intValue())))
            .andExpect(jsonPath("$.[*].producto").value(hasItem(DEFAULT_PRODUCTO)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR)));
    }

    @Test
    @Transactional
    void getPedidosProveedores() throws Exception {
        // Initialize the database
        insertedPedidosProveedores = pedidosProveedoresRepository.saveAndFlush(pedidosProveedores);

        // Get the pedidosProveedores
        restPedidosProveedoresMockMvc
            .perform(get(ENTITY_API_URL_ID, pedidosProveedores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pedidosProveedores.getId().intValue()))
            .andExpect(jsonPath("$.producto").value(DEFAULT_PRODUCTO))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR));
    }

    @Test
    @Transactional
    void getNonExistingPedidosProveedores() throws Exception {
        // Get the pedidosProveedores
        restPedidosProveedoresMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPedidosProveedores() throws Exception {
        // Initialize the database
        insertedPedidosProveedores = pedidosProveedoresRepository.saveAndFlush(pedidosProveedores);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pedidosProveedores
        PedidosProveedores updatedPedidosProveedores = pedidosProveedoresRepository.findById(pedidosProveedores.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPedidosProveedores are not directly saved in db
        em.detach(updatedPedidosProveedores);
        updatedPedidosProveedores.producto(UPDATED_PRODUCTO).cantidad(UPDATED_CANTIDAD).valor(UPDATED_VALOR);

        restPedidosProveedoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPedidosProveedores.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPedidosProveedores))
            )
            .andExpect(status().isOk());

        // Validate the PedidosProveedores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPedidosProveedoresToMatchAllProperties(updatedPedidosProveedores);
    }

    @Test
    @Transactional
    void putNonExistingPedidosProveedores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pedidosProveedores.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidosProveedoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pedidosProveedores.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pedidosProveedores))
            )
            .andExpect(status().isBadRequest());

        // Validate the PedidosProveedores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPedidosProveedores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pedidosProveedores.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidosProveedoresMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pedidosProveedores))
            )
            .andExpect(status().isBadRequest());

        // Validate the PedidosProveedores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPedidosProveedores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pedidosProveedores.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidosProveedoresMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pedidosProveedores)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PedidosProveedores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePedidosProveedoresWithPatch() throws Exception {
        // Initialize the database
        insertedPedidosProveedores = pedidosProveedoresRepository.saveAndFlush(pedidosProveedores);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pedidosProveedores using partial update
        PedidosProveedores partialUpdatedPedidosProveedores = new PedidosProveedores();
        partialUpdatedPedidosProveedores.setId(pedidosProveedores.getId());

        partialUpdatedPedidosProveedores.cantidad(UPDATED_CANTIDAD).valor(UPDATED_VALOR);

        restPedidosProveedoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPedidosProveedores.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPedidosProveedores))
            )
            .andExpect(status().isOk());

        // Validate the PedidosProveedores in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPedidosProveedoresUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPedidosProveedores, pedidosProveedores),
            getPersistedPedidosProveedores(pedidosProveedores)
        );
    }

    @Test
    @Transactional
    void fullUpdatePedidosProveedoresWithPatch() throws Exception {
        // Initialize the database
        insertedPedidosProveedores = pedidosProveedoresRepository.saveAndFlush(pedidosProveedores);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pedidosProveedores using partial update
        PedidosProveedores partialUpdatedPedidosProveedores = new PedidosProveedores();
        partialUpdatedPedidosProveedores.setId(pedidosProveedores.getId());

        partialUpdatedPedidosProveedores.producto(UPDATED_PRODUCTO).cantidad(UPDATED_CANTIDAD).valor(UPDATED_VALOR);

        restPedidosProveedoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPedidosProveedores.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPedidosProveedores))
            )
            .andExpect(status().isOk());

        // Validate the PedidosProveedores in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPedidosProveedoresUpdatableFieldsEquals(
            partialUpdatedPedidosProveedores,
            getPersistedPedidosProveedores(partialUpdatedPedidosProveedores)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPedidosProveedores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pedidosProveedores.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPedidosProveedoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pedidosProveedores.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pedidosProveedores))
            )
            .andExpect(status().isBadRequest());

        // Validate the PedidosProveedores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPedidosProveedores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pedidosProveedores.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidosProveedoresMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pedidosProveedores))
            )
            .andExpect(status().isBadRequest());

        // Validate the PedidosProveedores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPedidosProveedores() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pedidosProveedores.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPedidosProveedoresMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pedidosProveedores)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PedidosProveedores in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePedidosProveedores() throws Exception {
        // Initialize the database
        insertedPedidosProveedores = pedidosProveedoresRepository.saveAndFlush(pedidosProveedores);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pedidosProveedores
        restPedidosProveedoresMockMvc
            .perform(delete(ENTITY_API_URL_ID, pedidosProveedores.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pedidosProveedoresRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected PedidosProveedores getPersistedPedidosProveedores(PedidosProveedores pedidosProveedores) {
        return pedidosProveedoresRepository.findById(pedidosProveedores.getId()).orElseThrow();
    }

    protected void assertPersistedPedidosProveedoresToMatchAllProperties(PedidosProveedores expectedPedidosProveedores) {
        assertPedidosProveedoresAllPropertiesEquals(expectedPedidosProveedores, getPersistedPedidosProveedores(expectedPedidosProveedores));
    }

    protected void assertPersistedPedidosProveedoresToMatchUpdatableProperties(PedidosProveedores expectedPedidosProveedores) {
        assertPedidosProveedoresAllUpdatablePropertiesEquals(
            expectedPedidosProveedores,
            getPersistedPedidosProveedores(expectedPedidosProveedores)
        );
    }
}
