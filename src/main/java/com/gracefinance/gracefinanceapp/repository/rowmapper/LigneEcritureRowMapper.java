package com.gracefinance.gracefinanceapp.repository.rowmapper;

import com.gracefinance.gracefinanceapp.domain.principal.LigneEcriture;
import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link LigneEcriture}, with proper type conversions.
 */
@Service
public class LigneEcritureRowMapper implements BiFunction<Row, String, LigneEcriture> {

    private final ColumnConverter converter;

    public LigneEcritureRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link LigneEcriture} stored in the database.
     */
    @Override
    public LigneEcriture apply(Row row, String prefix) {
        LigneEcriture entity = new LigneEcriture();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setMontant(converter.fromRow(row, prefix + "_montant", BigDecimal.class));
        entity.setSens(converter.fromRow(row, prefix + "_sens", String.class));
        entity.setLibelle(converter.fromRow(row, prefix + "_libelle", String.class));
        entity.setEcritureId(converter.fromRow(row, prefix + "_ecriture_id", Long.class));
        entity.setCompteId(converter.fromRow(row, prefix + "_compte_id", Long.class));
        return entity;
    }
}
