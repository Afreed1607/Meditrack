package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import java.util.regex.Pattern;

/**
 * Utility class for validating user input and data.
 * Centralized validation logic used throughout the application.
 * Demonstrates static methods and utility class patterns.
 */
public class Validator {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private static final Pattern PHONE_PATTERN =
        Pattern.compile("^[0-9]{10}$");

    private static final Pattern NAME_PATTERN =
        Pattern.compile("^[a-zA-Z\\s]{2,}$");

    private Validator() {
        throw new AssertionError("Validator class cannot be instantiated");
    }

    /**
     * Validate email address format.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validate phone number (must be 10 digits).
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Validate person name (no numbers or special characters).
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (name.length() > Constants.MAX_NAME_LENGTH) {
            return false;
        }
        return NAME_PATTERN.matcher(name.trim()).matches();
    }

    /**
     * Validate age.
     */
    public static boolean isValidAge(int age) {
        return age >= Constants.MIN_AGE && age <= Constants.MAX_AGE;
    }

    /**
     * Validate password strength (minimum length).
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= Constants.MIN_PASSWORD_LENGTH;
    }

    /**
     * Validate that a string is not null or empty.
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Validate double is positive.
     */
    public static boolean isPositive(double value) {
        return value > 0;
    }

    /**
     * Validate integer is positive.
     */
    public static boolean isPositive(int value) {
        return value > 0;
    }

    /**
     * Validate blood group format.
     */
    public static boolean isValidBloodGroup(String bloodGroup) {
        if (bloodGroup == null || bloodGroup.isEmpty()) {
            return false;
        }
        String[] validGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        for (String group : validGroups) {
            if (group.equals(bloodGroup.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate license number format (alphanumeric, minimum 6 characters).
     */
    public static boolean isValidLicenseNumber(String license) {
        if (license == null || license.length() < 6) {
            return false;
        }
        return license.matches("^[a-zA-Z0-9]{6,}$");
    }
}

