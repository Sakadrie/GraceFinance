package com.gracefinance.gracefinanceapp.repository.rowmapper;

import com.gracefinance.gracefinanceapp.domain.principal.EcritureComptable;
import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link EcritureComptable}, with proper type conversions.
 */
@Service
public class EcritureComptableRowMapper implements BiFunction<Row, String, EcritureComptable> {

    private final ColumnConverter converter;

    public EcritureComptableRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link EcritureComptable} stored in the database.
     */
    @Override
    public EcritureComptable apply(Row row, String prefix) {
        EcritureComptable entity = new EcritureComptable();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setDateComptable(converter.fromRow(row, prefix + "_date_comptable", LocalDate.class));
        entity.setNumeroPiece(converter.fromRow(row, prefix + "_numero_piece", String.class));
        entity.setLibelle(converter.fromRow(row, prefix + "_libelle", String.class));
        entity.setReferenceExterne(converter.fromRow(row, prefix + "_reference_externe", String.class));
        return entity;
    }
}
