package com.aiep.ene.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ClienteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Cliente getClienteSample1() {
        return new Cliente().id(1L).mesa(1).orden("orden1").cantidadClientes(1);
    }

    public static Cliente getClienteSample2() {
        return new Cliente().id(2L).mesa(2).orden("orden2").cantidadClientes(2);
    }

    public static Cliente getClienteRandomSampleGenerator() {
        return new Cliente()
            .id(longCount.incrementAndGet())
            .mesa(intCount.incrementAndGet())
            .orden(UUID.randomUUID().toString())
            .cantidadClientes(intCount.incrementAndGet());
    }
}
