package com.aiep.ene.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProveedorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Proveedor getProveedorSample1() {
        return new Proveedor().id(1L).nombre("nombre1").direccion("direccion1").email("email1").fono("fono1");
    }

    public static Proveedor getProveedorSample2() {
        return new Proveedor().id(2L).nombre("nombre2").direccion("direccion2").email("email2").fono("fono2");
    }

    public static Proveedor getProveedorRandomSampleGenerator() {
        return new Proveedor()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .direccion(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .fono(UUID.randomUUID().toString());
    }
}
