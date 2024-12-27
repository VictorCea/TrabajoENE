package com.aiep.ene.web.rest;

import static com.aiep.ene.domain.ProveedorAsserts.*;
import static com.aiep.ene.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiep.ene.IntegrationTest;
import com.aiep.ene.domain.Proveedor;
import com.aiep.ene.repository.ProveedorRepository;
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
 * Integration tests for the {@link ProveedorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProveedorResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FONO = "AAAAAAAAAA";
    private static final String UPDATED_FONO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/proveedors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProveedorMockMvc;

    private Proveedor proveedor;

    private Proveedor insertedProveedor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proveedor createEntity() {
        return new Proveedor().nombre(DEFAULT_NOMBRE).direccion(DEFAULT_DIRECCION).email(DEFAULT_EMAIL).fono(DEFAULT_FONO);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proveedor createUpdatedEntity() {
        return new Proveedor().nombre(UPDATED_NOMBRE).direccion(UPDATED_DIRECCION).email(UPDATED_EMAIL).fono(UPDATED_FONO);
    }

    @BeforeEach
    public void initTest() {
        proveedor = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedProveedor != null) {
            proveedorRepository.delete(insertedProveedor);
            insertedProveedor = null;
        }
    }

    @Test
    @Transactional
    void createProveedor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Proveedor
        var returnedProveedor = om.readValue(
            restProveedorMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proveedor)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Proveedor.class
        );

        // Validate the Proveedor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProveedorUpdatableFieldsEquals(returnedProveedor, getPersistedProveedor(returnedProveedor));

        insertedProveedor = returnedProveedor;
    }

    @Test
    @Transactional
    void createProveedorWithExistingId() throws Exception {
        // Create the Proveedor with an existing ID
        proveedor.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProveedorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proveedor)))
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProveedors() throws Exception {
        // Initialize the database
        insertedProveedor = proveedorRepository.saveAndFlush(proveedor);

        // Get all the proveedorList
        restProveedorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proveedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].fono").value(hasItem(DEFAULT_FONO)));
    }

    @Test
    @Transactional
    void getProveedor() throws Exception {
        // Initialize the database
        insertedProveedor = proveedorRepository.saveAndFlush(proveedor);

        // Get the proveedor
        restProveedorMockMvc
            .perform(get(ENTITY_API_URL_ID, proveedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proveedor.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.fono").value(DEFAULT_FONO));
    }

    @Test
    @Transactional
    void getNonExistingProveedor() throws Exception {
        // Get the proveedor
        restProveedorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProveedor() throws Exception {
        // Initialize the database
        insertedProveedor = proveedorRepository.saveAndFlush(proveedor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the proveedor
        Proveedor updatedProveedor = proveedorRepository.findById(proveedor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProveedor are not directly saved in db
        em.detach(updatedProveedor);
        updatedProveedor.nombre(UPDATED_NOMBRE).direccion(UPDATED_DIRECCION).email(UPDATED_EMAIL).fono(UPDATED_FONO);

        restProveedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProveedor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProveedor))
            )
            .andExpect(status().isOk());

        // Validate the Proveedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProveedorToMatchAllProperties(updatedProveedor);
    }

    @Test
    @Transactional
    void putNonExistingProveedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proveedor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, proveedor.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proveedor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProveedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proveedor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(proveedor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProveedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proveedor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(proveedor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proveedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProveedorWithPatch() throws Exception {
        // Initialize the database
        insertedProveedor = proveedorRepository.saveAndFlush(proveedor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the proveedor using partial update
        Proveedor partialUpdatedProveedor = new Proveedor();
        partialUpdatedProveedor.setId(proveedor.getId());

        partialUpdatedProveedor.fono(UPDATED_FONO);

        restProveedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProveedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProveedor))
            )
            .andExpect(status().isOk());

        // Validate the Proveedor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProveedorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProveedor, proveedor),
            getPersistedProveedor(proveedor)
        );
    }

    @Test
    @Transactional
    void fullUpdateProveedorWithPatch() throws Exception {
        // Initialize the database
        insertedProveedor = proveedorRepository.saveAndFlush(proveedor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the proveedor using partial update
        Proveedor partialUpdatedProveedor = new Proveedor();
        partialUpdatedProveedor.setId(proveedor.getId());

        partialUpdatedProveedor.nombre(UPDATED_NOMBRE).direccion(UPDATED_DIRECCION).email(UPDATED_EMAIL).fono(UPDATED_FONO);

        restProveedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProveedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProveedor))
            )
            .andExpect(status().isOk());

        // Validate the Proveedor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProveedorUpdatableFieldsEquals(partialUpdatedProveedor, getPersistedProveedor(partialUpdatedProveedor));
    }

    @Test
    @Transactional
    void patchNonExistingProveedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proveedor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, proveedor.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(proveedor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProveedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proveedor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(proveedor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Proveedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProveedor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        proveedor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProveedorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(proveedor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Proveedor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProveedor() throws Exception {
        // Initialize the database
        insertedProveedor = proveedorRepository.saveAndFlush(proveedor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the proveedor
        restProveedorMockMvc
            .perform(delete(ENTITY_API_URL_ID, proveedor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return proveedorRepository.count();
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

    protected Proveedor getPersistedProveedor(Proveedor proveedor) {
        return proveedorRepository.findById(proveedor.getId()).orElseThrow();
    }

    protected void assertPersistedProveedorToMatchAllProperties(Proveedor expectedProveedor) {
        assertProveedorAllPropertiesEquals(expectedProveedor, getPersistedProveedor(expectedProveedor));
    }

    protected void assertPersistedProveedorToMatchUpdatableProperties(Proveedor expectedProveedor) {
        assertProveedorAllUpdatablePropertiesEquals(expectedProveedor, getPersistedProveedor(expectedProveedor));
    }
}
