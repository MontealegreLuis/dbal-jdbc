/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CountTest {
    @Test
    public void it_replaces_selected_columns_with_count()
    {
        Columns columns = Columns.empty().add("username", "password");
        count.count(columns);

        assertThat(columns.toSQL(), is("COUNT(*)"));
    }

    @Test
    public void it_adds_count_to_empty_columns()
    {
        Columns columns = Columns.empty();
        count.count(columns);

        assertThat(columns.toSQL(), is("COUNT(*)"));
    }

    @Test
    public void it_uses_tables_alias_when_provided()
    {
        Columns columns = Columns.empty();
        count.count(columns, "u"); // This is most likely countDistinct

        assertThat(columns.toSQL(), is("COUNT(DISTINCT u.id)"));
    }


    private Count count = new Count();
}