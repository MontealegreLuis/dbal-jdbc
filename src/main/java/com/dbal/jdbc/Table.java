/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc;

import com.dbal.jdbc.builders.HasSQLRepresentation;
import com.dbal.jdbc.statements.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract public class Table<T> {
    private final Connection connection;

    public Table(Connection connection) {
        this.connection = connection;
    }

    protected InsertStatement<T> createInsert(String... columns){
        return new InsertStatement<>(connection, table(), mapper()).columns(columns);
    }

    protected UpdateStatement createUpdate(String... columns) {
        return new UpdateStatement(connection, table()).columns(columns);
    }

    protected SelectStatement<T> select(String... columns) {
        return new SelectStatement<>(connection, table(), mapper()).select(columns);
    }

    protected void executeUpdate(
        HasSQLRepresentation insertOrUpdate,
        Object... parameters
    ) {
        try (PreparedStatement statement = connection.prepareStatement(
            insertOrUpdate.toSQL()
        )) {
            QueryParameters.bind(statement, parameters);
            statement.execute();
        } catch (SQLException e) {
            throw SQLError.producedBy(insertOrUpdate, parameters, e);
        }
    }

    abstract protected String table();

    abstract protected RowMapper<T> mapper();
}
