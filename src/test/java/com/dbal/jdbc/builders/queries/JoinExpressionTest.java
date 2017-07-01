/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import org.junit.Test;

import static com.dbal.jdbc.builders.queries.JoinExpression.*;
import static org.junit.Assert.assertEquals;

public class JoinExpressionTest {
    @Test
    public void it_converts_to_sql_an_inner_join() {
        JoinExpression join = new JoinExpression("roles r", "u.role_id = r.id", Type.INNER);
        assertEquals("INNER JOIN roles r ON u.role_id = r.id", join.toSQL());
    }

    @Test
    public void it_converts_to_sql_an_outer_join_statement() {
        JoinExpression join = new JoinExpression("roles r", "u.role_id = r.id", Type.OUTER);
        assertEquals("OUTER JOIN roles r ON u.role_id = r.id", join.toSQL());
    }
}
