/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdateTest {
    private Update update = Update.table("users");

    @Test
    public void it_converts_to_sql_an_update_without_a_where() {
        update.columns("username");
        assertEquals("UPDATE users SET username = ?", update.toSQL());
    }

    @Test
    public void it_converts_to_sql_an_update_with_a_where() {
        update.columns("username").where("user_id = ?");
        assertEquals(
            "UPDATE users SET username = ? WHERE user_id = ?",
            update.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_an_update_with_several_where_clauses() {
        update
            .columns("username")
            .where("user_id = ?")
            .orWhere("created_at > ?")
            .where("name LIKE ?")
        ;
        assertEquals(
            "UPDATE users SET username = ? WHERE user_id = ? OR created_at > ? AND name LIKE ?",
            update.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_an_update_with_several_columns_without_a_where() {
        update.columns("username", "password", "role");
        assertEquals(
            "UPDATE users SET username = ?, password = ?, role = ?",
            update.toSQL()
        );
    }
}
