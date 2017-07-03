/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private final Connection connection;

    public Database(Connection connection) {
        this.connection = connection;
    }

    public void create(String database) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("CREATE DATABASE %s", database));
        }
    }

    public void createIfNotExists(String database) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("CREATE DATABASE IF NOT EXISTS %s", database));
        }
    }

    public void drop(String database) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("DROP DATABASE %s", database));
        }
    }

    public void dropIfExists(String database) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("DROP DATABASE IF EXISTS %s", database));
        }
    }

    public void use(String database) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(String.format("USE %s", database));
        }
    }

    public void importSQLFile(String path) throws IOException, SQLException {
        StringBuilder lines = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            while ((line = reader.readLine()) != null) lines.append(line).append(" ");
        }

        try (Statement statement = connection.createStatement()) {
            String[] queries = lines.toString().split(";");
            for (String query : queries) if (!query.trim().isEmpty()) statement.executeUpdate(query);
        }
    }
}
