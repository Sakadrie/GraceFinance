package com.gracefinance.gracefinanceapp.service.dto.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.gracefinance.gracefinanceapp.service.dto.security.DroitDTO;
import com.gracefinance.gracefinanceapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DroitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DroitDTO.class);
        DroitDTO droitDTO1 = new DroitDTO();
        droitDTO1.setId(1L);
        DroitDTO droitDTO2 = new DroitDTO();
        assertThat(droitDTO1).isNotEqualTo(droitDTO2);
        droitDTO2.setId(droitDTO1.getId());
        assertThat(droitDTO1).isEqualTo(droitDTO2);
        droitDTO2.setId(2L);
        assertThat(droitDTO1).isNotEqualTo(droitDTO2);
        droitDTO1.setId(null);
        assertThat(droitDTO1).isNotEqualTo(droitDTO2);
    }
}
