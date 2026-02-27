package com.gracefinance.gracefinanceapp.repository.security;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class AffectationUtilisateurSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("actif", table, columnPrefix + "_actif"));
        columns.add(Column.aliased("date_affectation", table, columnPrefix + "_date_affectation"));

        columns.add(Column.aliased("user_id", table, columnPrefix + "_user_id"));
        columns.add(Column.aliased("entite_financiere_id", table, columnPrefix + "_entite_financiere_id"));
        return columns;
    }
}
