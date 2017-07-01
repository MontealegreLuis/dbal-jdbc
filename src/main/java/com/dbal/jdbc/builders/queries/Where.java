/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import com.dbal.jdbc.builders.HasSQLRepresentation;
import com.dbal.jdbc.builders.queries.WhereExpression.Operator;

import java.util.ArrayList;
import java.util.List;

class Where implements HasSQLRepresentation {
    private final List<WhereExpression> expressions;

    private Where() {
        expressions = new ArrayList<>();
    }

    public static Where empty() {
        return new Where();
    }

    public Where and(String expression) {
        addWhere(expression, Operator.AND);
        return this;
    }

    public Where and(String column, int parametersCount) {
        addWhere(whereInToSQL(column, parametersCount), Operator.AND);
        return this;
    }

    public Where or(String expression) {
        addWhere(expression, Operator.OR);
        return this;
    }

    public Where or(String column, int parametersCount) {
        addWhere(whereInToSQL(column, parametersCount), Operator.OR);
        return this;
    }

    private String whereInToSQL(String column, int parametersCount) {
        return String.format(
            "%s IN %s", column, ParameterPlaceholders.generate(parametersCount)
        );
    }

    private void addWhere(String expression, Operator operator) {
        if (isEmpty()) operator = null;

        expressions.add(WhereExpression.with(expression, operator));
    }

    @Override
    public String toSQL() {
        if (isEmpty()) return "";

        return String.format("WHERE %s", appendExpressions());
    }

    private String appendExpressions() {
        StringBuilder where = new StringBuilder();
        expressions
            .forEach(expression -> where.append(expression.toSQL()).append(" "))
        ;
        return where.toString().replaceAll(" $", "");
    }

    private boolean isEmpty() {
        return expressions.size() == 0;
    }
}
