package com.aiep.ene.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PedidosProveedoresTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PedidosProveedores getPedidosProveedoresSample1() {
        return new PedidosProveedores().id(1L).producto("producto1").cantidad(1).valor(1);
    }

    public static PedidosProveedores getPedidosProveedoresSample2() {
        return new PedidosProveedores().id(2L).producto("producto2").cantidad(2).valor(2);
    }

    public static PedidosProveedores getPedidosProveedoresRandomSampleGenerator() {
        return new PedidosProveedores()
            .id(longCount.incrementAndGet())
            .producto(UUID.randomUUID().toString())
            .cantidad(intCount.incrementAndGet())
            .valor(intCount.incrementAndGet());
    }
}
