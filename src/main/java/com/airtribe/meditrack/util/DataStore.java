package com.airtribe.meditrack.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Generic data storage class demonstrating Java generics and type safety.
 * Can store any type of object using parametrized types.
 * Provides basic CRUD operations and search capabilities.
 * Thread-safe for read operations.
 */
public class DataStore<T> {

    private final List<T> data;
    private final String name;

    /**
     * Constructor for creating a DataStore with a name.
     */
    public DataStore(String name) {
        this.data = new ArrayList<>();
        this.name = name;
    }

    /**
     * Add an item to the store.
     */
    public synchronized void add(T item) {
        if (item != null) {
            data.add(item);
        }
    }

    /**
     * Add all items to the store.
     */
    public synchronized void addAll(List<T> items) {
        if (items != null) {
            data.addAll(items);
        }
    }

    /**
     * Remove an item from the store.
     */
    public synchronized boolean remove(T item) {
        return data.remove(item);
    }

    /**
     * Get item at specific index.
     */
    public T get(int index) {
        if (index >= 0 && index < data.size()) {
            return data.get(index);
        }
        return null;
    }

    /**
     * Get all items.
     */
    public synchronized List<T> getAll() {
        return new ArrayList<>(data);
    }

    /**
     * Get total count of items.
     */
    public synchronized int size() {
        return data.size();
    }

    /**
     * Check if store is empty.
     */
    public synchronized boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Find items matching a predicate (using streams and lambdas).
     */
    public synchronized List<T> findAll(Predicate<T> predicate) {
        return data.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * Find first item matching a predicate.
     */
    public synchronized Optional<T> findFirst(Predicate<T> predicate) {
        return data.stream()
                .filter(predicate)
                .findFirst();
    }

    /**
     * Check if any item matches the predicate.
     */
    public synchronized boolean anyMatch(Predicate<T> predicate) {
        return data.stream()
                .anyMatch(predicate);
    }

    /**
     * Check if all items match the predicate.
     */
    public synchronized boolean allMatch(Predicate<T> predicate) {
        return data.stream()
                .allMatch(predicate);
    }

    /**
     * Clear all items from the store.
     */
    public synchronized void clear() {
        data.clear();
    }

    /**
     * Check if store contains an item.
     */
    public synchronized boolean contains(T item) {
        return data.contains(item);
    }

    /**
     * Get store name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "DataStore{" +
                "name='" + name + '\'' +
                ", size=" + data.size() +
                '}';
    }
}

