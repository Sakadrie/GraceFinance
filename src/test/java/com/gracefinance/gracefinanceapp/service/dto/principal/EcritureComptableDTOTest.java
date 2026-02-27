package com.gracefinance.gracefinanceapp.service.dto.principal;

import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcritureComptableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EcritureComptableDTO.class);
        EcritureComptableDTO ecritureComptableDTO1 = new EcritureComptableDTO();
        ecritureComptableDTO1.setId(1L);
        EcritureComptableDTO ecritureComptableDTO2 = new EcritureComptableDTO();
        assertThat(ecritureComptableDTO1).isNotEqualTo(ecritureComptableDTO2);
        ecritureComptableDTO2.setId(ecritureComptableDTO1.getId());
        assertThat(ecritureComptableDTO1).isEqualTo(ecritureComptableDTO2);
        ecritureComptableDTO2.setId(2L);
        assertThat(ecritureComptableDTO1).isNotEqualTo(ecritureComptableDTO2);
        ecritureComptableDTO1.setId(null);
        assertThat(ecritureComptableDTO1).isNotEqualTo(ecritureComptableDTO2);
    }
}
