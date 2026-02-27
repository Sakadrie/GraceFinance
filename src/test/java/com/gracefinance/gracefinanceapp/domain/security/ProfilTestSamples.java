package com.gracefinance.gracefinanceapp.domain.security;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProfilTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Profil getProfilSample1() {
        return new Profil().id(1L).nom("nom1").code("code1").description("description1");
    }

    public static Profil getProfilSample2() {
        return new Profil().id(2L).nom("nom2").code("code2").description("description2");
    }

    public static Profil getProfilRandomSampleGenerator() {
        return new Profil()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
