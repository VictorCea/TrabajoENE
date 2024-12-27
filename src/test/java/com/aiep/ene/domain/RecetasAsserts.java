package com.aiep.ene.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RecetasAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRecetasAllPropertiesEquals(Recetas expected, Recetas actual) {
        assertRecetasAutoGeneratedPropertiesEquals(expected, actual);
        assertRecetasAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRecetasAllUpdatablePropertiesEquals(Recetas expected, Recetas actual) {
        assertRecetasUpdatableFieldsEquals(expected, actual);
        assertRecetasUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRecetasAutoGeneratedPropertiesEquals(Recetas expected, Recetas actual) {
        assertThat(expected)
            .as("Verify Recetas auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRecetasUpdatableFieldsEquals(Recetas expected, Recetas actual) {
        assertThat(expected)
            .as("Verify Recetas relevant properties")
            .satisfies(e -> assertThat(e.getNombrePlato()).as("check nombrePlato").isEqualTo(actual.getNombrePlato()))
            .satisfies(e -> assertThat(e.getIngredientesPlato()).as("check ingredientesPlato").isEqualTo(actual.getIngredientesPlato()))
            .satisfies(e -> assertThat(e.getTiempoPreparacion()).as("check tiempoPreparacion").isEqualTo(actual.getTiempoPreparacion()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRecetasUpdatableRelationshipsEquals(Recetas expected, Recetas actual) {
        // empty method
    }
}