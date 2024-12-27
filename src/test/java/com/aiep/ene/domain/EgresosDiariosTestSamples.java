package com.aiep.ene.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EgresosDiariosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EgresosDiarios getEgresosDiariosSample1() {
        return new EgresosDiarios().id(1L).gastoDiario("gastoDiario1").valorGasto(1);
    }

    public static EgresosDiarios getEgresosDiariosSample2() {
        return new EgresosDiarios().id(2L).gastoDiario("gastoDiario2").valorGasto(2);
    }

    public static EgresosDiarios getEgresosDiariosRandomSampleGenerator() {
        return new EgresosDiarios()
            .id(longCount.incrementAndGet())
            .gastoDiario(UUID.randomUUID().toString())
            .valorGasto(intCount.incrementAndGet());
    }
}
