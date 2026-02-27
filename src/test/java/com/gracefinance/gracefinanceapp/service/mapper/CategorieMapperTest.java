package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.principal.CategorieAsserts.*;
import static com.gracefinance.gracefinanceapp.domain.referentiel.CategorieTestSamples.*;

import com.gracefinance.gracefinanceapp.service.mapper.referentiel.CategorieMapper;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.CategorieMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategorieMapperTest {

    private CategorieMapper categorieMapper;

    @BeforeEach
    void setUp() {
        categorieMapper = new CategorieMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCategorieSample1();
        var actual = categorieMapper.toEntity(categorieMapper.toDto(expected));
        assertCategorieAllPropertiesEquals(expected, actual);
    }
}
