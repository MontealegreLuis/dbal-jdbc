/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParameterPlaceholdersTest {
    @Test
    public void it_converts_to_sql_a_single_parameter() {
        assertEquals("(?)", ParameterPlaceholders.generate(1));
    }

    @Test
    public void it_converts_to_sql_several_parameter_placeholders() {
        assertEquals("(?, ?, ?, ?)", ParameterPlaceholders.generate(4));
    }

    @Test(expected = IllegalStateException.class)
    public void it_does_not_generate_placeholders_for_non_positive_numeric_values() {
        ParameterPlaceholders.generate(0);
    }
}
