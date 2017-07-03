/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class DatabaseTest {
    @Test
    public void it_creates_a_new_database() throws Exception {
        database.create(databaseName);

        assertThat(databaseExists(), is("yes"));
    }

    @Test
    public void it_creates_database_if_not_exists() {
        try {
            database.create(databaseName);
            database.createIfNotExists(databaseName);
            assertThat(databaseExists(), is("yes"));
        } catch (SQLException e) {
            fail(String.format("Second attempt to create database %s shouldn't have failed", database));
        }
    }

    @Test
    public void it_drops_an_existing_database() throws Exception {
        database.create(databaseName);
        database.drop(databaseName);
        assertThat(databaseExists(), is("no"));
    }

    @Test
    public void it_drops_a_database_if_exists() throws Exception
    {
        try {
            database.dropIfExists(databaseName);
        } catch (SQLException e) {
            fail(String.format(
                "Dropping database %s shouldn't be a problem if it does not exist", databaseName
            ));
        }
        database.create(databaseName);
        database.dropIfExists(databaseName);
        assertThat(databaseExists(), is("no"));
    }


    @Before
    public void createConnection() throws IOException, SQLException {
        Properties credentials = new Properties();
        credentials.load(new FileInputStream("src/test/resources/tests.properties"));
        MysqlDataSource source = ConfigurableDataSource.connectAsUserWith(credentials);
        databaseName = credentials.getProperty("database");
        connection = source.getConnection();
        cleanupDatabase();
        database = new Database(connection);
    }

    private void cleanupDatabase() throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute("DROP DATABASE IF EXISTS " + databaseName);
    }

    private String databaseExists() throws SQLException {
        Statement statement = connection.createStatement();
        String sql = String.format("SELECT IF(EXISTS (SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '%s'), 'yes','no')", databaseName);
        ResultSet set = statement.executeQuery(sql);
        set.next();

        return set.getString(1);
    }

    private Database database;
    private Connection connection;
    private String databaseName;
}