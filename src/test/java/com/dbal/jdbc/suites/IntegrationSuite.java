/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.suites;

import com.dbal.jdbc.ConfigurableDataSource;
import com.dbal.jdbc.DatabaseTest;
import com.dbal.jdbc.statements.InsertStatementTest;
import com.dbal.jdbc.statements.SelectStatementTest;
import com.dbal.jdbc.statements.UpdateStatementTest;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    DatabaseTest.class,
    InsertStatementTest.class,
    SelectStatementTest.class,
    UpdateStatementTest.class
})
public class IntegrationSuite {
    public static Connection connection;
    public static Properties credentials;

    @ClassRule
    public static ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            credentials = new Properties();
            credentials.load(new FileInputStream("src/test/resources/tests.properties"));
            MysqlDataSource source = ConfigurableDataSource.connectAsUserWith(credentials);
            connection = source.getConnection();
        }

        @Override
        protected void after() {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    };
}
