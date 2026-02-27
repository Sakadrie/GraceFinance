package com.gracefinance.gracefinanceapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class TransfertSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("code", table, columnPrefix + "_code"));
        columns.add(Column.aliased("date_transfert", table, columnPrefix + "_date_transfert"));
        columns.add(Column.aliased("montant", table, columnPrefix + "_montant"));
        columns.add(Column.aliased("motif", table, columnPrefix + "_motif"));
        columns.add(Column.aliased("type_transfert", table, columnPrefix + "_type_transfert"));
        columns.add(Column.aliased("statut", table, columnPrefix + "_statut"));
        columns.add(Column.aliased("valider_par", table, columnPrefix + "_valider_par"));
        columns.add(Column.aliased("date_validation", table, columnPrefix + "_date_validation"));

        columns.add(Column.aliased("entite_financiere_source_id", table, columnPrefix + "_entite_financiere_source_id"));
        columns.add(Column.aliased("caisse_source_id", table, columnPrefix + "_caisse_source_id"));
        columns.add(Column.aliased("caisse_destination_id", table, columnPrefix + "_caisse_destination_id"));
        return columns;
    }
}
