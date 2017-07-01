/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc;

import java.util.List;

public interface RowMapper <T> {
    /**
     * Re-create an existing entity
     *
     * @param values The values to be used to create the entity
     * @return A existing entity
     */
    T mapRow(List<Object> values);
}
