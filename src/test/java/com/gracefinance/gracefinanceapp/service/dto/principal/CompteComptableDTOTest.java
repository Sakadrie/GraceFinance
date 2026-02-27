package com.gracefinance.gracefinanceapp.service.dto.principal;

import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompteComptableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompteComptableDTO.class);
        CompteComptableDTO compteComptableDTO1 = new CompteComptableDTO();
        compteComptableDTO1.setId(1L);
        CompteComptableDTO compteComptableDTO2 = new CompteComptableDTO();
        assertThat(compteComptableDTO1).isNotEqualTo(compteComptableDTO2);
        compteComptableDTO2.setId(compteComptableDTO1.getId());
        assertThat(compteComptableDTO1).isEqualTo(compteComptableDTO2);
        compteComptableDTO2.setId(2L);
        assertThat(compteComptableDTO1).isNotEqualTo(compteComptableDTO2);
        compteComptableDTO1.setId(null);
        assertThat(compteComptableDTO1).isNotEqualTo(compteComptableDTO2);
    }
}
