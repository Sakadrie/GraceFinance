package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciereAsserts.assertEntiteFinanciereAllPropertiesEquals;
import static com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciereTestSamples.getEntiteFinanciereSample1;

import com.gracefinance.gracefinanceapp.service.mapper.principal.EntiteFinanciereMapper;
import com.gracefinance.gracefinanceapp.service.mapper.principal.EntiteFinanciereMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntiteFinanciereMapperTest {

    private EntiteFinanciereMapper entiteFinanciereMapper;

    @BeforeEach
    void setUp() {
        entiteFinanciereMapper = new EntiteFinanciereMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEntiteFinanciereSample1();
        var actual = entiteFinanciereMapper.toEntity(entiteFinanciereMapper.toDto(expected));
        assertEntiteFinanciereAllPropertiesEquals(expected, actual);
    }
}
