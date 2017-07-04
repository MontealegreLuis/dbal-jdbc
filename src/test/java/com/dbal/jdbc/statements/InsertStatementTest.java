/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.statements;

import com.dbal.jdbc.RowMapper;
import com.dbal.jdbc.suites.IntegrationSuite;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.mock;

public class InsertStatementTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void it_throws_exception_if_table_does_not_exists()
    {
        InsertStatement<Object> insert = new InsertStatement<>(
            IntegrationSuite.connection,
            "non_existing_table",
            mock(RowMapper.class)
        );
        insert.columns("test_1", "test_2");

        exception.expect(SQLError.class);
        insert.execute("any_1", "any_2");
    }
}