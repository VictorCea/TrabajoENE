package com.aiep.ene.web.rest;

import static com.aiep.ene.domain.IngresosDiariosAsserts.*;
import static com.aiep.ene.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.ene.IntegrationTest;
import com.aiep.ene.domain.IngresosDiarios;
import com.aiep.ene.repository.IngresosDiariosRepository;
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
 * Integration tests for the {@link IngresosDiariosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IngresosDiariosResourceIT {

    private static final Integer DEFAULT_VENTA_NUMERO = 1;
    private static final Integer UPDATED_VENTA_NUMERO = 2;

    private static final Integer DEFAULT_VALOR_VENTA = 1;
    private static final Integer UPDATED_VALOR_VENTA = 2;

    private static final String ENTITY_API_URL = "/api/ingresos-diarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private IngresosDiariosRepository ingresosDiariosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIngresosDiariosMockMvc;

    private IngresosDiarios ingresosDiarios;

    private IngresosDiarios insertedIngresosDiarios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngresosDiarios createEntity() {
        return new IngresosDiarios().ventaNumero(DEFAULT_VENTA_NUMERO).valorVenta(DEFAULT_VALOR_VENTA);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IngresosDiarios createUpdatedEntity() {
        return new IngresosDiarios().ventaNumero(UPDATED_VENTA_NUMERO).valorVenta(UPDATED_VALOR_VENTA);
    }

    @BeforeEach
    public void initTest() {
        ingresosDiarios = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedIngresosDiarios != null) {
            ingresosDiariosRepository.delete(insertedIngresosDiarios);
            insertedIngresosDiarios = null;
        }
    }

    @Test
    @Transactional
    void createIngresosDiarios() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the IngresosDiarios
        var returnedIngresosDiarios = om.readValue(
            restIngresosDiariosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingresosDiarios)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            IngresosDiarios.class
        );

        // Validate the IngresosDiarios in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertIngresosDiariosUpdatableFieldsEquals(returnedIngresosDiarios, getPersistedIngresosDiarios(returnedIngresosDiarios));

        insertedIngresosDiarios = returnedIngresosDiarios;
    }

    @Test
    @Transactional
    void createIngresosDiariosWithExistingId() throws Exception {
        // Create the IngresosDiarios with an existing ID
        ingresosDiarios.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIngresosDiariosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingresosDiarios)))
            .andExpect(status().isBadRequest());

        // Validate the IngresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIngresosDiarios() throws Exception {
        // Initialize the database
        insertedIngresosDiarios = ingresosDiariosRepository.saveAndFlush(ingresosDiarios);

        // Get all the ingresosDiariosList
        restIngresosDiariosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ingresosDiarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].ventaNumero").value(hasItem(DEFAULT_VENTA_NUMERO)))
            .andExpect(jsonPath("$.[*].valorVenta").value(hasItem(DEFAULT_VALOR_VENTA)));
    }

    @Test
    @Transactional
    void getIngresosDiarios() throws Exception {
        // Initialize the database
        insertedIngresosDiarios = ingresosDiariosRepository.saveAndFlush(ingresosDiarios);

        // Get the ingresosDiarios
        restIngresosDiariosMockMvc
            .perform(get(ENTITY_API_URL_ID, ingresosDiarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ingresosDiarios.getId().intValue()))
            .andExpect(jsonPath("$.ventaNumero").value(DEFAULT_VENTA_NUMERO))
            .andExpect(jsonPath("$.valorVenta").value(DEFAULT_VALOR_VENTA));
    }

    @Test
    @Transactional
    void getNonExistingIngresosDiarios() throws Exception {
        // Get the ingresosDiarios
        restIngresosDiariosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIngresosDiarios() throws Exception {
        // Initialize the database
        insertedIngresosDiarios = ingresosDiariosRepository.saveAndFlush(ingresosDiarios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingresosDiarios
        IngresosDiarios updatedIngresosDiarios = ingresosDiariosRepository.findById(ingresosDiarios.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedIngresosDiarios are not directly saved in db
        em.detach(updatedIngresosDiarios);
        updatedIngresosDiarios.ventaNumero(UPDATED_VENTA_NUMERO).valorVenta(UPDATED_VALOR_VENTA);

        restIngresosDiariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIngresosDiarios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedIngresosDiarios))
            )
            .andExpect(status().isOk());

        // Validate the IngresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedIngresosDiariosToMatchAllProperties(updatedIngresosDiarios);
    }

    @Test
    @Transactional
    void putNonExistingIngresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingresosDiarios.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngresosDiariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ingresosDiarios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ingresosDiarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIngresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingresosDiarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngresosDiariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ingresosDiarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIngresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingresosDiarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngresosDiariosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ingresosDiarios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IngresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIngresosDiariosWithPatch() throws Exception {
        // Initialize the database
        insertedIngresosDiarios = ingresosDiariosRepository.saveAndFlush(ingresosDiarios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingresosDiarios using partial update
        IngresosDiarios partialUpdatedIngresosDiarios = new IngresosDiarios();
        partialUpdatedIngresosDiarios.setId(ingresosDiarios.getId());

        partialUpdatedIngresosDiarios.ventaNumero(UPDATED_VENTA_NUMERO);

        restIngresosDiariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngresosDiarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIngresosDiarios))
            )
            .andExpect(status().isOk());

        // Validate the IngresosDiarios in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIngresosDiariosUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedIngresosDiarios, ingresosDiarios),
            getPersistedIngresosDiarios(ingresosDiarios)
        );
    }

    @Test
    @Transactional
    void fullUpdateIngresosDiariosWithPatch() throws Exception {
        // Initialize the database
        insertedIngresosDiarios = ingresosDiariosRepository.saveAndFlush(ingresosDiarios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ingresosDiarios using partial update
        IngresosDiarios partialUpdatedIngresosDiarios = new IngresosDiarios();
        partialUpdatedIngresosDiarios.setId(ingresosDiarios.getId());

        partialUpdatedIngresosDiarios.ventaNumero(UPDATED_VENTA_NUMERO).valorVenta(UPDATED_VALOR_VENTA);

        restIngresosDiariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIngresosDiarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedIngresosDiarios))
            )
            .andExpect(status().isOk());

        // Validate the IngresosDiarios in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertIngresosDiariosUpdatableFieldsEquals(
            partialUpdatedIngresosDiarios,
            getPersistedIngresosDiarios(partialUpdatedIngresosDiarios)
        );
    }

    @Test
    @Transactional
    void patchNonExistingIngresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingresosDiarios.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIngresosDiariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ingresosDiarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ingresosDiarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIngresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingresosDiarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngresosDiariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ingresosDiarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the IngresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIngresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ingresosDiarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIngresosDiariosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ingresosDiarios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IngresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIngresosDiarios() throws Exception {
        // Initialize the database
        insertedIngresosDiarios = ingresosDiariosRepository.saveAndFlush(ingresosDiarios);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ingresosDiarios
        restIngresosDiariosMockMvc
            .perform(delete(ENTITY_API_URL_ID, ingresosDiarios.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ingresosDiariosRepository.count();
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

    protected IngresosDiarios getPersistedIngresosDiarios(IngresosDiarios ingresosDiarios) {
        return ingresosDiariosRepository.findById(ingresosDiarios.getId()).orElseThrow();
    }

    protected void assertPersistedIngresosDiariosToMatchAllProperties(IngresosDiarios expectedIngresosDiarios) {
        assertIngresosDiariosAllPropertiesEquals(expectedIngresosDiarios, getPersistedIngresosDiarios(expectedIngresosDiarios));
    }

    protected void assertPersistedIngresosDiariosToMatchUpdatableProperties(IngresosDiarios expectedIngresosDiarios) {
        assertIngresosDiariosAllUpdatablePropertiesEquals(expectedIngresosDiarios, getPersistedIngresosDiarios(expectedIngresosDiarios));
    }
}
