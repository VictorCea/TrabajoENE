package com.aiep.ene.web.rest;

import static com.aiep.ene.domain.OrdenesCocinaAsserts.*;
import static com.aiep.ene.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.ene.IntegrationTest;
import com.aiep.ene.domain.OrdenesCocina;
import com.aiep.ene.repository.OrdenesCocinaRepository;
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
 * Integration tests for the {@link OrdenesCocinaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrdenesCocinaResourceIT {

    private static final String DEFAULT_NOMBRE_PLATO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PLATO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD_PLATO = 1;
    private static final Integer UPDATED_CANTIDAD_PLATO = 2;

    private static final String ENTITY_API_URL = "/api/ordenes-cocinas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrdenesCocinaRepository ordenesCocinaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdenesCocinaMockMvc;

    private OrdenesCocina ordenesCocina;

    private OrdenesCocina insertedOrdenesCocina;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdenesCocina createEntity() {
        return new OrdenesCocina().nombrePlato(DEFAULT_NOMBRE_PLATO).cantidadPlato(DEFAULT_CANTIDAD_PLATO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrdenesCocina createUpdatedEntity() {
        return new OrdenesCocina().nombrePlato(UPDATED_NOMBRE_PLATO).cantidadPlato(UPDATED_CANTIDAD_PLATO);
    }

    @BeforeEach
    public void initTest() {
        ordenesCocina = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedOrdenesCocina != null) {
            ordenesCocinaRepository.delete(insertedOrdenesCocina);
            insertedOrdenesCocina = null;
        }
    }

    @Test
    @Transactional
    void createOrdenesCocina() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrdenesCocina
        var returnedOrdenesCocina = om.readValue(
            restOrdenesCocinaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordenesCocina)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrdenesCocina.class
        );

        // Validate the OrdenesCocina in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertOrdenesCocinaUpdatableFieldsEquals(returnedOrdenesCocina, getPersistedOrdenesCocina(returnedOrdenesCocina));

        insertedOrdenesCocina = returnedOrdenesCocina;
    }

    @Test
    @Transactional
    void createOrdenesCocinaWithExistingId() throws Exception {
        // Create the OrdenesCocina with an existing ID
        ordenesCocina.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdenesCocinaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordenesCocina)))
            .andExpect(status().isBadRequest());

        // Validate the OrdenesCocina in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrdenesCocinas() throws Exception {
        // Initialize the database
        insertedOrdenesCocina = ordenesCocinaRepository.saveAndFlush(ordenesCocina);

        // Get all the ordenesCocinaList
        restOrdenesCocinaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordenesCocina.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrePlato").value(hasItem(DEFAULT_NOMBRE_PLATO)))
            .andExpect(jsonPath("$.[*].cantidadPlato").value(hasItem(DEFAULT_CANTIDAD_PLATO)));
    }

    @Test
    @Transactional
    void getOrdenesCocina() throws Exception {
        // Initialize the database
        insertedOrdenesCocina = ordenesCocinaRepository.saveAndFlush(ordenesCocina);

        // Get the ordenesCocina
        restOrdenesCocinaMockMvc
            .perform(get(ENTITY_API_URL_ID, ordenesCocina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ordenesCocina.getId().intValue()))
            .andExpect(jsonPath("$.nombrePlato").value(DEFAULT_NOMBRE_PLATO))
            .andExpect(jsonPath("$.cantidadPlato").value(DEFAULT_CANTIDAD_PLATO));
    }

    @Test
    @Transactional
    void getNonExistingOrdenesCocina() throws Exception {
        // Get the ordenesCocina
        restOrdenesCocinaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrdenesCocina() throws Exception {
        // Initialize the database
        insertedOrdenesCocina = ordenesCocinaRepository.saveAndFlush(ordenesCocina);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordenesCocina
        OrdenesCocina updatedOrdenesCocina = ordenesCocinaRepository.findById(ordenesCocina.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOrdenesCocina are not directly saved in db
        em.detach(updatedOrdenesCocina);
        updatedOrdenesCocina.nombrePlato(UPDATED_NOMBRE_PLATO).cantidadPlato(UPDATED_CANTIDAD_PLATO);

        restOrdenesCocinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedOrdenesCocina.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedOrdenesCocina))
            )
            .andExpect(status().isOk());

        // Validate the OrdenesCocina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrdenesCocinaToMatchAllProperties(updatedOrdenesCocina);
    }

    @Test
    @Transactional
    void putNonExistingOrdenesCocina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordenesCocina.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdenesCocinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ordenesCocina.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ordenesCocina))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdenesCocina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrdenesCocina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordenesCocina.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdenesCocinaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(ordenesCocina))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdenesCocina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrdenesCocina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordenesCocina.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdenesCocinaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(ordenesCocina)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdenesCocina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrdenesCocinaWithPatch() throws Exception {
        // Initialize the database
        insertedOrdenesCocina = ordenesCocinaRepository.saveAndFlush(ordenesCocina);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordenesCocina using partial update
        OrdenesCocina partialUpdatedOrdenesCocina = new OrdenesCocina();
        partialUpdatedOrdenesCocina.setId(ordenesCocina.getId());

        partialUpdatedOrdenesCocina.cantidadPlato(UPDATED_CANTIDAD_PLATO);

        restOrdenesCocinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdenesCocina.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrdenesCocina))
            )
            .andExpect(status().isOk());

        // Validate the OrdenesCocina in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrdenesCocinaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrdenesCocina, ordenesCocina),
            getPersistedOrdenesCocina(ordenesCocina)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrdenesCocinaWithPatch() throws Exception {
        // Initialize the database
        insertedOrdenesCocina = ordenesCocinaRepository.saveAndFlush(ordenesCocina);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the ordenesCocina using partial update
        OrdenesCocina partialUpdatedOrdenesCocina = new OrdenesCocina();
        partialUpdatedOrdenesCocina.setId(ordenesCocina.getId());

        partialUpdatedOrdenesCocina.nombrePlato(UPDATED_NOMBRE_PLATO).cantidadPlato(UPDATED_CANTIDAD_PLATO);

        restOrdenesCocinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrdenesCocina.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrdenesCocina))
            )
            .andExpect(status().isOk());

        // Validate the OrdenesCocina in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrdenesCocinaUpdatableFieldsEquals(partialUpdatedOrdenesCocina, getPersistedOrdenesCocina(partialUpdatedOrdenesCocina));
    }

    @Test
    @Transactional
    void patchNonExistingOrdenesCocina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordenesCocina.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdenesCocinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ordenesCocina.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ordenesCocina))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdenesCocina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrdenesCocina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordenesCocina.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdenesCocinaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(ordenesCocina))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrdenesCocina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrdenesCocina() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        ordenesCocina.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrdenesCocinaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(ordenesCocina)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrdenesCocina in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrdenesCocina() throws Exception {
        // Initialize the database
        insertedOrdenesCocina = ordenesCocinaRepository.saveAndFlush(ordenesCocina);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the ordenesCocina
        restOrdenesCocinaMockMvc
            .perform(delete(ENTITY_API_URL_ID, ordenesCocina.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return ordenesCocinaRepository.count();
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

    protected OrdenesCocina getPersistedOrdenesCocina(OrdenesCocina ordenesCocina) {
        return ordenesCocinaRepository.findById(ordenesCocina.getId()).orElseThrow();
    }

    protected void assertPersistedOrdenesCocinaToMatchAllProperties(OrdenesCocina expectedOrdenesCocina) {
        assertOrdenesCocinaAllPropertiesEquals(expectedOrdenesCocina, getPersistedOrdenesCocina(expectedOrdenesCocina));
    }

    protected void assertPersistedOrdenesCocinaToMatchUpdatableProperties(OrdenesCocina expectedOrdenesCocina) {
        assertOrdenesCocinaAllUpdatablePropertiesEquals(expectedOrdenesCocina, getPersistedOrdenesCocina(expectedOrdenesCocina));
    }
}
