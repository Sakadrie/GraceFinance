package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.principal.EcritureComptableAsserts.assertEcritureComptableAllPropertiesEquals;
import static com.gracefinance.gracefinanceapp.domain.principal.EcritureComptableTestSamples.getEcritureComptableSample1;

import com.gracefinance.gracefinanceapp.service.mapper.principal.EcritureComptableMapper;
import com.gracefinance.gracefinanceapp.service.mapper.principal.EcritureComptableMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EcritureComptableMapperTest {

    private EcritureComptableMapper ecritureComptableMapper;

    @BeforeEach
    void setUp() {
        ecritureComptableMapper = new EcritureComptableMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEcritureComptableSample1();
        var actual = ecritureComptableMapper.toEntity(ecritureComptableMapper.toDto(expected));
        assertEcritureComptableAllPropertiesEquals(expected, actual);
    }
}
