package com.airtribe.meditrack.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton class for generating unique IDs throughout the application.
 * Demonstrates the Singleton design pattern with eager initialization.
 * Thread-safe using AtomicInteger for counter operations.
 */
public class IdGenerator {

    // Eager initialization - instance created when class is loaded
    private static final IdGenerator INSTANCE = new IdGenerator();

    private final AtomicInteger appointmentCounter = new AtomicInteger(1000);
    private final AtomicInteger billCounter = new AtomicInteger(5000);

    /**
     * Private constructor - prevents external instantiation.
     * Part of Singleton pattern.
     */
    private IdGenerator() {
        // Private constructor body
    }

    /**
     * Get the singleton instance.
     * Thread-safe by design.
     */
    public static IdGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * Generate a unique UUID (for doctors, patients, appointments).
     */
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate a unique appointment ID with prefix.
     * Example: APT-1001, APT-1002, etc.
     */
    public String generateAppointmentId() {
        int nextId = appointmentCounter.incrementAndGet();
        return "APT-" + nextId;
    }

    /**
     * Generate a unique bill ID with prefix.
     * Example: BILL-5001, BILL-5002, etc.
     */
    public String generateBillId() {
        int nextId = billCounter.incrementAndGet();
        return "BILL-" + nextId;
    }

    /**
     * Generate a prefixed ID for any entity type.
     * Example: "DOC", "PAT", "APP"
     */
    public String generatePrefixedId(String prefix) {
        String uuid = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return prefix + "-" + uuid;
    }

    /**
     * Generate a doctor ID.
     */
    public String generateDoctorId() {
        return generatePrefixedId("DOC");
    }

    /**
     * Generate a patient ID.
     */
    public String generatePatientId() {
        return generatePrefixedId("PAT");
    }

    /**
     * Reset appointment counter (useful for testing).
     */
    public void resetAppointmentCounter() {
        appointmentCounter.set(1000);
    }

    /**
     * Reset bill counter (useful for testing).
     */
    public void resetBillCounter() {
        billCounter.set(5000);
    }

    /**
     * Get current appointment counter value.
     */
    public int getAppointmentCounterValue() {
        return appointmentCounter.get();
    }

    /**
     * Get current bill counter value.
     */
    public int getBillCounterValue() {
        return billCounter.get();
    }
}

