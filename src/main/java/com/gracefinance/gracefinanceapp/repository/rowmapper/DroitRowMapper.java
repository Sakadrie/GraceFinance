package com.gracefinance.gracefinanceapp.repository.rowmapper;

import com.gracefinance.gracefinanceapp.domain.security.Droit;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Droit}, with proper type conversions.
 */
@Service
public class DroitRowMapper implements BiFunction<Row, String, Droit> {

    private final ColumnConverter converter;

    public DroitRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Droit} stored in the database.
     */
    @Override
    public Droit apply(Row row, String prefix) {
        Droit entity = new Droit();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNom(converter.fromRow(row, prefix + "_nom", String.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        return entity;
    }
}
