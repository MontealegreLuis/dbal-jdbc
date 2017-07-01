/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import com.dbal.jdbc.builders.HasSQLRepresentation;

abstract public class Column implements HasSQLRepresentation {
    private final String name;
    private boolean required = false;
    private String value;

    Column(String name) {
        this.name = name;
    }

    String name() {
        return name;
    }

    public Column makeRequired() {
        required = true;
        return this;
    }

    public Column defaultTo(String value) {
        this.value = value;
        return this;
    }

    boolean isRequired() {
        return required;
    }

    boolean hasDefaultValue() {
        return value != null;
    }

    protected String defaultValueToSQL() {
        return String.format("DEFAULT '%s'", value);
    }
}
