package com.gracefinance.gracefinanceapp.domain.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciereTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EntiteFinanciereTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntiteFinanciere.class);
        EntiteFinanciere entiteFinanciere1 = getEntiteFinanciereSample1();
        EntiteFinanciere entiteFinanciere2 = new EntiteFinanciere();
        assertThat(entiteFinanciere1).isNotEqualTo(entiteFinanciere2);

        entiteFinanciere2.setId(entiteFinanciere1.getId());
        assertThat(entiteFinanciere1).isEqualTo(entiteFinanciere2);

        entiteFinanciere2 = getEntiteFinanciereSample2();
        assertThat(entiteFinanciere1).isNotEqualTo(entiteFinanciere2);
    }

    @Test
    void egliseLieeTest() {
        EntiteFinanciere entiteFinanciere = getEntiteFinanciereRandomSampleGenerator();
        EntiteFinanciere entiteFinanciereBack = getEntiteFinanciereRandomSampleGenerator();

        entiteFinanciere.addEgliseLiee(entiteFinanciereBack);
        assertThat(entiteFinanciere.getEgliseLiees()).containsOnly(entiteFinanciereBack);

        entiteFinanciere.removeEgliseLiee(entiteFinanciereBack);
        assertThat(entiteFinanciere.getEgliseLiees()).doesNotContain(entiteFinanciereBack);

        entiteFinanciere.egliseLiees(new HashSet<>(Set.of(entiteFinanciereBack)));
        assertThat(entiteFinanciere.getEgliseLiees()).containsOnly(entiteFinanciereBack);

        entiteFinanciere.setEgliseLiees(new HashSet<>());
        assertThat(entiteFinanciere.getEgliseLiees()).doesNotContain(entiteFinanciereBack);
    }

    @Test
    void structureLieeTest() {
        EntiteFinanciere entiteFinanciere = getEntiteFinanciereRandomSampleGenerator();
        EntiteFinanciere entiteFinanciereBack = getEntiteFinanciereRandomSampleGenerator();

        entiteFinanciere.addStructureLiee(entiteFinanciereBack);
        assertThat(entiteFinanciere.getStructureLiees()).containsOnly(entiteFinanciereBack);
        assertThat(entiteFinanciereBack.getEgliseLiees()).containsOnly(entiteFinanciere);

        entiteFinanciere.removeStructureLiee(entiteFinanciereBack);
        assertThat(entiteFinanciere.getStructureLiees()).doesNotContain(entiteFinanciereBack);
        assertThat(entiteFinanciereBack.getEgliseLiees()).doesNotContain(entiteFinanciere);

        entiteFinanciere.structureLiees(new HashSet<>(Set.of(entiteFinanciereBack)));
        assertThat(entiteFinanciere.getStructureLiees()).containsOnly(entiteFinanciereBack);
        assertThat(entiteFinanciereBack.getEgliseLiees()).containsOnly(entiteFinanciere);

        entiteFinanciere.setStructureLiees(new HashSet<>());
        assertThat(entiteFinanciere.getStructureLiees()).doesNotContain(entiteFinanciereBack);
        assertThat(entiteFinanciereBack.getEgliseLiees()).doesNotContain(entiteFinanciere);
    }
}
