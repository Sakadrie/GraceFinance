package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.referentiel.TransfertAsserts.assertTransfertAllPropertiesEquals;
import static com.gracefinance.gracefinanceapp.domain.referentiel.TransfertTestSamples.getTransfertSample1;

import com.gracefinance.gracefinanceapp.service.mapper.referentiel.TransfertMapper;
import com.gracefinance.gracefinanceapp.service.mapper.referentiel.TransfertMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransfertMapperTest {

    private TransfertMapper transfertMapper;

    @BeforeEach
    void setUp() {
        transfertMapper = new TransfertMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTransfertSample1();
        var actual = transfertMapper.toEntity(transfertMapper.toDto(expected));
        assertTransfertAllPropertiesEquals(expected, actual);
    }
}
