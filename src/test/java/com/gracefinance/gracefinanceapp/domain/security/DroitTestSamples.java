package com.gracefinance.gracefinanceapp.domain.security;

import com.gracefinance.gracefinanceapp.domain.security.Droit;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DroitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Droit getDroitSample1() {
        return new Droit().id(1L).nom("nom1").code("code1").description("description1");
    }

    public static Droit getDroitSample2() {
        return new Droit().id(2L).nom("nom2").code("code2").description("description2");
    }

    public static Droit getDroitRandomSampleGenerator() {
        return new Droit()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
