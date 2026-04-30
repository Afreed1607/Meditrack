package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.Specialization;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Doctor entity extending Person.
 * Represents a medical professional with specialization, experience, and fees.
 * Demonstrates inheritance, encapsulation, and polymorphism.
 */
public class Doctor extends Person {

    private Specialization specialization;
    private int yearsOfExperience;
    private double consultationFee;
    private String licenseNumber;
    private boolean isAvailable;
    private List<String> availableSlots;

    /**
     * Constructor for creating a Doctor instance.
     * Uses super() to call parent constructor (Person).
     */
    public Doctor(String id, String name, String email, String phoneNumber,
                  LocalDate dateOfBirth, String address, Specialization specialization,
                  int yearsOfExperience, double consultationFee, String licenseNumber) {
        super(id, name, email, phoneNumber, dateOfBirth, address);
        this.specialization = specialization;
        this.yearsOfExperience = yearsOfExperience;
        this.consultationFee = consultationFee;
        this.licenseNumber = licenseNumber;
        this.isAvailable = true;
        this.availableSlots = new ArrayList<>();
    }

    // Getters and Setters
    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
        updateTimestamp();
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
        updateTimestamp();
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
        updateTimestamp();
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
        updateTimestamp();
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
        updateTimestamp();
    }

    public List<String> getAvailableSlots() {
        return new ArrayList<>(availableSlots);
    }

    /**
     * Add an available time slot for appointments.
     */
    public void addAvailableSlot(String slot) {
        if (!availableSlots.contains(slot)) {
            availableSlots.add(slot);
            updateTimestamp();
        }
    }

    /**
     * Remove an available time slot.
     */
    public void removeAvailableSlot(String slot) {
        availableSlots.remove(slot);
        updateTimestamp();
    }

    /**
     * Check if a specific slot is available.
     */
    public boolean hasSlotAvailable(String slot) {
        return availableSlots.contains(slot);
    }

    /**
     * Calculate rating multiplier based on experience.
     * More experienced doctors get a 10% higher fee.
     */
    public double getExperienceMultiplier() {
        if (yearsOfExperience >= 10) return 1.10;
        if (yearsOfExperience >= 5) return 1.05;
        return 1.0;
    }

    /**
     * Get the effective consultation fee considering experience.
     */
    public double getEffectiveConsultationFee() {
        return consultationFee * getExperienceMultiplier();
    }

    /**
     * Override abstract method from Person.
     * Demonstrates polymorphism - different description for Doctor.
     */
    @Override
    public String getDescription() {
        return "Dr. " + getName() + " - " + specialization.getDisplayName() +
               " (" + yearsOfExperience + " years experience)";
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", specialization=" + specialization +
                ", experience=" + yearsOfExperience + " years" +
                ", fee=" + consultationFee +
                ", license='" + licenseNumber + '\'' +
                ", available=" + isAvailable +
                '}';
    }
}

