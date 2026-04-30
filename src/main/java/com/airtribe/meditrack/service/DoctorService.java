package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer for doctor-related operations.
 * Handles CRUD operations, search, and business logic for doctors.
 * Demonstrates service layer pattern and separation of concerns.
 */
public class DoctorService {

    private final DataStore<Doctor> doctorStore;
    private final IdGenerator idGenerator;

    /**
     * Constructor initializing the doctor store and ID generator.
     */
    public DoctorService() {
        this.doctorStore = new DataStore<>("Doctors");
        this.idGenerator = IdGenerator.getInstance();
    }

    /**
     * Register a new doctor.
     */
    public Doctor registerDoctor(String name, String email, String phoneNumber,
                                 String dateOfBirth, String address,
                                 Specialization specialization, int experience,
                                 double fee, String licenseNumber) {
        // Validate inputs
        if (!Validator.isValidName(name)) {
            throw new IllegalArgumentException("Invalid doctor name");
        }
        if (!Validator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (!Validator.isValidPhone(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        if (!Validator.isValidLicenseNumber(licenseNumber)) {
            throw new IllegalArgumentException("Invalid license number");
        }
        if (experience < 0) {
            throw new IllegalArgumentException("Experience cannot be negative");
        }
        if (!Validator.isPositive(fee)) {
            throw new IllegalArgumentException("Consultation fee must be positive");
        }

        // Generate ID and create doctor
        String doctorId = idGenerator.generateDoctorId();
        Doctor doctor = new Doctor(doctorId, name, email, phoneNumber,
                null, address, specialization, experience, fee, licenseNumber);

        doctorStore.add(doctor);
        return doctor;
    }

    /**
     * Get doctor by ID.
     */
    public Optional<Doctor> getDoctorById(String doctorId) {
        return doctorStore.findFirst(doc -> doc.getId().equals(doctorId));
    }

    /**
     * Get all doctors.
     */
    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }

    /**
     * Search doctors by specialization.
     */
    public List<Doctor> searchBySpecialization(Specialization specialization) {
        return doctorStore.findAll(doc -> doc.getSpecialization() == specialization);
    }

    /**
     * Search doctors by name.
     */
    public List<Doctor> searchByName(String name) {
        String lowerName = name.toLowerCase();
        return doctorStore.findAll(doc -> doc.getName().toLowerCase().contains(lowerName));
    }

    /**
     * Search doctors by minimum experience.
     */
    public List<Doctor> searchByExperience(int minExperience) {
        return doctorStore.findAll(doc -> doc.getYearsOfExperience() >= minExperience);
    }

    /**
     * Search doctors by fee range.
     */
    public List<Doctor> searchByFeeRange(double minFee, double maxFee) {
        return doctorStore.findAll(doc -> {
            double fee = doc.getEffectiveConsultationFee();
            return fee >= minFee && fee <= maxFee;
        });
    }

    /**
     * Get available doctors by specialization.
     */
    public List<Doctor> getAvailableDoctors(Specialization specialization) {
        return doctorStore.findAll(doc ->
            doc.isAvailable() && doc.getSpecialization() == specialization
        );
    }

    /**
     * Add available time slot to doctor's schedule.
     */
    public void addAvailableSlot(String doctorId, String slot) throws Exception {
        Optional<Doctor> doctor = getDoctorById(doctorId);
        if (doctor.isPresent()) {
            doctor.get().addAvailableSlot(slot);
        } else {
            throw new Exception("Doctor not found with ID: " + doctorId);
        }
    }

    /**
     * Mark doctor as unavailable.
     */
    public void markUnavailable(String doctorId) throws Exception {
        Optional<Doctor> doctor = getDoctorById(doctorId);
        if (doctor.isPresent()) {
            doctor.get().setAvailable(false);
        } else {
            throw new Exception("Doctor not found with ID: " + doctorId);
        }
    }

    /**
     * Mark doctor as available.
     */
    public void markAvailable(String doctorId) throws Exception {
        Optional<Doctor> doctor = getDoctorById(doctorId);
        if (doctor.isPresent()) {
            doctor.get().setAvailable(true);
        } else {
            throw new Exception("Doctor not found with ID: " + doctorId);
        }
    }

    /**
     * Update doctor's consultation fee.
     */
    public void updateFee(String doctorId, double newFee) throws Exception {
        Optional<Doctor> doctor = getDoctorById(doctorId);
        if (doctor.isPresent()) {
            doctor.get().setConsultationFee(newFee);
        } else {
            throw new Exception("Doctor not found with ID: " + doctorId);
        }
    }

    /**
     * Get total number of doctors.
     */
    public int getTotalDoctors() {
        return doctorStore.size();
    }

    /**
     * Get average consultation fee.
     */
    public double getAverageConsultationFee() {
        return doctorStore.getAll().stream()
                .mapToDouble(Doctor::getConsultationFee)
                .average()
                .orElse(0.0);
    }

    /**
     * Delete doctor by ID.
     */
    public boolean deleteDoctor(String doctorId) {
        Optional<Doctor> doctor = getDoctorById(doctorId);
        return doctor.isPresent() && doctorStore.remove(doctor.get());
    }
}

