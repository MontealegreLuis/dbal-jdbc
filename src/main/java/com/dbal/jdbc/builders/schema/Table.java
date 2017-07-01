/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import com.dbal.jdbc.builders.HasSQLRepresentation;

import java.util.ArrayList;
import java.util.List;

public class Table implements HasSQLRepresentation {
    private final String name;
    private List<Column> columns;
    private PrimaryKey primaryKey;
    private List<ForeignKey> foreignKeys;
    private boolean ifNotExists = false;

    Table(String name) {
        this.name = name;
        columns = new ArrayList<>();
        foreignKeys = new ArrayList<>();
    }

    public Column string(String name) {
        return string(name, 256);
    }

    public Column string(String name, int length) {
        StringColumn column = new StringColumn(name, length);
        columns.add(column);
        return column;
    }

    public IntColumn integer(String name) {
        IntColumn column = new IntColumn(name);
        columns.add(column);
        return column;
    }

    public IntColumn increments(String name) {
        Column id =  new IntColumn(name)
            .autoIncrement()
            .unsigned()
            .makeRequired()
        ;
        primaryKey = new PrimaryKey(id);
        columns.add(id);
        return (IntColumn) id;
    }

    public ForeignKey foreign(Column column) {
        ForeignKey foreignKey = new ForeignKey(column);
        foreignKeys.add(foreignKey);
        return foreignKey;
    }

    public PrimaryKey primary(Column... columns) {
        primaryKey = PrimaryKey.composed(columns);
        return primaryKey;
    }

    public Table ifNotExists() {
        ifNotExists = true;
        return this;
    }

    @Override
    public String toSQL() {
        assertPrimaryKeyIsPresent();

        return String.format(
            "CREATE TABLE %s %s (%s %s %s) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;",
            ifNotExistsSQL(),
            name,
            columnDefinitions(),
            primaryKey.toSQL(),
            foreignKeysSQL()
        ).replaceAll("( )+", " ");
    }

    private String ifNotExistsSQL() {
        return ifNotExists ? "IF NOT EXISTS" : "";
    }

    private void assertPrimaryKeyIsPresent() {
        if (primaryKey == null) {
            throw new IllegalStateException("Cannot create table without a primary key");
        }
    }

    private String foreignKeysSQL() {
        StringBuilder sql = new StringBuilder();
        foreignKeys.forEach(
            foreignKey -> sql.append(", ").append(foreignKey.toSQL())
        );
        return sql.toString();
    }

    private String columnDefinitions() {
        StringBuilder definition = new StringBuilder();
        columns.forEach(
            column -> definition.append(column.toSQL()).append(", ")
        );
        return definition.toString().trim();
    }
}
