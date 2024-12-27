package com.aiep.ene.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class OrdenesCocinaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static OrdenesCocina getOrdenesCocinaSample1() {
        return new OrdenesCocina().id(1L).nombrePlato("nombrePlato1").cantidadPlato(1);
    }

    public static OrdenesCocina getOrdenesCocinaSample2() {
        return new OrdenesCocina().id(2L).nombrePlato("nombrePlato2").cantidadPlato(2);
    }

    public static OrdenesCocina getOrdenesCocinaRandomSampleGenerator() {
        return new OrdenesCocina()
            .id(longCount.incrementAndGet())
            .nombrePlato(UUID.randomUUID().toString())
            .cantidadPlato(intCount.incrementAndGet());
    }
}
