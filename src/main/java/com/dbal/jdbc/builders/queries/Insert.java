/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import com.dbal.jdbc.builders.HasSQLRepresentation;

public class Insert implements HasSQLRepresentation {
    private String table;
    private int valuesCount;
    private Columns columns;

    private Insert(String table) {
        this.table = table;
        columns = Columns.empty();
    }

    public static Insert into(String table) {
        return new Insert(table);
    }

    public Insert values(int valuesCount) {
        this.valuesCount = valuesCount;
        return this;
    }

    public Insert columns(String... columns) {
        this.columns.add(columns);
        return this;
    }

    public String toSQL() {
        assertValuesArePresent();
        assertColumnsAndValuesMatch();
        return String.format(
            "INSERT INTO %s %s %s",
            table,
            columnsToString(),
            valuesToString()
        ).replaceAll("( )+", " ");
    }

    private void assertColumnsAndValuesMatch() {
        if (columns.size() > 0 && valuesCount() != columns.size()) {
            throw new IllegalStateException("Columns and values count do not match");
        }
    }

    private void assertValuesArePresent() {
        if (valuesCount() == 0) {
            throw new IllegalStateException("Cannot build INSERT without values");
        }
    }

    private String columnsToString() {
        if (columns.size() == 0) return "";

        return "(" + columns.toSQL() + ")";
    }

    private String valuesToString() {
        return "VALUES " + ParameterPlaceholders.generate(valuesCount());
    }

    private int valuesCount() {
        if (valuesCount == 0) {
            valuesCount = columns.size();
        }
        return valuesCount;
    }
}
