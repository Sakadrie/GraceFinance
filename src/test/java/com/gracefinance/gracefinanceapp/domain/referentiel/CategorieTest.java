package com.gracefinance.gracefinanceapp.domain.referentiel;

import static com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciereTestSamples.*;
import static com.gracefinance.gracefinanceapp.domain.referentiel.CategorieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categorie.class);
        Categorie categorie1 = getCategorieSample1();
        Categorie categorie2 = new Categorie();
        assertThat(categorie1).isNotEqualTo(categorie2);

        categorie2.setId(categorie1.getId());
        assertThat(categorie1).isEqualTo(categorie2);

        categorie2 = getCategorieSample2();
        assertThat(categorie1).isNotEqualTo(categorie2);
    }

    @Test
    void entiteFinanciereTest() {
        Categorie categorie = getCategorieRandomSampleGenerator();
        EntiteFinanciere entiteFinanciereBack = getEntiteFinanciereRandomSampleGenerator();

        categorie.setEntiteFinanciere(entiteFinanciereBack);
        assertThat(categorie.getEntiteFinanciere()).isEqualTo(entiteFinanciereBack);

        categorie.entiteFinanciere(null);
        assertThat(categorie.getEntiteFinanciere()).isNull();
    }
}
