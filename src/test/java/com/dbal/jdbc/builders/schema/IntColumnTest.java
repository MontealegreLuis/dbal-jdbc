/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntColumnTest {

    private IntColumn column;

    @Before
    public void newColumn() {
        column = new IntColumn("user_id");
    }

    @Test
    public void it_converts_to_sql_an_integer_column() {
        assertEquals("user_id INT", column.toSQL());
    }


    @Test
    public void it_converts_to_sql_required_integer_column() {
        column = (IntColumn) column.makeRequired();
        assertEquals("user_id INT NOT NULL", column.toSQL());
    }

    @Test
    public void it_converts_to_sql_an_unsigned_integer_column() {
        column.unsigned();
        assertEquals("user_id INT UNSIGNED", column.toSQL());
    }

    @Test
    public void it_converts_to_sql_an_autoincrementing_integer_column() {
        column.autoIncrement();
        assertEquals("user_id INT AUTO_INCREMENT", column.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_required_unsigned_autoincrementing_integer_column() {
        column.unsigned().autoIncrement().makeRequired();
        assertEquals("user_id INT UNSIGNED NOT NULL AUTO_INCREMENT", column.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_required_unsigned_integer_column_with_a_default() {
        column.unsigned().makeRequired().defaultTo("1");
        assertEquals("user_id INT UNSIGNED NOT NULL DEFAULT '1'", column.toSQL());
    }
}
