package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.enums.AppointmentStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Appointment entity representing a scheduled meeting between a doctor and patient.
 * Implements Cloneable for deep copying and Searchable for search operations.
 */
public class Appointment extends MedicalEntity implements Cloneable {

    private String doctorId;
    private String patientId;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;
    private String notes;
    private String diagnosis;
    private String prescription;
    private double consultationCharge;

    /**
     * Constructor for creating an appointment.
     */
    public Appointment(String id, String doctorId, String patientId,
                       LocalDateTime appointmentDateTime) {
        super(id);
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDateTime = appointmentDateTime;
        this.status = AppointmentStatus.PENDING;
        this.notes = "";
        this.diagnosis = "";
        this.prescription = "";
        this.consultationCharge = 0;
    }

    // Getters and Setters
    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
        updateTimestamp();
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
        updateTimestamp();
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
        updateTimestamp();
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
        updateTimestamp();
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
        updateTimestamp();
    }

    public double getConsultationCharge() {
        return consultationCharge;
    }

    public void setConsultationCharge(double consultationCharge) {
        this.consultationCharge = consultationCharge;
        updateTimestamp();
    }

    /**
     * Check if appointment can still be cancelled.
     */
    public boolean canBeCancelled() {
        return status.canBeCancelled();
    }

    /**
     * Check if appointment can be rescheduled.
     */
    public boolean canBeRescheduled() {
        return status.canBeRescheduled();
    }

    /**
     * Check if appointment is in the past.
     */
    public boolean isPastAppointment() {
        return LocalDateTime.now().isAfter(appointmentDateTime);
    }

    /**
     * Implement abstract method from MedicalEntity.
     */
    @Override
    public String getDescription() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "Appointment on " + appointmentDateTime.format(formatter) + " - " + status.getDisplayName();
    }

    /**
     * Deep clone implementation for Appointment.
     */
    @Override
    public Appointment clone() throws CloneNotSupportedException {
        return (Appointment) super.clone();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return "Appointment{" +
                "id='" + id + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", date=" + appointmentDateTime.format(formatter) +
                ", status=" + status +
                ", charge=" + consultationCharge +
                '}';
    }
}

