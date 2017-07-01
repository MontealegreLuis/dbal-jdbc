/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.statements;

import com.dbal.jdbc.builders.HasSQLRepresentation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class SQLStatement {
    protected final Connection connection;

    SQLStatement(Connection connection) {
        this.connection = connection;
    }

    public static RuntimeException queryException(
        HasSQLRepresentation statement,
        Object[] parameters,
        SQLException cause
    ) {
        return new RuntimeException(
            String.format(
                "Cannot execute statement %s with parameters %s",
                statement.toSQL(),
                Arrays.toString(parameters)
            ),
            cause
        );
    }
}
