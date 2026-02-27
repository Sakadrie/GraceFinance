package com.gracefinance.gracefinanceapp.domain.principal;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RecetteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Recette getRecetteSample1() {
        return new Recette()
            .id(1L)
            .code("code1")
            .typeRecette("typeRecette1")
            .membreNom("membreNom1")
            .motif("motif1")
            .referencePiece("referencePiece1")
            .statut("statut1");
    }

    public static Recette getRecetteSample2() {
        return new Recette()
            .id(2L)
            .code("code2")
            .typeRecette("typeRecette2")
            .membreNom("membreNom2")
            .motif("motif2")
            .referencePiece("referencePiece2")
            .statut("statut2");
    }

    public static Recette getRecetteRandomSampleGenerator() {
        return new Recette()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .typeRecette(UUID.randomUUID().toString())
            .membreNom(UUID.randomUUID().toString())
            .motif(UUID.randomUUID().toString())
            .referencePiece(UUID.randomUUID().toString())
            .statut(UUID.randomUUID().toString());
    }
}
