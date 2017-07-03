/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.util.Properties;

public class ConfigurableDataSource {
    public static MysqlDataSource connectAsUserWith(Properties credentials) {
        MysqlDataSource source = new MysqlDataSource();
        source.setUser(credentials.getProperty("user"));
        source.setPassword(credentials.getProperty("password"));

        return source;
    }

    public static MysqlDataSource connectViaUrlWith(Properties credentials) {
        MysqlDataSource source = new MysqlDataSource();
        source.setURL(credentials.getProperty("url"));
        source.setUser(credentials.getProperty("user"));
        source.setPassword(credentials.getProperty("password"));

        return source;
    }
}
