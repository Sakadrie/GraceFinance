package com.gracefinance.gracefinanceapp.service.mapper;

import static com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateurAsserts.assertAffectationUtilisateurAllPropertiesEquals;
import static com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateurTestSamples.getAffectationUtilisateurSample1;

import com.gracefinance.gracefinanceapp.service.mapper.security.AffectationUtilisateurMapper;
import com.gracefinance.gracefinanceapp.service.mapper.security.AffectationUtilisateurMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AffectationUtilisateurMapperTest {

    private AffectationUtilisateurMapper affectationUtilisateurMapper;

    @BeforeEach
    void setUp() {
        affectationUtilisateurMapper = new AffectationUtilisateurMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAffectationUtilisateurSample1();
        var actual = affectationUtilisateurMapper.toEntity(affectationUtilisateurMapper.toDto(expected));
        assertAffectationUtilisateurAllPropertiesEquals(expected, actual);
    }
}
