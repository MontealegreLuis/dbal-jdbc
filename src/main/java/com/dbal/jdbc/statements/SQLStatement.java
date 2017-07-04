/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.statements;

import java.sql.Connection;

public class SQLStatement {
    protected final Connection connection;

    SQLStatement(Connection connection) {
        this.connection = connection;
    }
}
