/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import com.dbal.jdbc.builders.HasSQLRepresentation;

class Rows implements HasSQLRepresentation {
    private int limit;
    private int offset;

    private Rows() {
        clear();
    }

    static Rows all() {
        return new Rows();
    }

    void clear() {
        limit = -1;
        offset = -1;
    }

    void countTo(int limit) {
        assertValueIsNotNegative("limit", limit);
        this.limit = limit;
    }

    void startingAt(int offset) {
        assertValueIsNotNegative("offset", offset);
        this.offset = offset;
    }

    private void assertValueIsNotNegative(String label, int value) {
        if (value >= 0) return;

        throw new IllegalArgumentException(String.format("%s cannot be negative", label));
    }

    @Override
    public String toSQL() {
        return String.format("%s %s", limitToSQL(), offsetToSQL()).trim();
    }

    private String offsetToSQL() {
        if (offset < 0) return "";

        return String.format("OFFSET %d", offset);
    }

    private String limitToSQL() {
        if (limit < 0) return "";

        return String.format("LIMIT %d", limit);
    }
}
