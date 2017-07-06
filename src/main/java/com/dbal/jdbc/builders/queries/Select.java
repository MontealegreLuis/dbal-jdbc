/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import com.dbal.jdbc.builders.HasSQLRepresentation;

import java.util.HashMap;
import java.util.Map;

public class Select implements HasSQLRepresentation {
    private Map<String, HasSQLRepresentation> parts;

    private Select(From from) {
        parts = new HashMap<>();
        parts.put("from", new From(from));
        parts.put("columns", Columns.empty().defaultTo("*"));
        parts.put("where", Where.empty());
        parts.put("join", Join.empty());
        parts.put("rows", Rows.all());
    }

    /**
     * Create a 'cloned' copy of the given select statement
     *
     * @param select Statement to be copied
     */
    public Select(Select select) {
        parts = new HashMap<>();
        parts.put("from", new From(((From) select.parts.get("from"))));
        parts.put("columns", new Columns(((Columns) select.parts.get("columns"))));
        parts.put("where", select.parts.get("where"));
        parts.put("join", select.parts.get("join"));
        parts.put("rows", select.parts.get("rows"));
    }

    public static Select from(String table) {
        return new Select(From.table(table));
    }

    public static Select from(String table, String alias) {
        return new Select(From.tableWithAlias(table, alias));
    }

    /**
     * Add alias to original table name in order to remove ambiguity, possibly due to
     * a criteria object trying to add a join. For instance:
     *
     * `Select.from("users").addTableAlias("u").toSQL()`
     *
     * will result in:
     *
     * `SELECT * FROM users u`
     */
    public Select addTableAlias(String alias) {
        ((From) parts.get("from")).addAlias(alias);
        return this;
    }

    public Select addColumns(String... columns) {
        ((Columns) parts.get("columns")).add(columns);
        return this;
    }

    public Select columns(String... columns) {
        ((Columns) parts.get("columns")).clear().add(columns);
        return this;
    }

    public Select count() {
        ((Rows) parts.get("rows")).clear();
        ((Columns) parts.get("columns")).count();
        return this;
    }

    /**
     * Parameter `column` allows yu to specify a specific column to use for your count. For instance
     *
     * ```
     * u.id
     * users.username
     * users.*
     * ```
     */
    public Select countDistinct(String column) {
        ((Rows) parts.get("rows")).clear();
        ((Columns) parts.get("columns")).countDistinct(column);
        return this;
    }

    public Select where(String expression) {
        ((Where) parts.get("where")).and(expression);
        return this;
    }

    /**
     * Generates WHERE IN clause.
     *
     * `select.where("name", 3)`
     *
     * will generate
     *
     * `AND name IN (?, ?, ?)`
     *
     * @param column Column name
     * @param parametersCount Count of `?` parameters in the `IN` clause
     */
    public Select where(String column, int parametersCount) {
        ((Where) parts.get("where")).and(column, parametersCount);
        return this;
    }

    public Select orWhere(String expression) {
        ((Where) parts.get("where")).or(expression);
        return this;
    }

    /**
     * Generates WHERE IN clause.
     *
     * `select.where("name", 3)`
     *
     * will generate
     *
     * `OR name IN (?, ?, ?)`
     *
     * @param column Column name
     * @param parametersCount Count of `?` parameters in the `IN` clause
     */
    public Select orWhere(String column, int parametersCount) {
        ((Where) parts.get("where")).or(column, parametersCount);
        return this;
    }

    public Select join(String table, String on) {
        ((Join) parts.get("join")).inner(table, on);
        return this;
    }

    public Select outerJoin(String table, String on) {
        ((Join) parts.get("join")).outer(table, on);
        return this;
    }

    public Select limit(int limit) {
        ((Rows) parts.get("rows")).countTo(limit);
        return this;
    }

    public Select offset(int offset) {
        ((Rows) parts.get("rows")).startingAt(offset);
        return this;
    }

    @Override
    public String toSQL() {
        return String.format(
            "SELECT %s FROM %s %s %s %s",
            parts.get("columns").toSQL(),
            parts.get("from").toSQL(),
            parts.get("join").toSQL(),
            parts.get("where").toSQL(),
            parts.get("rows").toSQL()
        ).trim().replaceAll("( )+", " ");
    }
}
