package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.security.ProfilAsserts.assertProfilAllPropertiesEquals;
import static com.gracefinance.gracefinanceapp.domain.security.ProfilTestSamples.getProfilSample1;

import com.gracefinance.gracefinanceapp.service.mapper.security.ProfilMapper;
import com.gracefinance.gracefinanceapp.service.mapper.security.ProfilMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProfilMapperTest {

    private ProfilMapper profilMapper;

    @BeforeEach
    void setUp() {
        profilMapper = new ProfilMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProfilSample1();
        var actual = profilMapper.toEntity(profilMapper.toDto(expected));
        assertProfilAllPropertiesEquals(expected, actual);
    }
}
