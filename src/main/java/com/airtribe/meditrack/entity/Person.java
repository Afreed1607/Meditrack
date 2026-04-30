package com.airtribe.meditrack.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Abstract class representing a person in the medical system.
 * Both Doctor and Patient extend this class, demonstrating inheritance.
 * Contains common attributes like name, email, phone, and date of birth.
 */
public abstract class Person extends MedicalEntity {

    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    /**
     * Constructor with essential person details.
     */
    public Person(String id, String name, String email, String phoneNumber,
                  LocalDate dateOfBirth, String address) {
        super(id);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateTimestamp();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        updateTimestamp();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        updateTimestamp();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        updateTimestamp();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        updateTimestamp();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Calculate age from date of birth.
     */
    public int getAge() {
        if (dateOfBirth == null) return 0;
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }

    /**
     * Get full address as a single string.
     */
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (address != null && !address.isEmpty()) sb.append(address);
        if (city != null && !city.isEmpty()) sb.append(", ").append(city);
        if (state != null && !state.isEmpty()) sb.append(", ").append(state);
        if (zipCode != null && !zipCode.isEmpty()) sb.append(" - ").append(zipCode);
        return sb.toString();
    }

    /**
     * Abstract method that subclasses must implement.
     * Allows different descriptions for Doctor and Patient.
     */
    @Override
    public abstract String getDescription();

    /**
     * Format date of birth for display.
     */
    protected String formatDate(LocalDate date) {
        if (date == null) return "N/A";
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + formatDate(dateOfBirth) +
                ", address='" + address + '\'' +
                '}';
    }
}

