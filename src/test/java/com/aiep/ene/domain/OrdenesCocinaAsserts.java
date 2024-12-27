package com.aiep.ene.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class OrdenesCocinaAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrdenesCocinaAllPropertiesEquals(OrdenesCocina expected, OrdenesCocina actual) {
        assertOrdenesCocinaAutoGeneratedPropertiesEquals(expected, actual);
        assertOrdenesCocinaAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrdenesCocinaAllUpdatablePropertiesEquals(OrdenesCocina expected, OrdenesCocina actual) {
        assertOrdenesCocinaUpdatableFieldsEquals(expected, actual);
        assertOrdenesCocinaUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrdenesCocinaAutoGeneratedPropertiesEquals(OrdenesCocina expected, OrdenesCocina actual) {
        assertThat(expected)
            .as("Verify OrdenesCocina auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrdenesCocinaUpdatableFieldsEquals(OrdenesCocina expected, OrdenesCocina actual) {
        assertThat(expected)
            .as("Verify OrdenesCocina relevant properties")
            .satisfies(e -> assertThat(e.getNombrePlato()).as("check nombrePlato").isEqualTo(actual.getNombrePlato()))
            .satisfies(e -> assertThat(e.getCantidadPlato()).as("check cantidadPlato").isEqualTo(actual.getCantidadPlato()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertOrdenesCocinaUpdatableRelationshipsEquals(OrdenesCocina expected, OrdenesCocina actual) {
        // empty method
    }
}
