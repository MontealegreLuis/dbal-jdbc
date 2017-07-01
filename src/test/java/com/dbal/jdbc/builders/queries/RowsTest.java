/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RowsTest {
    private Rows rows;

    @Before
    public void initRows() {
        rows = Rows.all();
    }

    @Test
    public void it_converts_to_sql_an_object_with_no_offset_and_limit() {
        assertEquals("", rows.toSQL());
    }

    @Test
    public void it_converts_to_sql_an_object_with_5_rows_as_limit() {
        rows.countTo(5);

        assertEquals("LIMIT 5", rows.toSQL());
    }

    @Test
    public void it_converts_sql_an_object_with_10_rows_and_offset_of_30() {
        rows.countTo(10);
        rows.startingAt(30);

        assertEquals("LIMIT 10 OFFSET 30", rows.toSQL());
    }

    @Test(expected = IllegalArgumentException.class)
    public void it_does_not_allow_a_negative_limit() {
        rows.countTo(-3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void it_does_not_allow_a_negative_offset() {
        rows.startingAt(-4);
    }
}
