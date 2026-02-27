package com.gracefinance.gracefinanceapp.domain.referentiel;

import static com.gracefinance.gracefinanceapp.domain.principal.CaisseTestSamples.*;
import static com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciereTestSamples.*;
import static com.gracefinance.gracefinanceapp.domain.referentiel.TransfertTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.domain.referentiel.Transfert;
import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TransfertTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Transfert.class);
        Transfert transfert1 = getTransfertSample1();
        Transfert transfert2 = new Transfert();
        assertThat(transfert1).isNotEqualTo(transfert2);

        transfert2.setId(transfert1.getId());
        assertThat(transfert1).isEqualTo(transfert2);

        transfert2 = getTransfertSample2();
        assertThat(transfert1).isNotEqualTo(transfert2);
    }

    @Test
    void entiteFinanciereSourceTest() {
        Transfert transfert = getTransfertRandomSampleGenerator();
        EntiteFinanciere entiteFinanciereBack = getEntiteFinanciereRandomSampleGenerator();

        transfert.setEntiteFinanciereSource(entiteFinanciereBack);
        assertThat(transfert.getEntiteFinanciereSource()).isEqualTo(entiteFinanciereBack);

        transfert.entiteFinanciereSource(null);
        assertThat(transfert.getEntiteFinanciereSource()).isNull();
    }

    @Test
    void caisseSourceTest() {
        Transfert transfert = getTransfertRandomSampleGenerator();
        Caisse caisseBack = getCaisseRandomSampleGenerator();

        transfert.setCaisseSource(caisseBack);
        assertThat(transfert.getCaisseSource()).isEqualTo(caisseBack);

        transfert.caisseSource(null);
        assertThat(transfert.getCaisseSource()).isNull();
    }

    @Test
    void caisseDestinationTest() {
        Transfert transfert = getTransfertRandomSampleGenerator();
        Caisse caisseBack = getCaisseRandomSampleGenerator();

        transfert.setCaisseDestination(caisseBack);
        assertThat(transfert.getCaisseDestination()).isEqualTo(caisseBack);

        transfert.caisseDestination(null);
        assertThat(transfert.getCaisseDestination()).isNull();
    }
}
