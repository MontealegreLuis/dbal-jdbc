/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TableTest {
    private Table table;

    @Before
    public void newTable() {
        table = new Table("users");
    }

    @Test(expected = IllegalStateException.class)
    public void it_fails_to_convert_to_sql_if_no_primary_key_is_provided() {
        table.toSQL();
    }

    @Test
    public void it_converts_to_sql_a_table_to_be_created_if_not_exists() {
        table.increments("id");

        table.ifNotExists();

        assertEquals(
            "CREATE TABLE IF NOT EXISTS users (id INT UNSIGNED NOT NULL AUTO_INCREMENT, PRIMARY KEY (id) ) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;",
            table.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_a_table_with_an_auto_increment_key() {
        table.increments("id");
        assertEquals(
            "CREATE TABLE users (id INT UNSIGNED NOT NULL AUTO_INCREMENT, PRIMARY KEY (id) ) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;",
            table.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_a_table_with_several_columns() {
        table.increments("id");
        table.string("username").makeRequired();
        table.string("password", 300).makeRequired();
        assertEquals(
            "CREATE TABLE users (id INT UNSIGNED NOT NULL AUTO_INCREMENT, username VARCHAR(256) NOT NULL, password VARCHAR(300) NOT NULL, PRIMARY KEY (id) ) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;",
            table.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_table_with_a_composite_key() {
        Column id = table.integer("id").unsigned().makeRequired();
        Column username = table.string("username").makeRequired();
        table.string("password", 300).makeRequired();
        table.primary(id, username);
        assertEquals(
            "CREATE TABLE users (id INT UNSIGNED NOT NULL, username VARCHAR(256) NOT NULL, password VARCHAR(300) NOT NULL, PRIMARY KEY (id, username) ) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;",
            table.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_a_table_with_a_foreign_key() {
        table.increments("id");
        table.string("username").makeRequired();
        table.string("password", 300).makeRequired();
        Column roleId = table.integer("role_id").unsigned().makeRequired();
        table.foreign(roleId).on("roles").references("id");
        assertEquals(
            "CREATE TABLE users (id INT UNSIGNED NOT NULL AUTO_INCREMENT, username VARCHAR(256) NOT NULL, password VARCHAR(300) NOT NULL, role_id INT UNSIGNED NOT NULL, PRIMARY KEY (id) , FOREIGN KEY (role_id) REFERENCES roles(id)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;",
            table.toSQL()
        );
    }
}
