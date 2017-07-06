/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ColumnsTest {
    @Test
    public void it_converts_to_sql_a_single_column() {
        columns.add("username");
        assertEquals("username", columns.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_using_a_default_value() {
        columns.defaultTo("*");
        assertEquals("*", columns.toSQL());
    }

    @Test
    public void it_converts_to_sql_overriding_the_default_value() {
        columns.defaultTo("*");

        columns.add("username", "password");

        assertEquals("username, password", columns.toSQL());
    }

    @Test
    public void it_converts_to_sql_several_columns() {
        columns.add("username", "password", "email");

        assertEquals("username, password, email", columns.toSQL());
    }

    @Test
    public void it_clears_current_columns_and_append_more() {
        columns.add("username", "password").clear().add("password", "email");

        assertEquals("password, email", columns.toSQL());
    }

    @Test
    public void it_selects_the_count_of_rows()
    {
        columns.count();

        assertThat(columns.toSQL(), is("COUNT(*)"));
    }

    @Test
    public void it_selects_the_count_of_distinct_rows()
    {
        columns.countDistinct("u.id");

        assertThat(columns.toSQL(), is("COUNT(DISTINCT u.id)"));
    }

    private Columns columns = Columns.empty();
}
