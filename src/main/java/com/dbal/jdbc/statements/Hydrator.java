/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.statements;

import com.dbal.jdbc.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hydrator<T> {
    private List<List<Object>> rows;
    private RowMapper<T> mapper;

    private Hydrator(List<List<Object>> rows, RowMapper<T> mapper) {
        this.rows = rows;
        this.mapper = mapper;
    }

    Hydrator(ResultSet resultSet, RowMapper<T> mapper) throws SQLException {
        this(Hydrator.populateValues(resultSet), mapper);
    }

    Hydrator(long id, Object[] insertedValues, RowMapper<T> mapper) {
        this.mapper = mapper;
        this.rows = new ArrayList<>();
        ArrayList<Object> row = new ArrayList<>();
        row.add(id);
        row.addAll(Arrays.asList(insertedValues));
        rows.add(row);
    }

    public T fetch() {
        if (rows.isEmpty()) return null;

        return mapper.mapRow(rows.get(0));
    }

    public List<T> fetchAll() {
        List<T> entities = new ArrayList<>();

        rows.forEach(row -> entities.add(mapper.mapRow(row)));

        return entities;
    }

    public long fetchLong() {
        return (long) rows.get(0).get(0);
    }

    private static List<List<Object>> populateValues(
        ResultSet resultSet
    ) throws SQLException {
        List<List<Object>> rows = new ArrayList<>();
        int columnCount = resultSet.getMetaData().getColumnCount();

        while (resultSet.next()) {
            ArrayList<Object> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(resultSet.getObject(i));
            }
            rows.add(row);
        }
        return rows;
    }
}
