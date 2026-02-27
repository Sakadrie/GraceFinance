package com.gracefinance.gracefinanceapp.domain.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.CompteComptableTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.domain.principal.CompteComptable;
import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompteComptableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteComptable.class);
        CompteComptable compteComptable1 = getCompteComptableSample1();
        CompteComptable compteComptable2 = new CompteComptable();
        assertThat(compteComptable1).isNotEqualTo(compteComptable2);

        compteComptable2.setId(compteComptable1.getId());
        assertThat(compteComptable1).isEqualTo(compteComptable2);

        compteComptable2 = getCompteComptableSample2();
        assertThat(compteComptable1).isNotEqualTo(compteComptable2);
    }
}
