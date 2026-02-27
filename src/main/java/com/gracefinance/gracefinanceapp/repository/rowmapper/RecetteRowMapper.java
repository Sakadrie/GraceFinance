package com.gracefinance.gracefinanceapp.repository.rowmapper;

import com.gracefinance.gracefinanceapp.domain.principal.Recette;
import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Recette}, with proper type conversions.
 */
@Service
public class RecetteRowMapper implements BiFunction<Row, String, Recette> {

    private final ColumnConverter converter;

    public RecetteRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Recette} stored in the database.
     */
    @Override
    public Recette apply(Row row, String prefix) {
        Recette entity = new Recette();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setDateRecette(converter.fromRow(row, prefix + "_date_recette", LocalDate.class));
        entity.setMontant(converter.fromRow(row, prefix + "_montant", BigDecimal.class));
        entity.setTypeRecette(converter.fromRow(row, prefix + "_type_recette", String.class));
        entity.setAnonyme(converter.fromRow(row, prefix + "_anonyme", Boolean.class));
        entity.setMembreNom(converter.fromRow(row, prefix + "_membre_nom", String.class));
        entity.setMotif(converter.fromRow(row, prefix + "_motif", String.class));
        entity.setReferencePiece(converter.fromRow(row, prefix + "_reference_piece", String.class));
        entity.setStatut(converter.fromRow(row, prefix + "_statut", String.class));
        entity.setEntiteFinanciereId(converter.fromRow(row, prefix + "_entite_financiere_id", Long.class));
        entity.setCaisseId(converter.fromRow(row, prefix + "_caisse_id", Long.class));
        entity.setCategorieId(converter.fromRow(row, prefix + "_categorie_id", Long.class));
        return entity;
    }
}
