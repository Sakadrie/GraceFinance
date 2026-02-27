package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.security.DroitAsserts.assertDroitAllPropertiesEquals;
import static com.gracefinance.gracefinanceapp.domain.security.DroitTestSamples.getDroitSample1;

import com.gracefinance.gracefinanceapp.service.mapper.security.DroitMapper;
import com.gracefinance.gracefinanceapp.service.mapper.security.DroitMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DroitMapperTest {

    private DroitMapper droitMapper;

    @BeforeEach
    void setUp() {
        droitMapper = new DroitMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDroitSample1();
        var actual = droitMapper.toEntity(droitMapper.toDto(expected));
        assertDroitAllPropertiesEquals(expected, actual);
    }
}
