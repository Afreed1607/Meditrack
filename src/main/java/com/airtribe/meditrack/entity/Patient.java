package com.airtribe.meditrack.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patient entity extending Person.
 * Represents a medical patient with health-related information.
 * Implements Cloneable to demonstrate deep copy semantics.
 * Demonstrates inheritance and advanced OOP concepts.
 */
public class Patient extends Person implements Cloneable {

    private String bloodGroup;
    private List<String> medicalHistory;
    private List<String> allergies;
    private double height; // in cm
    private double weight; // in kg
    private String emergencyContact;
    private String emergencyContactPhone;

    /**
     * Constructor for creating a Patient instance.
     */
    public Patient(String id, String name, String email, String phoneNumber,
                   LocalDate dateOfBirth, String address, String bloodGroup) {
        super(id, name, email, phoneNumber, dateOfBirth, address);
        this.bloodGroup = bloodGroup;
        this.medicalHistory = new ArrayList<>();
        this.allergies = new ArrayList<>();
    }

    // Getters and Setters
    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
        updateTimestamp();
    }

    public List<String> getMedicalHistory() {
        return new ArrayList<>(medicalHistory);
    }

    /**
     * Add a medical history entry.
     */
    public void addMedicalHistory(String entry) {
        medicalHistory.add(entry);
        updateTimestamp();
    }

    /**
     * Get all allergies.
     */
    public List<String> getAllergies() {
        return new ArrayList<>(allergies);
    }

    /**
     * Add an allergy.
     */
    public void addAllergy(String allergy) {
        if (!allergies.contains(allergy)) {
            allergies.add(allergy);
            updateTimestamp();
        }
    }

    /**
     * Remove an allergy.
     */
    public void removeAllergy(String allergy) {
        allergies.remove(allergy);
        updateTimestamp();
    }

    /**
     * Check if patient is allergic to something.
     */
    public boolean hasAllergy(String allergy) {
        return allergies.contains(allergy);
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
        updateTimestamp();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        updateTimestamp();
    }

    /**
     * Calculate BMI (Body Mass Index).
     * BMI = weight(kg) / (height(m) * height(m))
     */
    public double calculateBMI() {
        if (height <= 0 || weight <= 0) return 0;
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    /**
     * Override abstract method from Person.
     * Demonstrates polymorphism - different description for Patient.
     */
    @Override
    public String getDescription() {
        return getName() + " (Blood Group: " + bloodGroup + ", Age: " + getAge() + ")";
    }

    /**
     * Deep clone implementation.
     * Creates a complete copy of this Patient with all nested objects copied.
     */
    @Override
    public Patient clone() throws CloneNotSupportedException {
        Patient cloned = (Patient) super.clone();
        // Deep copy for mutable collections
        cloned.medicalHistory = new ArrayList<>(this.medicalHistory);
        cloned.allergies = new ArrayList<>(this.allergies);
        return cloned;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", height=" + height + "cm" +
                ", weight=" + weight + "kg" +
                ", bmi=" + String.format("%.2f", calculateBMI()) +
                ", allergies=" + allergies +
                '}';
    }
}

