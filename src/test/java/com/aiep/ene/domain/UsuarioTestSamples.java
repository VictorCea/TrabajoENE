package com.aiep.ene.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UsuarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Usuario getUsuarioSample1() {
        return new Usuario().id(1L).nombre("nombre1").rol("rol1").rut("rut1");
    }

    public static Usuario getUsuarioSample2() {
        return new Usuario().id(2L).nombre("nombre2").rol("rol2").rut("rut2");
    }

    public static Usuario getUsuarioRandomSampleGenerator() {
        return new Usuario()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .rol(UUID.randomUUID().toString())
            .rut(UUID.randomUUID().toString());
    }
}
