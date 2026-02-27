package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.principal.RecetteAsserts.assertRecetteAllPropertiesEquals;
import static com.gracefinance.gracefinanceapp.domain.principal.RecetteTestSamples.getRecetteSample1;

import com.gracefinance.gracefinanceapp.service.mapper.principal.RecetteMapper;
import com.gracefinance.gracefinanceapp.service.mapper.principal.RecetteMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecetteMapperTest {

    private RecetteMapper recetteMapper;

    @BeforeEach
    void setUp() {
        recetteMapper = new RecetteMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRecetteSample1();
        var actual = recetteMapper.toEntity(recetteMapper.toDto(expected));
        assertRecetteAllPropertiesEquals(expected, actual);
    }
}
