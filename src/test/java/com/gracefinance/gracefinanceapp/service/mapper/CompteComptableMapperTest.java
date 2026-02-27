package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.principal.CompteComptableAsserts.*;
import static com.gracefinance.gracefinanceapp.domain.principal.CompteComptableTestSamples.*;

import com.gracefinance.gracefinanceapp.service.mapper.principal.CompteComptableMapper;
import com.gracefinance.gracefinanceapp.service.mapper.principal.CompteComptableMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompteComptableMapperTest {

    private CompteComptableMapper compteComptableMapper;

    @BeforeEach
    void setUp() {
        compteComptableMapper = new CompteComptableMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCompteComptableSample1();
        var actual = compteComptableMapper.toEntity(compteComptableMapper.toDto(expected));
        assertCompteComptableAllPropertiesEquals(expected, actual);
    }
}
