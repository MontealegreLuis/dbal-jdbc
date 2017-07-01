/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import com.dbal.jdbc.builders.HasSQLRepresentation;

class WhereExpression implements HasSQLRepresentation {
    private final String expression;
    private final Operator operator;

    enum Operator {AND, OR};

    public static WhereExpression with(String expression, Operator operator) {
        return new WhereExpression(expression, operator);
    }

    private WhereExpression(String expression, Operator operator) {
        this.expression = expression;
        this.operator = operator;
    }

    @Override
    public String toSQL() {
        return operator() + expression;
    }

    private String operator() {
        return operator != null ? operator.toString() + " " : "";
    }
}
