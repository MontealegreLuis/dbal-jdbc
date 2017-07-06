/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

public class Count {
    public void count(Columns columns) {
        columns.clear();
        columns.add("COUNT(*)");
    }

    public void count(Columns columns, String alias) {
        columns.clear();
        columns.add(String.format("COUNT(DISTINCT %s.id)", alias));
    }
}
