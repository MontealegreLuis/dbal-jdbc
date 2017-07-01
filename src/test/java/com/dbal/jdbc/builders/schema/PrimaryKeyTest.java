/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PrimaryKeyTest {
    @Test
    public void it_converts_to_sql_a_primary_key() {
        PrimaryKey key = new PrimaryKey(new IntColumn("user_id"));
        assertEquals("PRIMARY KEY (user_id)", key.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_composite_key() {
        PrimaryKey key = PrimaryKey.composed(
            new IntColumn("user_id"),
            new IntColumn("post_id")
        );
        assertEquals("PRIMARY KEY (user_id, post_id)", key.toSQL());
    }
}
