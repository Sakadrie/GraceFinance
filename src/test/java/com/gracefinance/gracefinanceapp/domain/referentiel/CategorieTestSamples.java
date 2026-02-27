package com.gracefinance.gracefinanceapp.domain.referentiel;

import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CategorieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Categorie getCategorieSample1() {
        return new Categorie().id(1L).nom("nom1").code("code1").typeCategorie("typeCategorie1").description("description1");
    }

    public static Categorie getCategorieSample2() {
        return new Categorie().id(2L).nom("nom2").code("code2").typeCategorie("typeCategorie2").description("description2");
    }

    public static Categorie getCategorieRandomSampleGenerator() {
        return new Categorie()
            .id(longCount.incrementAndGet())
            .nom(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .typeCategorie(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString());
    }
}
