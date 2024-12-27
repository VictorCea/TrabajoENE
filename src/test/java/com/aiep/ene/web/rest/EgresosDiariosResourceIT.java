package com.aiep.ene.web.rest;

import static com.aiep.ene.domain.EgresosDiariosAsserts.*;
import static com.aiep.ene.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.ene.IntegrationTest;
import com.aiep.ene.domain.EgresosDiarios;
import com.aiep.ene.repository.EgresosDiariosRepository;
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
 * Integration tests for the {@link EgresosDiariosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EgresosDiariosResourceIT {

    private static final String DEFAULT_GASTO_DIARIO = "AAAAAAAAAA";
    private static final String UPDATED_GASTO_DIARIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALOR_GASTO = 1;
    private static final Integer UPDATED_VALOR_GASTO = 2;

    private static final String ENTITY_API_URL = "/api/egresos-diarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EgresosDiariosRepository egresosDiariosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEgresosDiariosMockMvc;

    private EgresosDiarios egresosDiarios;

    private EgresosDiarios insertedEgresosDiarios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EgresosDiarios createEntity() {
        return new EgresosDiarios().gastoDiario(DEFAULT_GASTO_DIARIO).valorGasto(DEFAULT_VALOR_GASTO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EgresosDiarios createUpdatedEntity() {
        return new EgresosDiarios().gastoDiario(UPDATED_GASTO_DIARIO).valorGasto(UPDATED_VALOR_GASTO);
    }

    @BeforeEach
    public void initTest() {
        egresosDiarios = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedEgresosDiarios != null) {
            egresosDiariosRepository.delete(insertedEgresosDiarios);
            insertedEgresosDiarios = null;
        }
    }

    @Test
    @Transactional
    void createEgresosDiarios() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EgresosDiarios
        var returnedEgresosDiarios = om.readValue(
            restEgresosDiariosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(egresosDiarios)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EgresosDiarios.class
        );

        // Validate the EgresosDiarios in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertEgresosDiariosUpdatableFieldsEquals(returnedEgresosDiarios, getPersistedEgresosDiarios(returnedEgresosDiarios));

        insertedEgresosDiarios = returnedEgresosDiarios;
    }

    @Test
    @Transactional
    void createEgresosDiariosWithExistingId() throws Exception {
        // Create the EgresosDiarios with an existing ID
        egresosDiarios.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEgresosDiariosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(egresosDiarios)))
            .andExpect(status().isBadRequest());

        // Validate the EgresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEgresosDiarios() throws Exception {
        // Initialize the database
        insertedEgresosDiarios = egresosDiariosRepository.saveAndFlush(egresosDiarios);

        // Get all the egresosDiariosList
        restEgresosDiariosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(egresosDiarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].gastoDiario").value(hasItem(DEFAULT_GASTO_DIARIO)))
            .andExpect(jsonPath("$.[*].valorGasto").value(hasItem(DEFAULT_VALOR_GASTO)));
    }

    @Test
    @Transactional
    void getEgresosDiarios() throws Exception {
        // Initialize the database
        insertedEgresosDiarios = egresosDiariosRepository.saveAndFlush(egresosDiarios);

        // Get the egresosDiarios
        restEgresosDiariosMockMvc
            .perform(get(ENTITY_API_URL_ID, egresosDiarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(egresosDiarios.getId().intValue()))
            .andExpect(jsonPath("$.gastoDiario").value(DEFAULT_GASTO_DIARIO))
            .andExpect(jsonPath("$.valorGasto").value(DEFAULT_VALOR_GASTO));
    }

    @Test
    @Transactional
    void getNonExistingEgresosDiarios() throws Exception {
        // Get the egresosDiarios
        restEgresosDiariosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEgresosDiarios() throws Exception {
        // Initialize the database
        insertedEgresosDiarios = egresosDiariosRepository.saveAndFlush(egresosDiarios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the egresosDiarios
        EgresosDiarios updatedEgresosDiarios = egresosDiariosRepository.findById(egresosDiarios.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEgresosDiarios are not directly saved in db
        em.detach(updatedEgresosDiarios);
        updatedEgresosDiarios.gastoDiario(UPDATED_GASTO_DIARIO).valorGasto(UPDATED_VALOR_GASTO);

        restEgresosDiariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEgresosDiarios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedEgresosDiarios))
            )
            .andExpect(status().isOk());

        // Validate the EgresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEgresosDiariosToMatchAllProperties(updatedEgresosDiarios);
    }

    @Test
    @Transactional
    void putNonExistingEgresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        egresosDiarios.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEgresosDiariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, egresosDiarios.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(egresosDiarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the EgresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEgresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        egresosDiarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEgresosDiariosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(egresosDiarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the EgresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEgresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        egresosDiarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEgresosDiariosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(egresosDiarios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EgresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEgresosDiariosWithPatch() throws Exception {
        // Initialize the database
        insertedEgresosDiarios = egresosDiariosRepository.saveAndFlush(egresosDiarios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the egresosDiarios using partial update
        EgresosDiarios partialUpdatedEgresosDiarios = new EgresosDiarios();
        partialUpdatedEgresosDiarios.setId(egresosDiarios.getId());

        partialUpdatedEgresosDiarios.gastoDiario(UPDATED_GASTO_DIARIO).valorGasto(UPDATED_VALOR_GASTO);

        restEgresosDiariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEgresosDiarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEgresosDiarios))
            )
            .andExpect(status().isOk());

        // Validate the EgresosDiarios in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEgresosDiariosUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEgresosDiarios, egresosDiarios),
            getPersistedEgresosDiarios(egresosDiarios)
        );
    }

    @Test
    @Transactional
    void fullUpdateEgresosDiariosWithPatch() throws Exception {
        // Initialize the database
        insertedEgresosDiarios = egresosDiariosRepository.saveAndFlush(egresosDiarios);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the egresosDiarios using partial update
        EgresosDiarios partialUpdatedEgresosDiarios = new EgresosDiarios();
        partialUpdatedEgresosDiarios.setId(egresosDiarios.getId());

        partialUpdatedEgresosDiarios.gastoDiario(UPDATED_GASTO_DIARIO).valorGasto(UPDATED_VALOR_GASTO);

        restEgresosDiariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEgresosDiarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEgresosDiarios))
            )
            .andExpect(status().isOk());

        // Validate the EgresosDiarios in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEgresosDiariosUpdatableFieldsEquals(partialUpdatedEgresosDiarios, getPersistedEgresosDiarios(partialUpdatedEgresosDiarios));
    }

    @Test
    @Transactional
    void patchNonExistingEgresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        egresosDiarios.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEgresosDiariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, egresosDiarios.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(egresosDiarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the EgresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEgresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        egresosDiarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEgresosDiariosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(egresosDiarios))
            )
            .andExpect(status().isBadRequest());

        // Validate the EgresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEgresosDiarios() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        egresosDiarios.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEgresosDiariosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(egresosDiarios)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EgresosDiarios in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEgresosDiarios() throws Exception {
        // Initialize the database
        insertedEgresosDiarios = egresosDiariosRepository.saveAndFlush(egresosDiarios);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the egresosDiarios
        restEgresosDiariosMockMvc
            .perform(delete(ENTITY_API_URL_ID, egresosDiarios.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return egresosDiariosRepository.count();
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

    protected EgresosDiarios getPersistedEgresosDiarios(EgresosDiarios egresosDiarios) {
        return egresosDiariosRepository.findById(egresosDiarios.getId()).orElseThrow();
    }

    protected void assertPersistedEgresosDiariosToMatchAllProperties(EgresosDiarios expectedEgresosDiarios) {
        assertEgresosDiariosAllPropertiesEquals(expectedEgresosDiarios, getPersistedEgresosDiarios(expectedEgresosDiarios));
    }

    protected void assertPersistedEgresosDiariosToMatchUpdatableProperties(EgresosDiarios expectedEgresosDiarios) {
        assertEgresosDiariosAllUpdatablePropertiesEquals(expectedEgresosDiarios, getPersistedEgresosDiarios(expectedEgresosDiarios));
    }
}
