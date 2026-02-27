package com.gracefinance.gracefinanceapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class RecetteSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("code", table, columnPrefix + "_code"));
        columns.add(Column.aliased("date_recette", table, columnPrefix + "_date_recette"));
        columns.add(Column.aliased("montant", table, columnPrefix + "_montant"));
        columns.add(Column.aliased("type_recette", table, columnPrefix + "_type_recette"));
        columns.add(Column.aliased("anonyme", table, columnPrefix + "_anonyme"));
        columns.add(Column.aliased("membre_nom", table, columnPrefix + "_membre_nom"));
        columns.add(Column.aliased("motif", table, columnPrefix + "_motif"));
        columns.add(Column.aliased("reference_piece", table, columnPrefix + "_reference_piece"));
        columns.add(Column.aliased("statut", table, columnPrefix + "_statut"));

        columns.add(Column.aliased("entite_financiere_id", table, columnPrefix + "_entite_financiere_id"));
        columns.add(Column.aliased("caisse_id", table, columnPrefix + "_caisse_id"));
        columns.add(Column.aliased("categorie_id", table, columnPrefix + "_categorie_id"));
        return columns;
    }
}
