/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.statements;

import com.dbal.jdbc.builders.queries.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompoundCriteria extends Criteria {
    private final List<Criteria> criteria = new ArrayList<>();

    public CompoundCriteria(Criteria... criteria) {
        Collections.addAll(this.criteria, criteria);
    }

    @Override
    public void applyTo(Select select) {
        criteria.forEach(criteria -> {
            criteria.applyTo(select);
            arguments().addAll(criteria.arguments());
        });
    }
}
