package com.aiep.ene.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class IngresosDiariosTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static IngresosDiarios getIngresosDiariosSample1() {
        return new IngresosDiarios().id(1L).ventaNumero(1).valorVenta(1);
    }

    public static IngresosDiarios getIngresosDiariosSample2() {
        return new IngresosDiarios().id(2L).ventaNumero(2).valorVenta(2);
    }

    public static IngresosDiarios getIngresosDiariosRandomSampleGenerator() {
        return new IngresosDiarios()
            .id(longCount.incrementAndGet())
            .ventaNumero(intCount.incrementAndGet())
            .valorVenta(intCount.incrementAndGet());
    }
}
