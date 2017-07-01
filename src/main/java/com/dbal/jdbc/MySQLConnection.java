package com.dbal.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private String database = null;
    private Connection connection;
    private String username;
    private String password;

    public MySQLConnection(String username, String password, String database) {
        this(username, password);
        this.database = database;
    }

    public MySQLConnection(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection connect() throws ClassNotFoundException, SQLException {
        if (connection != null)  return connection;

        Class.forName("com.mysql.cj.jdbc.Driver");

        connection = DriverManager.getConnection(
            connectionURL(),
            username,
            password
        );
        return connection;
    }

    private String connectionURL() {
        return String.format(
            "jdbc:mysql://%s/%s?useLegacyDatetimeCode=false&serverTimezone=UTC",
            "localhost",
            database == null ? "" : database
        );
    }
}
