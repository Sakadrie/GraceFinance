package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.principal.LigneEcritureAsserts.assertLigneEcritureAllPropertiesEquals;
import static com.gracefinance.gracefinanceapp.domain.principal.LigneEcritureTestSamples.getLigneEcritureSample1;

import com.gracefinance.gracefinanceapp.service.mapper.principal.LigneEcritureMapper;
import com.gracefinance.gracefinanceapp.service.mapper.principal.LigneEcritureMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LigneEcritureMapperTest {

    private LigneEcritureMapper ligneEcritureMapper;

    @BeforeEach
    void setUp() {
        ligneEcritureMapper = new LigneEcritureMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getLigneEcritureSample1();
        var actual = ligneEcritureMapper.toEntity(ligneEcritureMapper.toDto(expected));
        assertLigneEcritureAllPropertiesEquals(expected, actual);
    }
}
