/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.statements;

import com.dbal.jdbc.RowMapper;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class HydratorTest {
    @Test
    public void it_fetches_a_new_entity() {
        RowMapper<Object> mapper = mock(RowMapper.class);
        ArrayList<Object> row = new ArrayList<>();
        row.add(10L);
        row.add("luis");
        row.add("changeme");

        Hydrator<Object> hydrator = new Hydrator<>(
            10L,
            new Object[]{"luis", "changeme"},
            mapper
        );

        hydrator.fetch();

        verify(mapper).mapRow(row);
    }

    @Test
    public void it_fecthes_a_scalar_numeric_value() throws SQLException {
        long expectedNumber = 100L;
        RowMapper<Object> mapper = mock(RowMapper.class);

        // Return a single column result set
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(metaData.getColumnCount()).thenReturn(1);

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getMetaData()).thenReturn(metaData);
        // The result set only contains a row
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getObject(1)).thenReturn(expectedNumber);

        Hydrator<Object> hydrator = new Hydrator<>(
            resultSet,
            mapper
        );

        long actualNumber = hydrator.fetchLong();

        assertEquals(expectedNumber, actualNumber);
    }

    @Test
    public void it_fetches_several_entities() throws SQLException {
        ArrayList<Object> firstEntity = new ArrayList<>();
        firstEntity.addAll(Arrays.asList(3L, "luis", "changeme"));

        ArrayList<Object> secondEntity = new ArrayList<>();
        secondEntity.addAll(Arrays.asList(4L, "mario", "password"));

        ArrayList<Object> expectedEntities = new ArrayList<>();
        expectedEntities.add(firstEntity);
        expectedEntities.add(secondEntity);

        RowMapper<Object> mapper = mock(RowMapper.class);

        // Return a single column result set
        ResultSetMetaData metaData = mock(ResultSetMetaData.class);
        when(metaData.getColumnCount()).thenReturn(3);

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getMetaData()).thenReturn(metaData);
        // The result contains 2 rows
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getObject(1))
            .thenReturn(3L)
            .thenReturn(4L)
        ;
        when(resultSet.getObject(2))
            .thenReturn("luis")
            .thenReturn("mario")
        ;
        when(resultSet.getObject(3))
            .thenReturn("changeme")
            .thenReturn("password")
        ;

        Hydrator<Object> hydrator = new Hydrator<>(
            resultSet,
            mapper
        );

        hydrator.fetchAll();

        verify(mapper).mapRow(firstEntity);
        verify(mapper).mapRow(secondEntity);
    }
}
