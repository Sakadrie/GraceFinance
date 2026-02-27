package com.gracefinance.gracefinanceapp.domain.principal;

import static com.gracefinance.gracefinanceapp.domain.principal.EcritureComptableTestSamples.getEcritureComptableSample1;
import static com.gracefinance.gracefinanceapp.domain.principal.EcritureComptableTestSamples.getEcritureComptableSample2;
import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcritureComptableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EcritureComptable.class);
        EcritureComptable ecritureComptable1 = getEcritureComptableSample1();
        EcritureComptable ecritureComptable2 = new EcritureComptable();
        assertThat(ecritureComptable1).isNotEqualTo(ecritureComptable2);

        ecritureComptable2.setId(ecritureComptable1.getId());
        assertThat(ecritureComptable1).isEqualTo(ecritureComptable2);

        ecritureComptable2 = getEcritureComptableSample2();
        assertThat(ecritureComptable1).isNotEqualTo(ecritureComptable2);
    }
}
