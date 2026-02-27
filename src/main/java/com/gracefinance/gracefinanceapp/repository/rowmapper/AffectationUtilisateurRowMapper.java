package com.gracefinance.gracefinanceapp.repository.rowmapper;

import com.gracefinance.gracefinanceapp.domain.security.AffectationUtilisateur;
import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link AffectationUtilisateur}, with proper type conversions.
 */
@Service
public class AffectationUtilisateurRowMapper implements BiFunction<Row, String, AffectationUtilisateur> {

    private final ColumnConverter converter;

    public AffectationUtilisateurRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link AffectationUtilisateur} stored in the database.
     */
    @Override
    public AffectationUtilisateur apply(Row row, String prefix) {
        AffectationUtilisateur entity = new AffectationUtilisateur();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setActif(converter.fromRow(row, prefix + "_actif", Boolean.class));
        entity.setDateAffectation(converter.fromRow(row, prefix + "_date_affectation", LocalDate.class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", Long.class));
        entity.setEntiteFinanciereId(converter.fromRow(row, prefix + "_entite_financiere_id", Long.class));
        return entity;
    }
}
