package com.aiep.ene.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MenuTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Menu getMenuSample1() {
        return new Menu().id(1L).nombrePlato("nombrePlato1").valorPlato(1).descripcionPlato("descripcionPlato1");
    }

    public static Menu getMenuSample2() {
        return new Menu().id(2L).nombrePlato("nombrePlato2").valorPlato(2).descripcionPlato("descripcionPlato2");
    }

    public static Menu getMenuRandomSampleGenerator() {
        return new Menu()
            .id(longCount.incrementAndGet())
            .nombrePlato(UUID.randomUUID().toString())
            .valorPlato(intCount.incrementAndGet())
            .descripcionPlato(UUID.randomUUID().toString());
    }
}
