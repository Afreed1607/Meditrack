package com.airtribe.meditrack.interfaces;

import java.util.List;

/**
 * Generic interface for searchable entities.
 * Any class implementing this interface must support searching by various criteria.
 * Uses generics to maintain type safety across different entity types.
 */
public interface Searchable<T> {

    /**
     * Search for entities by a specific field and value.
     *
     * @param fieldName The name of the field to search in
     * @param value The value to search for
     * @return List of matching entities
     */
    List<T> searchByField(String fieldName, String value);

    /**
     * Search for entities by ID.
     *
     * @param id The ID to search for
     * @return The matching entity, or null if not found
     */
    T findById(String id);

    /**
     * Get all entities.
     *
     * @return List of all entities
     */
    List<T> getAll();

    /**
     * Get total count of entities.
     *
     * @return Number of entities stored
     */
    int count();
}

