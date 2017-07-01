/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.statements;

import com.dbal.jdbc.builders.queries.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Criteria {
    private final Map<String, String[]> request;
    private List<Object> arguments = new ArrayList<>();

    public Criteria(Map<String, String[]> request) {
        this.request = request;
    }

    protected Criteria() {
        request = Collections.emptyMap();
    }

    protected Map<String, String[]> request() {
        return request;
    }

    public abstract void applyTo(Select select);

    public List<Object> arguments() {
        return arguments;
    }
}
