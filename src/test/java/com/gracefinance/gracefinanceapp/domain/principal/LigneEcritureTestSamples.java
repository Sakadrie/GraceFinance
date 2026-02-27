package com.gracefinance.gracefinanceapp.domain.principal;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LigneEcritureTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LigneEcriture getLigneEcritureSample1() {
        return new LigneEcriture().id(1L).sens("sens1").libelle("libelle1");
    }

    public static LigneEcriture getLigneEcritureSample2() {
        return new LigneEcriture().id(2L).sens("sens2").libelle("libelle2");
    }

    public static LigneEcriture getLigneEcritureRandomSampleGenerator() {
        return new LigneEcriture().id(longCount.incrementAndGet()).sens(UUID.randomUUID().toString()).libelle(UUID.randomUUID().toString());
    }
}
