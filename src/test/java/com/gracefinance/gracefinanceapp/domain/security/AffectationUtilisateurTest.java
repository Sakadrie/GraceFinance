package com.gracefinance.gracefinanceapp.domain.security;

import static com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciereTestSamples.*;
import static com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateurTestSamples.*;
import static com.gracefinance.gracefinanceapp.domain.security.ProfilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AffectationUtilisateurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AffectationUtilisateur.class);
        AffectationUtilisateur affectationUtilisateur1 = getAffectationUtilisateurSample1();
        AffectationUtilisateur affectationUtilisateur2 = new AffectationUtilisateur();
        assertThat(affectationUtilisateur1).isNotEqualTo(affectationUtilisateur2);

        affectationUtilisateur2.setId(affectationUtilisateur1.getId());
        assertThat(affectationUtilisateur1).isEqualTo(affectationUtilisateur2);

        affectationUtilisateur2 = getAffectationUtilisateurSample2();
        assertThat(affectationUtilisateur1).isNotEqualTo(affectationUtilisateur2);
    }

    @Test
    void entiteFinanciereTest() {
        AffectationUtilisateur affectationUtilisateur = getAffectationUtilisateurRandomSampleGenerator();
        EntiteFinanciere entiteFinanciereBack = getEntiteFinanciereRandomSampleGenerator();

        affectationUtilisateur.setEntiteFinanciere(entiteFinanciereBack);
        assertThat(affectationUtilisateur.getEntiteFinanciere()).isEqualTo(entiteFinanciereBack);

        affectationUtilisateur.entiteFinanciere(null);
        assertThat(affectationUtilisateur.getEntiteFinanciere()).isNull();
    }

    @Test
    void profilTest() {
        AffectationUtilisateur affectationUtilisateur = getAffectationUtilisateurRandomSampleGenerator();
        Profil profilBack = getProfilRandomSampleGenerator();

        affectationUtilisateur.addProfil(profilBack);
        assertThat(affectationUtilisateur.getProfils()).containsOnly(profilBack);

        affectationUtilisateur.removeProfil(profilBack);
        assertThat(affectationUtilisateur.getProfils()).doesNotContain(profilBack);

        affectationUtilisateur.profils(new HashSet<>(Set.of(profilBack)));
        assertThat(affectationUtilisateur.getProfils()).containsOnly(profilBack);

        affectationUtilisateur.setProfils(new HashSet<>());
        assertThat(affectationUtilisateur.getProfils()).doesNotContain(profilBack);
    }
}
