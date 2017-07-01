/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import com.dbal.jdbc.builders.HasSQLRepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Update implements HasSQLRepresentation {
    private String table;
    private List<String> columns;
    private Where where;

    private Update(String table) {
        this.table = table;
        columns = new ArrayList<>();
        where = Where.empty();
    }

    public Update columns(String... columns) {
        Collections.addAll(this.columns, columns);
        return this;
    }

    public Update where(String expression) {
        where.and(expression);
        return this;
    }

    public Update orWhere(String expression) {
        where.or(expression);
        return this;
    }

    public static Update table(String table) {
        return new Update(table);
    }

    @Override
    public String toSQL() {
        assertNonEmptyColumns();
        return String.format(
            "UPDATE %s SET %s %s",
            table,
            columnsToSQL(),
            where.toSQL()
        ).trim();
    }

    private void assertNonEmptyColumns() {
        if (columns.isEmpty()) {
            throw new IllegalStateException("Cannot determine what columns to update");
        }
    }

    private String columnsToSQL() {
        return String.join(
            ", ",
            columns.stream().map(column -> column + " = ?").toArray(String[]::new)
        );
    }
}
