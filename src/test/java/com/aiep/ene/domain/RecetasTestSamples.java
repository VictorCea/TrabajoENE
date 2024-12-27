package com.aiep.ene.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RecetasTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Recetas getRecetasSample1() {
        return new Recetas()
            .id(1L)
            .nombrePlato("nombrePlato1")
            .ingredientesPlato("ingredientesPlato1")
            .tiempoPreparacion("tiempoPreparacion1");
    }

    public static Recetas getRecetasSample2() {
        return new Recetas()
            .id(2L)
            .nombrePlato("nombrePlato2")
            .ingredientesPlato("ingredientesPlato2")
            .tiempoPreparacion("tiempoPreparacion2");
    }

    public static Recetas getRecetasRandomSampleGenerator() {
        return new Recetas()
            .id(longCount.incrementAndGet())
            .nombrePlato(UUID.randomUUID().toString())
            .ingredientesPlato(UUID.randomUUID().toString())
            .tiempoPreparacion(UUID.randomUUID().toString());
    }
}
