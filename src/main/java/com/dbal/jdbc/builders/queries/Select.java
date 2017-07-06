/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import com.dbal.jdbc.builders.HasSQLRepresentation;

public class Select implements HasSQLRepresentation {
    private Columns columns;
    private From from;
    private Where where;
    private Join join;
    private Rows rows;
    private Count count;

    private Select(From from) {
        this.from = from;
        columns = Columns.empty().defaultTo("*");
        where = Where.empty();
        join = Join.empty();
        rows = Rows.all();
        count = null;
    }

    /**
     * Create a 'cloned' copy of the given select statement
     *
     * @param select Statement to be copied
     */
    public Select(Select select) {
        from = new From(select.from);
        columns = new Columns(select.columns);
        where = select.where;
        join = select.join;
        rows = select.rows;
        count = select.count;
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
     *
     * @param alias
     * @return Select
     */
    public Select addTableAlias(String alias) {
        from.addAlias(alias);
        return this;
    }

    public Select addColumns(String... columns) {
        this.columns.add(columns);
        return this;
    }

    public Select columns(String... columns) {
        this.columns.clear().add(columns);
        return this;
    }

    public Select count() {
        count = new Count();
        return this;
    }

    private String alias() {
        return from.alias();
    }

    public Select where(String expression) {
        where.and(expression);
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
        where.and(column, parametersCount);
        return this;
    }

    public Select orWhere(String expression) {
        where.or(expression);
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
     * @return
     */
    public Select orWhere(String column, int parametersCount) {
        where.or(column, parametersCount);
        return this;
    }

    public Select join(String table, String on) {
        join.inner(table, on);
        return this;
    }

    public Select outerJoin(String table, String on) {
        join.outer(table, on);
        return this;
    }

    public Select limit(int limit) {
        rows.countTo(limit);
        return this;
    }

    public Select offset(int offset) {
        rows.startingAt(offset);
        return this;
    }

    @Override
    public String toSQL() {
        return String.format(
            "SELECT %s FROM %s %s %s %s",
            columnsToSQL(),
            from.toSQL(),
            join.toSQL(),
            where.toSQL(),
            rows.toSQL()
        ).trim().replaceAll("( )+", " ");
    }

    private String columnsToSQL() {
        if (count != null) determineCount();
        return columns.toSQL();
    }

    private void determineCount() {
        columns.clear();
        rows.clear();
        if (join.isEmpty()) count.count(columns);
        else count.count(columns, alias());
    }
}
