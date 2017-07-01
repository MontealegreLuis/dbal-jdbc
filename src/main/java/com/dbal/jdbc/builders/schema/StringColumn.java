/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

class StringColumn extends Column {
    private final int length;

    StringColumn(String name) {
        this(name, 256);
    }

    StringColumn(String name, int length) {
        super(name);
        this.length = length;
    }

    @Override
    public String toSQL() {
        return String.format(
            "%s VARCHAR(%d) %s %s",
            name(),
            length,
            isRequired() ? "NOT NULL" : "",
            hasDefaultValue() ? defaultValueToSQL() : ""
        ).trim().replaceAll("( )+", " ");
    }
}
