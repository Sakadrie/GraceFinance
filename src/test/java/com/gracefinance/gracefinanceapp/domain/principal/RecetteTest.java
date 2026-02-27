package com.gracefinance.gracefinanceapp.domain.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.CaisseTestSamples.getCaisseRandomSampleGenerator;
import static com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciereTestSamples.getEntiteFinanciereRandomSampleGenerator;
import static com.gracefinance.gracefinanceapp.domain.principal.RecetteTestSamples.getRecetteRandomSampleGenerator;
import static com.gracefinance.gracefinanceapp.domain.principal.RecetteTestSamples.getRecetteSample1;
import static com.gracefinance.gracefinanceapp.domain.principal.RecetteTestSamples.getRecetteSample2;
import static com.gracefinance.gracefinanceapp.domain.referentiel.CategorieTestSamples.getCategorieRandomSampleGenerator;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecetteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recette.class);
        Recette recette1 = getRecetteSample1();
        Recette recette2 = new Recette();
        assertThat(recette1).isNotEqualTo(recette2);

        recette2.setId(recette1.getId());
        assertThat(recette1).isEqualTo(recette2);

        recette2 = getRecetteSample2();
        assertThat(recette1).isNotEqualTo(recette2);
    }

    @Test
    void entiteFinanciereTest() {
        Recette recette = getRecetteRandomSampleGenerator();
        EntiteFinanciere entiteFinanciereBack = getEntiteFinanciereRandomSampleGenerator();

        recette.setEntiteFinanciere(entiteFinanciereBack);
        assertThat(recette.getEntiteFinanciere()).isEqualTo(entiteFinanciereBack);

        recette.entiteFinanciere(null);
        assertThat(recette.getEntiteFinanciere()).isNull();
    }

    @Test
    void caisseTest() {
        Recette recette = getRecetteRandomSampleGenerator();
        Caisse caisseBack = getCaisseRandomSampleGenerator();

        recette.setCaisse(caisseBack);
        assertThat(recette.getCaisse()).isEqualTo(caisseBack);

        recette.caisse(null);
        assertThat(recette.getCaisse()).isNull();
    }

    @Test
    void categorieTest() {
        Recette recette = getRecetteRandomSampleGenerator();
        Categorie categorieBack = getCategorieRandomSampleGenerator();

        recette.setCategorie(categorieBack);
        assertThat(recette.getCategorie()).isEqualTo(categorieBack);

        recette.categorie(null);
        assertThat(recette.getCategorie()).isNull();
    }
}
