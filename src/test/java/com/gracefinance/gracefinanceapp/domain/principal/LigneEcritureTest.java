package com.gracefinance.gracefinanceapp.domain.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.CompteComptableTestSamples.*;
import static com.gracefinance.gracefinanceapp.domain.principal.EcritureComptableTestSamples.*;
import static com.gracefinance.gracefinanceapp.domain.principal.LigneEcritureTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LigneEcritureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LigneEcriture.class);
        LigneEcriture ligneEcriture1 = getLigneEcritureSample1();
        LigneEcriture ligneEcriture2 = new LigneEcriture();
        assertThat(ligneEcriture1).isNotEqualTo(ligneEcriture2);

        ligneEcriture2.setId(ligneEcriture1.getId());
        assertThat(ligneEcriture1).isEqualTo(ligneEcriture2);

        ligneEcriture2 = getLigneEcritureSample2();
        assertThat(ligneEcriture1).isNotEqualTo(ligneEcriture2);
    }

    @Test
    void ecritureTest() {
        LigneEcriture ligneEcriture = getLigneEcritureRandomSampleGenerator();
        EcritureComptable ecritureComptableBack = getEcritureComptableRandomSampleGenerator();

        ligneEcriture.setEcriture(ecritureComptableBack);
        assertThat(ligneEcriture.getEcriture()).isEqualTo(ecritureComptableBack);

        ligneEcriture.ecriture(null);
        assertThat(ligneEcriture.getEcriture()).isNull();
    }

    @Test
    void compteTest() {
        LigneEcriture ligneEcriture = getLigneEcritureRandomSampleGenerator();
        CompteComptable compteComptableBack = getCompteComptableRandomSampleGenerator();

        ligneEcriture.setCompte(compteComptableBack);
        assertThat(ligneEcriture.getCompte()).isEqualTo(compteComptableBack);

        ligneEcriture.compte(null);
        assertThat(ligneEcriture.getCompte()).isNull();
    }
}
