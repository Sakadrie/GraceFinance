package com.gracefinance.gracefinanceapp.repository.rowmapper;

import com.gracefinance.gracefinanceapp.domain.referentiel.Transfert;
import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Transfert}, with proper type conversions.
 */
@Service
public class TransfertRowMapper implements BiFunction<Row, String, Transfert> {

    private final ColumnConverter converter;

    public TransfertRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Transfert} stored in the database.
     */
    @Override
    public Transfert apply(Row row, String prefix) {
        Transfert entity = new Transfert();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setDateTransfert(converter.fromRow(row, prefix + "_date_transfert", LocalDate.class));
        entity.setMontant(converter.fromRow(row, prefix + "_montant", BigDecimal.class));
        entity.setMotif(converter.fromRow(row, prefix + "_motif", String.class));
        entity.setTypeTransfert(converter.fromRow(row, prefix + "_type_transfert", String.class));
        entity.setStatut(converter.fromRow(row, prefix + "_statut", String.class));
        entity.setValiderPar(converter.fromRow(row, prefix + "_valider_par", String.class));
        entity.setDateValidation(converter.fromRow(row, prefix + "_date_validation", Instant.class));
        entity.setEntiteFinanciereSourceId(converter.fromRow(row, prefix + "_entite_financiere_source_id", Long.class));
        entity.setCaisseSourceId(converter.fromRow(row, prefix + "_caisse_source_id", Long.class));
        entity.setCaisseDestinationId(converter.fromRow(row, prefix + "_caisse_destination_id", Long.class));
        return entity;
    }
}
