package com.gracefinance.gracefinanceapp.service.dto.principal;

import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.service.dto.principal.LigneEcritureDTO;
import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LigneEcritureDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LigneEcritureDTO.class);
        LigneEcritureDTO ligneEcritureDTO1 = new LigneEcritureDTO();
        ligneEcritureDTO1.setId(1L);
        LigneEcritureDTO ligneEcritureDTO2 = new LigneEcritureDTO();
        assertThat(ligneEcritureDTO1).isNotEqualTo(ligneEcritureDTO2);
        ligneEcritureDTO2.setId(ligneEcritureDTO1.getId());
        assertThat(ligneEcritureDTO1).isEqualTo(ligneEcritureDTO2);
        ligneEcritureDTO2.setId(2L);
        assertThat(ligneEcritureDTO1).isNotEqualTo(ligneEcritureDTO2);
        ligneEcritureDTO1.setId(null);
        assertThat(ligneEcritureDTO1).isNotEqualTo(ligneEcritureDTO2);
    }
}
