package com.gracefinance.gracefinanceapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class LigneEcritureSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("montant", table, columnPrefix + "_montant"));
        columns.add(Column.aliased("sens", table, columnPrefix + "_sens"));
        columns.add(Column.aliased("libelle", table, columnPrefix + "_libelle"));

        columns.add(Column.aliased("ecriture_id", table, columnPrefix + "_ecriture_id"));
        columns.add(Column.aliased("compte_id", table, columnPrefix + "_compte_id"));
        return columns;
    }
}
