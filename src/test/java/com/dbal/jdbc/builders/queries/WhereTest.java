/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WhereTest {
    private Where where = Where.empty();

    @Test
    public void it_converts_to_sql_a_single_and_where_expression() {
        where.and("username = ?");
        assertEquals("WHERE username = ?", where.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_single_or_where_expression() {
        where.or("username = ?");
        assertEquals("WHERE username = ?", where.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_single_and_where_in_expression() {
        where.and("username", 3);
        assertEquals("WHERE username IN (?, ?, ?)", where.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_single_or_where_in_expression() {
        where.and("username", 3);
        assertEquals("WHERE username IN (?, ?, ?)", where.toSQL());
    }

    @Test
    public void it_converts_to_sql_several_and_where_expressions() {
        where.and("username = ?").and("password = ?").and("name LIKE ?");
        assertEquals(
            "WHERE username = ? AND password = ? AND name LIKE ?",
            where.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_several_or_where_expressions() {
        where.or("username = ?").or("password = ?").or("name LIKE ?");
        assertEquals(
            "WHERE username = ? OR password = ? OR name LIKE ?",
            where.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_a_combination_of_where_expressions() {
        where.and("username = ?").or("password = ?").and("name IN (?, ?, ?)");
        assertEquals(
            "WHERE username = ? OR password = ? AND name IN (?, ?, ?)",
            where.toSQL()
        );
    }
}
