/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

public class IntColumn extends Column {
    private boolean autoIncrements = false;
    private boolean unsigned = false;

    IntColumn(String name) {
        super(name);
    }

    IntColumn autoIncrement() {
        autoIncrements = true;
        return this;
    }

    public IntColumn unsigned() {
        unsigned = true;
        return this;
    }

    private boolean isUnsigned() {
        return unsigned;
    }

    private boolean isAutoIncrementing()
    {
        return autoIncrements;
    }

    @Override
    public String toSQL() {
        return String.format(
            "%s INT %s %s %s %s",
            name(),
            isUnsigned() ? "UNSIGNED" : "",
            isRequired() ? "NOT NULL" : "",
            hasDefaultValue() ? defaultValueToSQL() : "",
            isAutoIncrementing() ? "AUTO_INCREMENT" : ""
        ).trim().replaceAll("( )+", " ");
    }
}
