/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringColumnTest {
    @Test
    public void it_converts_to_sql_a_string_column_with_a_default_length() {
        StringColumn column = new StringColumn("username");
        assertEquals("username VARCHAR(256)", column.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_string_column_with_a_specific_length() {
        StringColumn column = new StringColumn("username", 300);
        assertEquals("username VARCHAR(300)", column.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_required_string_column() {
        StringColumn column = (StringColumn) new StringColumn("username")
            .makeRequired()
        ;
        assertEquals("username VARCHAR(256) NOT NULL", column.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_string_column_with_a_default_value() {
        StringColumn column = (StringColumn) new StringColumn("favorite_language")
            .defaultTo("Java")
        ;
        assertEquals("favorite_language VARCHAR(256) DEFAULT 'Java'", column.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_required_string_column_with_specific_length_and_default_value() {
        StringColumn column = (StringColumn) new StringColumn("favorite_language", 300)
            .makeRequired()
            .defaultTo("Java")
        ;
        assertEquals("favorite_language VARCHAR(300) NOT NULL DEFAULT 'Java'", column.toSQL());
    }
}
