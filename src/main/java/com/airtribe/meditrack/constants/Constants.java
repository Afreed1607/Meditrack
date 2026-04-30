package com.airtribe.meditrack.constants;

/**
 * Application-wide constants for the MediTrack system.
 * This class maintains all configuration values, tax rates, and file paths
 * used throughout the application.
 */
public class Constants {

    // Tax and billing constants
    public static final double TAX_RATE = 0.18; // 18% GST
    public static final double CONSULTATION_FEE_MULTIPLIER = 1.5;

    // File paths for persistence
    public static final String DOCTORS_CSV_PATH = "data/doctors.csv";
    public static final String PATIENTS_CSV_PATH = "data/patients.csv";
    public static final String APPOINTMENTS_CSV_PATH = "data/appointments.csv";
    public static final String BILLS_SERIALIZATION_PATH = "data/bills.ser";

    // Application configuration
    public static final String APP_NAME = "MediTrack - Clinic Management System";
    public static final String APP_VERSION = "1.0.0";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATETIME_FORMAT = "dd-MM-yyyy HH:mm";

    // Validation constants
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 120;

    // Appointment constants
    public static final int APPOINTMENT_DURATION_MINUTES = 30;
    public static final int MAX_APPOINTMENTS_PER_DAY = 20;

    // Error messages
    public static final String INVALID_EMAIL_MESSAGE = "Please enter a valid email address";
    public static final String INVALID_PHONE_MESSAGE = "Please enter a valid phone number (10 digits)";
    public static final String INVALID_AGE_MESSAGE = "Age must be between 18 and 120";
    public static final String INVALID_NAME_MESSAGE = "Name cannot be empty or exceed 100 characters";

    private Constants() {
        // Private constructor to prevent instantiation
        throw new AssertionError("Constants class cannot be instantiated");
    }
}

