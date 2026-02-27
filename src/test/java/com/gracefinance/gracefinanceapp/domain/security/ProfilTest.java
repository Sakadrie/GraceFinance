package com.gracefinance.gracefinanceapp.domain.security;

import static com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateurTestSamples.*;
import static com.gracefinance.gracefinanceapp.domain.security.DroitTestSamples.*;
import static com.gracefinance.gracefinanceapp.domain.security.ProfilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProfilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profil.class);
        Profil profil1 = getProfilSample1();
        Profil profil2 = new Profil();
        assertThat(profil1).isNotEqualTo(profil2);

        profil2.setId(profil1.getId());
        assertThat(profil1).isEqualTo(profil2);

        profil2 = getProfilSample2();
        assertThat(profil1).isNotEqualTo(profil2);
    }

    @Test
    void droitTest() {
        Profil profil = getProfilRandomSampleGenerator();
        Droit droitBack = getDroitRandomSampleGenerator();

        profil.addDroit(droitBack);
        assertThat(profil.getDroits()).containsOnly(droitBack);

        profil.removeDroit(droitBack);
        assertThat(profil.getDroits()).doesNotContain(droitBack);

        profil.droits(new HashSet<>(Set.of(droitBack)));
        assertThat(profil.getDroits()).containsOnly(droitBack);

        profil.setDroits(new HashSet<>());
        assertThat(profil.getDroits()).doesNotContain(droitBack);
    }

    @Test
    void affectationTest() {
        Profil profil = getProfilRandomSampleGenerator();
        AffectationUtilisateur affectationUtilisateurBack = getAffectationUtilisateurRandomSampleGenerator();

        profil.addAffectation(affectationUtilisateurBack);
        assertThat(profil.getAffectations()).containsOnly(affectationUtilisateurBack);
        assertThat(affectationUtilisateurBack.getProfils()).containsOnly(profil);

        profil.removeAffectation(affectationUtilisateurBack);
        assertThat(profil.getAffectations()).doesNotContain(affectationUtilisateurBack);
        assertThat(affectationUtilisateurBack.getProfils()).doesNotContain(profil);

        profil.affectations(new HashSet<>(Set.of(affectationUtilisateurBack)));
        assertThat(profil.getAffectations()).containsOnly(affectationUtilisateurBack);
        assertThat(affectationUtilisateurBack.getProfils()).containsOnly(profil);

        profil.setAffectations(new HashSet<>());
        assertThat(profil.getAffectations()).doesNotContain(affectationUtilisateurBack);
        assertThat(affectationUtilisateurBack.getProfils()).doesNotContain(profil);
    }
}
