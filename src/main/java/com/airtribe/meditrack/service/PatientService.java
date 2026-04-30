package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for patient-related operations.
 * Handles CRUD operations, medical history, allergies, and patient searching.
 * Demonstrates service layer responsibility and business logic encapsulation.
 */
public class PatientService {

    private final DataStore<Patient> patientStore;
    private final IdGenerator idGenerator;

    /**
     * Constructor initializing the patient store and ID generator.
     */
    public PatientService() {
        this.patientStore = new DataStore<>("Patients");
        this.idGenerator = IdGenerator.getInstance();
    }

    /**
     * Register a new patient.
     */
    public Patient registerPatient(String name, String email, String phoneNumber,
                                   LocalDate dateOfBirth, String address,
                                   String bloodGroup) {
        // Validate inputs
        if (!Validator.isValidName(name)) {
            throw new IllegalArgumentException("Invalid patient name");
        }
        if (!Validator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (!Validator.isValidPhone(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        if (!Validator.isValidBloodGroup(bloodGroup)) {
            throw new IllegalArgumentException("Invalid blood group");
        }

        // Generate ID and create patient
        String patientId = idGenerator.generatePatientId();
        Patient patient = new Patient(patientId, name, email, phoneNumber,
                dateOfBirth, address, bloodGroup);

        patientStore.add(patient);
        return patient;
    }

    /**
     * Get patient by ID.
     */
    public Optional<Patient> getPatientById(String patientId) {
        return patientStore.findFirst(patient -> patient.getId().equals(patientId));
    }

    /**
     * Get all patients.
     */
    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }

    /**
     * Search patients by name.
     */
    public List<Patient> searchByName(String name) {
        String lowerName = name.toLowerCase();
        return patientStore.findAll(patient ->
            patient.getName().toLowerCase().contains(lowerName)
        );
    }

    /**
     * Search patients by blood group.
     */
    public List<Patient> searchByBloodGroup(String bloodGroup) {
        return patientStore.findAll(patient ->
            patient.getBloodGroup().equalsIgnoreCase(bloodGroup)
        );
    }

    /**
     * Search patients by age range.
     */
    public List<Patient> searchByAgeRange(int minAge, int maxAge) {
        return patientStore.findAll(patient -> {
            int age = patient.getAge();
            return age >= minAge && age <= maxAge;
        });
    }

    /**
     * Add medical history entry for a patient.
     */
    public void addMedicalHistory(String patientId, String entry) throws Exception {
        Optional<Patient> patient = getPatientById(patientId);
        if (patient.isPresent()) {
            patient.get().addMedicalHistory(entry);
        } else {
            throw new Exception("Patient not found with ID: " + patientId);
        }
    }

    /**
     * Add allergy for a patient.
     */
    public void addAllergy(String patientId, String allergy) throws Exception {
        Optional<Patient> patient = getPatientById(patientId);
        if (patient.isPresent()) {
            patient.get().addAllergy(allergy);
        } else {
            throw new Exception("Patient not found with ID: " + patientId);
        }
    }

    /**
     * Remove allergy from patient.
     */
    public void removeAllergy(String patientId, String allergy) throws Exception {
        Optional<Patient> patient = getPatientById(patientId);
        if (patient.isPresent()) {
            patient.get().removeAllergy(allergy);
        } else {
            throw new Exception("Patient not found with ID: " + patientId);
        }
    }

    /**
     * Check if patient has an allergy.
     */
    public boolean hasAllergy(String patientId, String allergy) throws Exception {
        Optional<Patient> patient = getPatientById(patientId);
        if (patient.isPresent()) {
            return patient.get().hasAllergy(allergy);
        } else {
            throw new Exception("Patient not found with ID: " + patientId);
        }
    }

    /**
     * Update patient's height and weight (for BMI calculation).
     */
    public void updateMeasurements(String patientId, double height, double weight) throws Exception {
        Optional<Patient> patient = getPatientById(patientId);
        if (patient.isPresent()) {
            patient.get().setHeight(height);
            patient.get().setWeight(weight);
        } else {
            throw new Exception("Patient not found with ID: " + patientId);
        }
    }

    /**
     * Get patient's BMI.
     */
    public double getPatientBMI(String patientId) throws Exception {
        Optional<Patient> patient = getPatientById(patientId);
        if (patient.isPresent()) {
            return patient.get().calculateBMI();
        } else {
            throw new Exception("Patient not found with ID: " + patientId);
        }
    }

    /**
     * Set emergency contact information.
     */
    public void setEmergencyContact(String patientId, String contactName,
                                   String contactPhone) throws Exception {
        Optional<Patient> patient = getPatientById(patientId);
        if (patient.isPresent()) {
            patient.get().setEmergencyContact(contactName);
            patient.get().setEmergencyContactPhone(contactPhone);
        } else {
            throw new Exception("Patient not found with ID: " + patientId);
        }
    }

    /**
     * Get total number of patients.
     */
    public int getTotalPatients() {
        return patientStore.size();
    }

    /**
     * Delete patient by ID.
     */
    public boolean deletePatient(String patientId) {
        Optional<Patient> patient = getPatientById(patientId);
        return patient.isPresent() && patientStore.remove(patient.get());
    }
}

