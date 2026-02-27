package com.gracefinance.gracefinanceapp.repository.rowmapper;

import com.gracefinance.gracefinanceapp.domain.referentiel.Categorie;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Categorie}, with proper type conversions.
 */
@Service
public class CategorieRowMapper implements BiFunction<Row, String, Categorie> {

    private final ColumnConverter converter;

    public CategorieRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Categorie} stored in the database.
     */
    @Override
    public Categorie apply(Row row, String prefix) {
        Categorie entity = new Categorie();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNom(converter.fromRow(row, prefix + "_nom", String.class));
        entity.setCode(converter.fromRow(row, prefix + "_code", String.class));
        entity.setTypeCategorie(converter.fromRow(row, prefix + "_type_categorie", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setActif(converter.fromRow(row, prefix + "_actif", Boolean.class));
        entity.setEntiteFinanciereId(converter.fromRow(row, prefix + "_entite_financiere_id", Long.class));
        return entity;
    }
}
