package com.gracefinance.gracefinanceapp.domain.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.CaisseTestSamples.getCaisseRandomSampleGenerator;
import static com.gracefinance.gracefinanceapp.domain.principal.DepenseTestSamples.getDepenseRandomSampleGenerator;
import static com.gracefinance.gracefinanceapp.domain.principal.DepenseTestSamples.getDepenseSample1;
import static com.gracefinance.gracefinanceapp.domain.principal.DepenseTestSamples.getDepenseSample2;
import static com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciereTestSamples.getEntiteFinanciereRandomSampleGenerator;
import static com.gracefinance.gracefinanceapp.domain.referentiel.CategorieTestSamples.getCategorieRandomSampleGenerator;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepenseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Depense.class);
        Depense depense1 = getDepenseSample1();
        Depense depense2 = new Depense();
        assertThat(depense1).isNotEqualTo(depense2);

        depense2.setId(depense1.getId());
        assertThat(depense1).isEqualTo(depense2);

        depense2 = getDepenseSample2();
        assertThat(depense1).isNotEqualTo(depense2);
    }

    @Test
    void entiteFinanciereTest() {
        Depense depense = getDepenseRandomSampleGenerator();
        EntiteFinanciere entiteFinanciereBack = getEntiteFinanciereRandomSampleGenerator();

        depense.setEntiteFinanciere(entiteFinanciereBack);
        assertThat(depense.getEntiteFinanciere()).isEqualTo(entiteFinanciereBack);

        depense.entiteFinanciere(null);
        assertThat(depense.getEntiteFinanciere()).isNull();
    }

    @Test
    void caisseTest() {
        Depense depense = getDepenseRandomSampleGenerator();
        Caisse caisseBack = getCaisseRandomSampleGenerator();

        depense.setCaisse(caisseBack);
        assertThat(depense.getCaisse()).isEqualTo(caisseBack);

        depense.caisse(null);
        assertThat(depense.getCaisse()).isNull();
    }

    @Test
    void categorieTest() {
        Depense depense = getDepenseRandomSampleGenerator();
        Categorie categorieBack = getCategorieRandomSampleGenerator();

        depense.setCategorie(categorieBack);
        assertThat(depense.getCategorie()).isEqualTo(categorieBack);

        depense.categorie(null);
        assertThat(depense.getCategorie()).isNull();
    }
}
