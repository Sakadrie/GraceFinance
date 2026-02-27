package com.gracefinance.gracefinanceapp.service.dto.principal;

import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RecetteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecetteDTO.class);
        RecetteDTO recetteDTO1 = new RecetteDTO();
        recetteDTO1.setId(1L);
        RecetteDTO recetteDTO2 = new RecetteDTO();
        assertThat(recetteDTO1).isNotEqualTo(recetteDTO2);
        recetteDTO2.setId(recetteDTO1.getId());
        assertThat(recetteDTO1).isEqualTo(recetteDTO2);
        recetteDTO2.setId(2L);
        assertThat(recetteDTO1).isNotEqualTo(recetteDTO2);
        recetteDTO1.setId(null);
        assertThat(recetteDTO1).isNotEqualTo(recetteDTO2);
    }
}
