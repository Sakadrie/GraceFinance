package com.gracefinance.gracefinanceapp.repository.rowmapper;

import com.gracefinance.gracefinanceapp.domain.principal.Caisse;
import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Caisse}, with proper type conversions.
 */
@Service
public class CaisseRowMapper implements BiFunction<Row, String, Caisse> {

    private final ColumnConverter converter;

    public CaisseRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Caisse} stored in the database.
     */
    @Override
    public Caisse apply(Row row, String prefix) {
        Caisse entity = new Caisse();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNom(converter.fromRow(row, prefix + "_nom", String.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setType(converter.fromRow(row, prefix + "_type", String.class));
        entity.setDevise(converter.fromRow(row, prefix + "_devise", String.class));
        entity.setSolde(converter.fromRow(row, prefix + "_solde", BigDecimal.class));
        entity.setActif(converter.fromRow(row, prefix + "_actif", Boolean.class));
        entity.setEntiteFinanciereId(converter.fromRow(row, prefix + "_entite_financiere_id", Long.class));
        return entity;
    }
}
