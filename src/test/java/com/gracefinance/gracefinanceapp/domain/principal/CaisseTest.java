package com.gracefinance.gracefinanceapp.domain.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.CaisseTestSamples.*;
import static com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciereTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CaisseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caisse.class);
        Caisse caisse1 = getCaisseSample1();
        Caisse caisse2 = new Caisse();
        assertThat(caisse1).isNotEqualTo(caisse2);

        caisse2.setId(caisse1.getId());
        assertThat(caisse1).isEqualTo(caisse2);

        caisse2 = getCaisseSample2();
        assertThat(caisse1).isNotEqualTo(caisse2);
    }

    @Test
    void entiteFinanciereTest() {
        Caisse caisse = getCaisseRandomSampleGenerator();
        EntiteFinanciere entiteFinanciereBack = getEntiteFinanciereRandomSampleGenerator();

        caisse.setEntiteFinanciere(entiteFinanciereBack);
        assertThat(caisse.getEntiteFinanciere()).isEqualTo(entiteFinanciereBack);

        caisse.entiteFinanciere(null);
        assertThat(caisse.getEntiteFinanciere()).isNull();
    }
}
