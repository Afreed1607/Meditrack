package com.airtribe.meditrack.enums;

/**
 * Enumeration representing the lifecycle status of an appointment.
 * Tracks the current state of an appointment from booking to completion.
 */
public enum AppointmentStatus {
    PENDING("Pending", "Appointment is scheduled but not yet confirmed"),
    CONFIRMED("Confirmed", "Appointment has been confirmed by the doctor"),
    CANCELLED("Cancelled", "Appointment has been cancelled"),
    COMPLETED("Completed", "Appointment has been completed"),
    RESCHEDULED("Rescheduled", "Appointment has been rescheduled to another date");

    private final String displayName;
    private final String description;

    AppointmentStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Check if appointment can be cancelled from current status.
     */
    public boolean canBeCancelled() {
        return this == PENDING || this == CONFIRMED;
    }

    /**
     * Check if appointment can be rescheduled from current status.
     */
    public boolean canBeRescheduled() {
        return this == PENDING || this == CONFIRMED;
    }

    /**
     * Find status by its display name.
     */
    public static AppointmentStatus fromDisplayName(String name) {
        for (AppointmentStatus status : AppointmentStatus.values()) {
            if (status.displayName.equalsIgnoreCase(name)) {
                return status;
            }
        }
        return PENDING; // Default status
    }
}

