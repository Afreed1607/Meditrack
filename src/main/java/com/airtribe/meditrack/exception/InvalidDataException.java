package com.airtribe.meditrack.exception;

/**
 * Custom exception thrown when invalid data is encountered.
 * Used for data validation failures and invalid input scenarios.
 */
public class InvalidDataException extends Exception {

    private String fieldName;
    private Object invalidValue;

    /**
     * Simple constructor with just a message.
     */
    public InvalidDataException(String message) {
        super(message);
    }

    /**
     * Constructor with message and field name.
     */
    public InvalidDataException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    /**
     * Constructor with message and cause (exception chaining).
     */
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with message, field name, and invalid value.
     */
    public InvalidDataException(String message, String fieldName, Object invalidValue) {
        super(message);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    /**
     * Constructor with message, field name, invalid value, and cause.
     * Demonstrates full exception chaining capability.
     */
    public InvalidDataException(String message, String fieldName, Object invalidValue, Throwable cause) {
        super(message, cause);
        this.fieldName = fieldName;
        this.invalidValue = invalidValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("InvalidDataException: ");
        sb.append(getMessage());

        if (fieldName != null) {
            sb.append(" (Field: ").append(fieldName);
            if (invalidValue != null) {
                sb.append(", Value: ").append(invalidValue);
            }
            sb.append(")");
        }

        return sb.toString();
    }
}

