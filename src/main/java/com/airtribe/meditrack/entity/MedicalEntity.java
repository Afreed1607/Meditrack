package com.airtribe.meditrack.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Base class for all medical entities in the system.
 * Provides common fields like ID and timestamps for tracking creation and updates.
 * This demonstrates the base layer of our inheritance hierarchy.
 */
public abstract class MedicalEntity {

    protected String id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    /**
     * Default no-argument constructor for serialization support.
     * Protected to prevent external instantiation.
     */
    protected MedicalEntity() {
        this("DEFAULT");
    }

    /**
     * Default constructor - initializes timestamps to current time.
     */
    protected MedicalEntity(String id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public String getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Update the updatedAt timestamp to current time.
     * Call this whenever the entity is modified.
     */
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Format timestamps for display.
     */
    protected String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "N/A";
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }

    /**
     * Get a description of this entity.
     * Subclasses should override this method.
     */
    public abstract String getDescription();

    @Override
    public String toString() {
        return "MedicalEntity{" +
                "id='" + id + '\'' +
                ", createdAt=" + formatDateTime(createdAt) +
                ", updatedAt=" + formatDateTime(updatedAt) +
                '}';
    }
}

