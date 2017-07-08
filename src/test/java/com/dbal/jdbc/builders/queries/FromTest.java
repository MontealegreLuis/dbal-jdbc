/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FromTest {
    private From from;

    @Test
    public void it_converts_to_sql_a_table_without_alias() {
        from = From.empty().table("users");

        assertEquals("users", from.toSQL());
    }

    @Test(expected = IllegalArgumentException.class)
    public void it_does_not_create_a_table_with_an_empty_name() {
        from = From.empty().table("    ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void it_does_not_create_a_table_with_alias_as_part_of_the_table_name() {
        from = From.empty().table("users u");
    }

    @Test
    public void it_converts_to_sql_a_table_with_an_alias() {
        from = From.empty().tableWithAlias("users", "u");

        assertEquals("users u", from.toSQL());
    }

    @Test
    public void it_adds_an_alias_to_an_existing_table() {
        from = From.empty().table("users");

        from.addAlias("u");

        assertEquals("users u", from.toSQL());
    }

    @Test
    public void it_can_create_a_copy_from_existing_object() {
        from = From.empty().tableWithAlias("users", "u");
        From copy = new From(this.from);

        copy.addAlias("p");

        assertEquals("users u", from.toSQL());
        assertEquals("users p", copy.toSQL());
    }
}
