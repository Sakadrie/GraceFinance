package com.gracefinance.gracefinanceapp.domain.principal;

import com.gracefinance.gracefinanceapp.domain.principal.CompteComptable;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CompteComptableTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CompteComptable getCompteComptableSample1() {
        return new CompteComptable().id(1L).code("code1").libelle("libelle1").classe(1);
    }

    public static CompteComptable getCompteComptableSample2() {
        return new CompteComptable().id(2L).code("code2").libelle("libelle2").classe(2);
    }

    public static CompteComptable getCompteComptableRandomSampleGenerator() {
        return new CompteComptable()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .libelle(UUID.randomUUID().toString())
            .classe(intCount.incrementAndGet());
    }
}
