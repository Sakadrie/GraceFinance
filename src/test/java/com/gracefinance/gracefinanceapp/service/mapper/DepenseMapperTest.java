package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.principal.DepenseAsserts.assertDepenseAllPropertiesEquals;
import static com.gracefinance.gracefinanceapp.domain.principal.DepenseTestSamples.getDepenseSample1;

import com.gracefinance.gracefinanceapp.service.mapper.principal.DepenseMapper;
import com.gracefinance.gracefinanceapp.service.mapper.principal.DepenseMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepenseMapperTest {

    private DepenseMapper depenseMapper;

    @BeforeEach
    void setUp() {
        depenseMapper = new DepenseMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDepenseSample1();
        var actual = depenseMapper.toEntity(depenseMapper.toDto(expected));
        assertDepenseAllPropertiesEquals(expected, actual);
    }
}
