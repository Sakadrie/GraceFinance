package com.gracefinance.gracefinanceapp.domain.security;

import static com.gracefinance.gracefinanceapp.domain.security.DroitTestSamples.*;
import static com.gracefinance.gracefinanceapp.domain.security.ProfilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DroitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Droit.class);
        Droit droit1 = getDroitSample1();
        Droit droit2 = new Droit();
        assertThat(droit1).isNotEqualTo(droit2);

        droit2.setId(droit1.getId());
        assertThat(droit1).isEqualTo(droit2);

        droit2 = getDroitSample2();
        assertThat(droit1).isNotEqualTo(droit2);
    }

    @Test
    void profilTest() {
        Droit droit = getDroitRandomSampleGenerator();
        Profil profilBack = getProfilRandomSampleGenerator();

        droit.addProfil(profilBack);
        assertThat(droit.getProfils()).containsOnly(profilBack);
        assertThat(profilBack.getDroits()).containsOnly(droit);

        droit.removeProfil(profilBack);
        assertThat(droit.getProfils()).doesNotContain(profilBack);
        assertThat(profilBack.getDroits()).doesNotContain(droit);

        droit.profils(new HashSet<>(Set.of(profilBack)));
        assertThat(droit.getProfils()).containsOnly(profilBack);
        assertThat(profilBack.getDroits()).containsOnly(droit);

        droit.setProfils(new HashSet<>());
        assertThat(droit.getProfils()).doesNotContain(profilBack);
        assertThat(profilBack.getDroits()).doesNotContain(droit);
    }
}
