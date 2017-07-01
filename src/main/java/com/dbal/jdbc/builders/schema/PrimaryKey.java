/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import com.dbal.jdbc.builders.HasSQLRepresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PrimaryKey implements HasSQLRepresentation {
    private List<Column> columns = new ArrayList<>();

    private PrimaryKey(List<Column> columns) {
        this.columns = columns;
    }

    PrimaryKey(Column column) {
        columns.add(column);
    }

    static PrimaryKey composed(Column ...columns) {
        return new PrimaryKey(new ArrayList<>(Arrays.asList(columns)));
    }

    @Override
    public String toSQL() {
        return String.format("PRIMARY KEY (%s)", columnNames());
    }

    private String columnNames() {
        StringBuilder names = new StringBuilder();
        columns.forEach(column -> names.append(column.name()).append(", "));
        return names.toString().replaceAll(", $", "");
    }
}
