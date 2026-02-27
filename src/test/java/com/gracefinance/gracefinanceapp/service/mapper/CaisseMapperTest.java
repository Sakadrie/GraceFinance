package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.principal.CaisseAsserts.*;
import static com.gracefinance.gracefinanceapp.domain.principal.CaisseTestSamples.*;

import com.gracefinance.gracefinanceapp.service.mapper.principal.CaisseMapper;
import com.gracefinance.gracefinanceapp.service.mapper.principal.CaisseMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CaisseMapperTest {

    private CaisseMapper caisseMapper;

    @BeforeEach
    void setUp() {
        caisseMapper = new CaisseMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCaisseSample1();
        var actual = caisseMapper.toEntity(caisseMapper.toDto(expected));
        assertCaisseAllPropertiesEquals(expected, actual);
    }
}
