package com.gracefinance.gracefinanceapp.repository.rowmapper;

import com.gracefinance.gracefinanceapp.domain.principal.CompteComptable;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link CompteComptable}, with proper type conversions.
 */
@Service
public class CompteComptableRowMapper implements BiFunction<Row, String, CompteComptable> {

    private final ColumnConverter converter;

    public CompteComptableRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link CompteComptable} stored in the database.
     */
    @Override
    public CompteComptable apply(Row row, String prefix) {
        CompteComptable entity = new CompteComptable();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setLibelle(converter.fromRow(row, prefix + "_libelle", String.class));
        entity.setClasse(converter.fromRow(row, prefix + "_classe", Integer.class));
        return entity;
    }
}
