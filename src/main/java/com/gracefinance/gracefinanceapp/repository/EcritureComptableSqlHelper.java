package com.gracefinance.gracefinanceapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class EcritureComptableSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("date_comptable", table, columnPrefix + "_date_comptable"));
        columns.add(Column.aliased("numero_piece", table, columnPrefix + "_numero_piece"));
        columns.add(Column.aliased("libelle", table, columnPrefix + "_libelle"));
        columns.add(Column.aliased("reference_externe", table, columnPrefix + "_reference_externe"));

        return columns;
    }
}
