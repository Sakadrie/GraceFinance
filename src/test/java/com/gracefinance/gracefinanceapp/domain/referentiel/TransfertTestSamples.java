package com.gracefinance.gracefinanceapp.domain.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.Transfert;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TransfertTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Transfert getTransfertSample1() {
        return new Transfert()
            .id(1L)
            .code("code1")
            .motif("motif1")
            .typeTransfert("typeTransfert1")
            .statut("statut1")
            .validerPar("validerPar1");
    }

    public static Transfert getTransfertSample2() {
        return new Transfert()
            .id(2L)
            .code("code2")
            .motif("motif2")
            .typeTransfert("typeTransfert2")
            .statut("statut2")
            .validerPar("validerPar2");
    }

    public static Transfert getTransfertRandomSampleGenerator() {
        return new Transfert()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .motif(UUID.randomUUID().toString())
            .typeTransfert(UUID.randomUUID().toString())
            .statut(UUID.randomUUID().toString())
            .validerPar(UUID.randomUUID().toString());
    }
}
