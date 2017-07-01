/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ColumnsTest {
    @Test
    public void it_converts_to_sql_a_single_column() {
        Columns columns = Columns.empty();
        columns.add("username");
        assertEquals("username", columns.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_using_a_default_value() {
        Columns columns = Columns.empty().defaultTo("*");
        assertEquals("*", columns.toSQL());
    }

    @Test
    public void it_converts_to_sql_overriding_the_default_value() {
        Columns columns = Columns.empty().defaultTo("*");
        columns.add("username", "password");
        assertEquals("username, password", columns.toSQL());
    }

    @Test
    public void it_converts_to_sql_several_columns() {
        Columns columns = Columns.empty();
        columns.add("username", "password", "email");
        assertEquals("username, password, email", columns.toSQL());
    }

    @Test
    public void it_clears_current_columns_and_append_more() {
        Columns columns = Columns.empty();
        columns.add("username", "password").clear().add("password", "email");
        assertEquals("password, email", columns.toSQL());
    }
}
