package com.gracefinance.gracefinanceapp.service.dto.principal;

import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntiteFinanciereDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntiteFinanciereDTO.class);
        EntiteFinanciereDTO entiteFinanciereDTO1 = new EntiteFinanciereDTO();
        entiteFinanciereDTO1.setId(1L);
        EntiteFinanciereDTO entiteFinanciereDTO2 = new EntiteFinanciereDTO();
        assertThat(entiteFinanciereDTO1).isNotEqualTo(entiteFinanciereDTO2);
        entiteFinanciereDTO2.setId(entiteFinanciereDTO1.getId());
        assertThat(entiteFinanciereDTO1).isEqualTo(entiteFinanciereDTO2);
        entiteFinanciereDTO2.setId(2L);
        assertThat(entiteFinanciereDTO1).isNotEqualTo(entiteFinanciereDTO2);
        entiteFinanciereDTO1.setId(null);
        assertThat(entiteFinanciereDTO1).isNotEqualTo(entiteFinanciereDTO2);
    }
}
