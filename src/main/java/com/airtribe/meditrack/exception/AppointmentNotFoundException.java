package com.airtribe.meditrack.exception;

/**
 * Custom exception thrown when an appointment is not found.
 * Extends the standard Exception class for checked exception handling.
 */
public class AppointmentNotFoundException extends Exception {

    private String appointmentId;

    /**
     * Constructor with message and appointment ID.
     */
    public AppointmentNotFoundException(String message, String appointmentId) {
        super(message);
        this.appointmentId = appointmentId;
    }

    /**
     * Constructor with message for generic not found scenarios.
     */
    public AppointmentNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause (exception chaining).
     * Useful when this exception is caused by another exception.
     */
    public AppointmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with message, cause, and appointment ID.
     */
    public AppointmentNotFoundException(String message, String appointmentId, Throwable cause) {
        super(message, cause);
        this.appointmentId = appointmentId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public String toString() {
        if (appointmentId != null) {
            return "AppointmentNotFoundException: " + getMessage() + " (ID: " + appointmentId + ")";
        }
        return "AppointmentNotFoundException: " + getMessage();
    }
}

