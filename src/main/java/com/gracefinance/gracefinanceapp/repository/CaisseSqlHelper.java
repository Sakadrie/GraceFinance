package com.gracefinance.gracefinanceapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class CaisseSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nom", table, columnPrefix + "_nom"));
        columns.add(Column.aliased("code", table, columnPrefix + "_code"));
        columns.add(Column.aliased("type", table, columnPrefix + "_type"));
        columns.add(Column.aliased("devise", table, columnPrefix + "_devise"));
        columns.add(Column.aliased("solde", table, columnPrefix + "_solde"));
        columns.add(Column.aliased("actif", table, columnPrefix + "_actif"));

        columns.add(Column.aliased("entite_financiere_id", table, columnPrefix + "_entite_financiere_id"));
        return columns;
    }
}
