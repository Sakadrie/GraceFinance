package com.gracefinance.gracefinanceapp.repository.rowmapper;

import com.gracefinance.gracefinanceapp.domain.principal.EntiteFinanciere;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link EntiteFinanciere}, with proper type conversions.
 */
@Service
public class EntiteFinanciereRowMapper implements BiFunction<Row, String, EntiteFinanciere> {

    private final ColumnConverter converter;

    public EntiteFinanciereRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link EntiteFinanciere} stored in the database.
     */
    @Override
    public EntiteFinanciere apply(Row row, String prefix) {
        EntiteFinanciere entity = new EntiteFinanciere();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNom(converter.fromRow(row, prefix + "_nom", String.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setActif(converter.fromRow(row, prefix + "_actif", Boolean.class));
        return entity;
    }
}
