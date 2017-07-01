/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import java.util.Arrays;

/**
 * Generates a sequence of ? placeholders for prepared statements
 *
 * ```
 * ParameterPlaceholders.generate(3); // Will return (?, ?, ?)
 * ```
 *
 * Useful for VALUES and IN clauses
 */
class ParameterPlaceholders {
    static String generate(int count) {
        assertCountIsPositive(count);
        String[] parameters = new String[count];
        Arrays.fill(parameters, "?");
        return "(" + String.join(", ", parameters) + ")";
    }

    private static void assertCountIsPositive(int count) {
        if (count <= 0) {
            throw new IllegalStateException("Cannot generate less than 1 placeholder");
        }
    }
}
