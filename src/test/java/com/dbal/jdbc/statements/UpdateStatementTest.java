/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.statements;

import com.dbal.jdbc.suites.IntegrationSuite;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UpdateStatementTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void it_throws_exception_if_table_does_not_exists() {
        UpdateStatement update = new UpdateStatement(
            IntegrationSuite.connection,
            "non_existing_table"
        );
        update.columns("any");

        exception.expect(SQLError.class);
        update.execute("won't work anyway...");
    }
}