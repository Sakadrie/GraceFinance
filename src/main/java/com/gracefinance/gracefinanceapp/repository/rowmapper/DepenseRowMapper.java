package com.gracefinance.gracefinanceapp.repository.rowmapper;

import com.gracefinance.gracefinanceapp.domain.principal.Depense;
import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Depense}, with proper type conversions.
 */
@Service
public class DepenseRowMapper implements BiFunction<Row, String, Depense> {

    private final ColumnConverter converter;

    public DepenseRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Depense} stored in the database.
     */
    @Override
    public Depense apply(Row row, String prefix) {
        Depense entity = new Depense();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setDateDepense(converter.fromRow(row, prefix + "_date_depense", LocalDate.class));
        entity.setMontant(converter.fromRow(row, prefix + "_montant", BigDecimal.class));
        entity.setMotif(converter.fromRow(row, prefix + "_motif", String.class));
        entity.setReferencePiece(converter.fromRow(row, prefix + "_reference_piece", String.class));
        entity.setStatut(converter.fromRow(row, prefix + "_statut", String.class));
        entity.setValiderPar(converter.fromRow(row, prefix + "_valider_par", String.class));
        entity.setDateValidation(converter.fromRow(row, prefix + "_date_validation", Instant.class));
        entity.setEntiteFinanciereId(converter.fromRow(row, prefix + "_entite_financiere_id", Long.class));
        entity.setCaisseId(converter.fromRow(row, prefix + "_caisse_id", Long.class));
        entity.setCategorieId(converter.fromRow(row, prefix + "_categorie_id", Long.class));
        return entity;
    }
}
