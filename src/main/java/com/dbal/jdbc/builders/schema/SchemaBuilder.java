/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SchemaBuilder {
    private final Connection connection;
    private List<Table> tables;

    public SchemaBuilder(Connection connection) {
        this.connection = connection;
        tables = new ArrayList<>();
    }

    public Table table(String name) {
        Table table = new Table(name);
        tables.add(table);
        return table;
    }

    public void build() throws SQLException {
        Statement statement = connection.createStatement();
        for (Table table : tables)  statement.executeUpdate(table.toSQL());
    }
}
