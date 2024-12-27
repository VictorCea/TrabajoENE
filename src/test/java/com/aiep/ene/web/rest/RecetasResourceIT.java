package com.aiep.ene.web.rest;

import static com.aiep.ene.domain.RecetasAsserts.*;
import static com.aiep.ene.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.ene.IntegrationTest;
import com.aiep.ene.domain.Recetas;
import com.aiep.ene.repository.RecetasRepository;
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
 * Integration tests for the {@link RecetasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RecetasResourceIT {

    private static final String DEFAULT_NOMBRE_PLATO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PLATO = "BBBBBBBBBB";

    private static final String DEFAULT_INGREDIENTES_PLATO = "AAAAAAAAAA";
    private static final String UPDATED_INGREDIENTES_PLATO = "BBBBBBBBBB";

    private static final String DEFAULT_TIEMPO_PREPARACION = "AAAAAAAAAA";
    private static final String UPDATED_TIEMPO_PREPARACION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/recetas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RecetasRepository recetasRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecetasMockMvc;

    private Recetas recetas;

    private Recetas insertedRecetas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recetas createEntity() {
        return new Recetas()
            .nombrePlato(DEFAULT_NOMBRE_PLATO)
            .ingredientesPlato(DEFAULT_INGREDIENTES_PLATO)
            .tiempoPreparacion(DEFAULT_TIEMPO_PREPARACION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recetas createUpdatedEntity() {
        return new Recetas()
            .nombrePlato(UPDATED_NOMBRE_PLATO)
            .ingredientesPlato(UPDATED_INGREDIENTES_PLATO)
            .tiempoPreparacion(UPDATED_TIEMPO_PREPARACION);
    }

    @BeforeEach
    public void initTest() {
        recetas = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRecetas != null) {
            recetasRepository.delete(insertedRecetas);
            insertedRecetas = null;
        }
    }

    @Test
    @Transactional
    void createRecetas() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Recetas
        var returnedRecetas = om.readValue(
            restRecetasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recetas)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Recetas.class
        );

        // Validate the Recetas in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertRecetasUpdatableFieldsEquals(returnedRecetas, getPersistedRecetas(returnedRecetas));

        insertedRecetas = returnedRecetas;
    }

    @Test
    @Transactional
    void createRecetasWithExistingId() throws Exception {
        // Create the Recetas with an existing ID
        recetas.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecetasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recetas)))
            .andExpect(status().isBadRequest());

        // Validate the Recetas in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRecetas() throws Exception {
        // Initialize the database
        insertedRecetas = recetasRepository.saveAndFlush(recetas);

        // Get all the recetasList
        restRecetasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recetas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrePlato").value(hasItem(DEFAULT_NOMBRE_PLATO)))
            .andExpect(jsonPath("$.[*].ingredientesPlato").value(hasItem(DEFAULT_INGREDIENTES_PLATO)))
            .andExpect(jsonPath("$.[*].tiempoPreparacion").value(hasItem(DEFAULT_TIEMPO_PREPARACION)));
    }

    @Test
    @Transactional
    void getRecetas() throws Exception {
        // Initialize the database
        insertedRecetas = recetasRepository.saveAndFlush(recetas);

        // Get the recetas
        restRecetasMockMvc
            .perform(get(ENTITY_API_URL_ID, recetas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recetas.getId().intValue()))
            .andExpect(jsonPath("$.nombrePlato").value(DEFAULT_NOMBRE_PLATO))
            .andExpect(jsonPath("$.ingredientesPlato").value(DEFAULT_INGREDIENTES_PLATO))
            .andExpect(jsonPath("$.tiempoPreparacion").value(DEFAULT_TIEMPO_PREPARACION));
    }

    @Test
    @Transactional
    void getNonExistingRecetas() throws Exception {
        // Get the recetas
        restRecetasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRecetas() throws Exception {
        // Initialize the database
        insertedRecetas = recetasRepository.saveAndFlush(recetas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recetas
        Recetas updatedRecetas = recetasRepository.findById(recetas.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRecetas are not directly saved in db
        em.detach(updatedRecetas);
        updatedRecetas
            .nombrePlato(UPDATED_NOMBRE_PLATO)
            .ingredientesPlato(UPDATED_INGREDIENTES_PLATO)
            .tiempoPreparacion(UPDATED_TIEMPO_PREPARACION);

        restRecetasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRecetas.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedRecetas))
            )
            .andExpect(status().isOk());

        // Validate the Recetas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRecetasToMatchAllProperties(updatedRecetas);
    }

    @Test
    @Transactional
    void putNonExistingRecetas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recetas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecetasMockMvc
            .perform(put(ENTITY_API_URL_ID, recetas.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recetas)))
            .andExpect(status().isBadRequest());

        // Validate the Recetas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRecetas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recetas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecetasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(recetas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recetas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRecetas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recetas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecetasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(recetas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recetas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRecetasWithPatch() throws Exception {
        // Initialize the database
        insertedRecetas = recetasRepository.saveAndFlush(recetas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recetas using partial update
        Recetas partialUpdatedRecetas = new Recetas();
        partialUpdatedRecetas.setId(recetas.getId());

        partialUpdatedRecetas.nombrePlato(UPDATED_NOMBRE_PLATO).ingredientesPlato(UPDATED_INGREDIENTES_PLATO);

        restRecetasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecetas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecetas))
            )
            .andExpect(status().isOk());

        // Validate the Recetas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecetasUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRecetas, recetas), getPersistedRecetas(recetas));
    }

    @Test
    @Transactional
    void fullUpdateRecetasWithPatch() throws Exception {
        // Initialize the database
        insertedRecetas = recetasRepository.saveAndFlush(recetas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the recetas using partial update
        Recetas partialUpdatedRecetas = new Recetas();
        partialUpdatedRecetas.setId(recetas.getId());

        partialUpdatedRecetas
            .nombrePlato(UPDATED_NOMBRE_PLATO)
            .ingredientesPlato(UPDATED_INGREDIENTES_PLATO)
            .tiempoPreparacion(UPDATED_TIEMPO_PREPARACION);

        restRecetasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRecetas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRecetas))
            )
            .andExpect(status().isOk());

        // Validate the Recetas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRecetasUpdatableFieldsEquals(partialUpdatedRecetas, getPersistedRecetas(partialUpdatedRecetas));
    }

    @Test
    @Transactional
    void patchNonExistingRecetas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recetas.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecetasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, recetas.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(recetas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recetas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRecetas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recetas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecetasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(recetas))
            )
            .andExpect(status().isBadRequest());

        // Validate the Recetas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRecetas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        recetas.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRecetasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(recetas)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Recetas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRecetas() throws Exception {
        // Initialize the database
        insertedRecetas = recetasRepository.saveAndFlush(recetas);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the recetas
        restRecetasMockMvc
            .perform(delete(ENTITY_API_URL_ID, recetas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return recetasRepository.count();
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

    protected Recetas getPersistedRecetas(Recetas recetas) {
        return recetasRepository.findById(recetas.getId()).orElseThrow();
    }

    protected void assertPersistedRecetasToMatchAllProperties(Recetas expectedRecetas) {
        assertRecetasAllPropertiesEquals(expectedRecetas, getPersistedRecetas(expectedRecetas));
    }

    protected void assertPersistedRecetasToMatchUpdatableProperties(Recetas expectedRecetas) {
        assertRecetasAllUpdatablePropertiesEquals(expectedRecetas, getPersistedRecetas(expectedRecetas));
    }
}
