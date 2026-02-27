package com.gracefinance.gracefinanceapp.domain.principal;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EcritureComptableTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EcritureComptable getEcritureComptableSample1() {
        return new EcritureComptable().id(1L).numeroPiece("numeroPiece1").libelle("libelle1").referenceExterne("referenceExterne1");
    }

    public static EcritureComptable getEcritureComptableSample2() {
        return new EcritureComptable().id(2L).numeroPiece("numeroPiece2").libelle("libelle2").referenceExterne("referenceExterne2");
    }

    public static EcritureComptable getEcritureComptableRandomSampleGenerator() {
        return new EcritureComptable()
            .id(longCount.incrementAndGet())
            .numeroPiece(UUID.randomUUID().toString())
            .libelle(UUID.randomUUID().toString())
            .referenceExterne(UUID.randomUUID().toString());
    }
}
