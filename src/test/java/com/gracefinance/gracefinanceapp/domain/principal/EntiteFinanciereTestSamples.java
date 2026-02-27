package com.gracefinance.gracefinanceapp.domain.principal;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EntiteFinanciereTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EntiteFinanciere getEntiteFinanciereSample1() {
        return new EntiteFinanciere().id(1L).nom("nom1").code("code1").type("type1").description("description1");
    }

    public static EntiteFinanciere getEntiteFinanciereSample2() {
        return new EntiteFinanciere().id(2L).nom("nom2").code("code2").type("type2").description("description2");
    }

    public static EntiteFinanciere getEntiteFinanciereRandomSampleGenerator() {
        return new EntiteFinanciere()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
