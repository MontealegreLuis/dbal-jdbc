/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import org.junit.Test;

import static com.dbal.jdbc.builders.queries.WhereExpression.Operator;
import static org.junit.Assert.assertEquals;

public class WhereExpressionTest {
    @Test
    public void it_converts_to_sql_an_and_expression() {
        WhereExpression where = WhereExpression.with(
            "u.username = ?",
            Operator.AND
        );
        assertEquals("AND u.username = ?", where.toSQL());
    }

    @Test
    public void it_converts_to_sql_an_or_expression() {
        WhereExpression where = WhereExpression.with(
            "u.password = ?",
            Operator.OR
        );
        assertEquals("OR u.password = ?", where.toSQL());
    }

    @Test
    public void it_ignores_boolean_operator_from_first_expression() {
        WhereExpression where = WhereExpression.with("u.name LIKE ?", null);
        assertEquals("u.name LIKE ?", where.toSQL());
    }
}
