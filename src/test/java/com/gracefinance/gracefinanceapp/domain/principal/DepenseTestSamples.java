package com.gracefinance.gracefinanceapp.domain.principal;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DepenseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Depense getDepenseSample1() {
        return new Depense()
            .id(1L)
            .code("code1")
            .motif("motif1")
            .referencePiece("referencePiece1")
            .statut("statut1")
            .validerPar("validerPar1");
    }

    public static Depense getDepenseSample2() {
        return new Depense()
            .id(2L)
            .code("code2")
            .motif("motif2")
            .referencePiece("referencePiece2")
            .statut("statut2")
            .validerPar("validerPar2");
    }

    public static Depense getDepenseRandomSampleGenerator() {
        return new Depense()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .motif(UUID.randomUUID().toString())
            .referencePiece(UUID.randomUUID().toString())
            .statut(UUID.randomUUID().toString())
            .validerPar(UUID.randomUUID().toString());
    }
}
