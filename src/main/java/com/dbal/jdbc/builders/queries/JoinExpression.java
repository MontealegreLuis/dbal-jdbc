/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import com.dbal.jdbc.builders.HasSQLRepresentation;

class JoinExpression implements HasSQLRepresentation {
    private final String table;
    private final String on;
    private final Type type;

    enum Type {INNER, OUTER};

    JoinExpression(String table, String on, Type type) {
        this.table = table;
        this.on = on;
        this.type = type;
    }

    public String toSQL() {
        return String.format(
            "%s JOIN %s ON %s",
            type.toString(),
            table,
            on
        );
    }
}
