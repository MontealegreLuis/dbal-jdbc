/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.*;

public class SchemaBuilderTest {

    @Test
    public void it_converts_to_sql_a_single_table() throws SQLException {
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        SchemaBuilder builder = new SchemaBuilder(connection);
        when(connection.createStatement()).thenReturn(statement);

        Table users = builder.table("users");
        users.increments("id");
        builder.build();

        verify(statement).executeUpdate(users.toSQL());
    }

    @Test
    public void it_converts_to_sql_a_several_tables() throws SQLException {
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        SchemaBuilder builder = new SchemaBuilder(connection);
        when(connection.createStatement()).thenReturn(statement);

        Table users = builder.table("users");
        users.increments("id");
        Table roles = builder.table("roles");
        roles.increments("id");
        Table usersRoles = builder.table("users_roles");
        usersRoles.increments("id");
        builder.build();

        verify(statement).executeUpdate(users.toSQL());
        verify(statement).executeUpdate(roles.toSQL());
        verify(statement).executeUpdate(usersRoles.toSQL());
    }
}
