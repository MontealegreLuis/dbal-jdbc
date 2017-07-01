/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import com.dbal.jdbc.builders.HasSQLRepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Columns implements HasSQLRepresentation {
    private List<String> columns;
    private String defaultColumn;

    private Columns() {
        columns = new ArrayList<>();
    }

    Columns(Columns columns) {
        this.columns = new ArrayList<>(columns.columns);
        defaultColumn = columns.defaultColumn;
    }

    public static Columns empty() {
        return new Columns();
    }

    Columns defaultTo(String column) {
        defaultColumn = column;
        return this;
    }

    public Columns add(String... columns) {
        Collections.addAll(this.columns, columns);
        return this;
    }

    int size() {
        return columns.size();
    }

    Columns clear() {
        columns.clear();
        return this;
    }

    @Override
    public String toSQL() {
        if (columns.isEmpty()) columns.add(defaultColumn);

        return String.join(", ", columns.toArray(new String[]{}));
    }
}
