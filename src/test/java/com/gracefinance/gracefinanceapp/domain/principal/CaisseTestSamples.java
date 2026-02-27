package com.gracefinance.gracefinanceapp.domain.principal;

import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CaisseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Caisse getCaisseSample1() {
        return new Caisse().id(1L).nom("nom1").code("code1").type("type1").devise("devise1");
    }

    public static Caisse getCaisseSample2() {
        return new Caisse().id(2L).nom("nom2").code("code2").type("type2").devise("devise2");
    }

    public static Caisse getCaisseRandomSampleGenerator() {
        return new Caisse()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .type(UUID.randomUUID().toString())
            .devise(UUID.randomUUID().toString());
    }
}
