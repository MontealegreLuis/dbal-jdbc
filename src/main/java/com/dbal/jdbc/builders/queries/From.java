/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import com.dbal.jdbc.builders.HasSQLRepresentation;

class From implements HasSQLRepresentation {
    private String table;
    private String alias;

    private From(String table, String alias) {
        assertValidTableName(table);
        this.table = table;
        this.alias = alias;
    }

    private void assertValidTableName(String table) {
        if (table.trim().length() > 0 && table.indexOf(' ') == -1) return;

        throw new IllegalArgumentException("Invalid table name given");
    }

    From(From from) {
        table = from.table;
        alias = from.alias;
    }

    static From table(String table) {
        return new From(table, null);
    }

    static From tableWithAlias(String table, String alias) {
        return new From(table, alias);
    }

    void addAlias(String alias) {
        this.alias = alias;
    }

    String alias() {
        if (alias == null) return Character.toString(table.charAt(0)).toLowerCase();

        return alias;
    }

    public String toSQL() {
        return table + ((alias == null) ? "" : " " + alias);
    }
}
