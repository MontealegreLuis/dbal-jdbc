/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JoinTest {
    private Join join = Join.empty();

    @Test
    public void it_converts_to_sql_a_single_inner_join_statement() {
        join.inner("roles r", "r.id = u.role_id");
        assertEquals("INNER JOIN roles r ON r.id = u.role_id", join.toSQL());
    }

    @Test
    public void it_converts_to_sql_several_inner_join_statements() {
        join
            .inner("user_roles ur", "ur.user_id = u.id")
            .inner("roles r", "r.id = ur.role_id")
        ;
        assertEquals(
            "INNER JOIN user_roles ur ON ur.user_id = u.id INNER JOIN roles r ON r.id = ur.role_id",
            join.toSQL()
        );
    }

    @Test
    public void it_converts_to_sql_a_single_outer_join_statement() {
        join.outer("roles r", "r.id = u.role_id");
        assertEquals("OUTER JOIN roles r ON r.id = u.role_id", join.toSQL());
    }

    @Test
    public void it_converts_to_sql_several_outer_join_statements() {
        join
            .outer("user_roles ur", "ur.user_id = u.id")
            .outer("roles r", "r.id = ur.role_id")
        ;
        assertEquals(
            "OUTER JOIN user_roles ur ON ur.user_id = u.id OUTER JOIN roles r ON r.id = ur.role_id",
            join.toSQL()
        );
    }
}
