package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for appointment and billing operations.
 * Handles appointment CRUD, status changes, and billing.
 * Demonstrates service layer orchestration and business logic.
 */
public class AppointmentService {

    private final DataStore<Appointment> appointmentStore;
    private final DataStore<Bill> billStore;
    private final IdGenerator idGenerator;
    private final DoctorService doctorService;

    /**
     * Constructor initializing stores, ID generator, and dependent services.
     */
    public AppointmentService(DoctorService doctorService) {
        this.appointmentStore = new DataStore<>("Appointments");
        this.billStore = new DataStore<>("Bills");
        this.idGenerator = IdGenerator.getInstance();
        this.doctorService = doctorService;
    }

    /**
     * Book a new appointment.
     */
    public Appointment bookAppointment(String doctorId, String patientId,
                                       LocalDateTime appointmentDateTime) throws Exception {
        // Validate doctor exists
        Optional<Doctor> doctor = doctorService.getDoctorById(doctorId);
        if (!doctor.isPresent()) {
            throw new Exception("Doctor not found with ID: " + doctorId);
        }

        // Generate ID and create appointment
        String appointmentId = idGenerator.generateAppointmentId();
        Appointment appointment = new Appointment(appointmentId, doctorId, patientId,
                appointmentDateTime);

        appointmentStore.add(appointment);
        return appointment;
    }

    /**
     * Get appointment by ID.
     */
    public Optional<Appointment> getAppointmentById(String appointmentId) {
        return appointmentStore.findFirst(apt -> apt.getId().equals(appointmentId));
    }

    /**
     * Get all appointments.
     */
    public List<Appointment> getAllAppointments() {
        return appointmentStore.getAll();
    }

    /**
     * Get appointments for a specific doctor.
     */
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointmentStore.findAll(apt -> apt.getDoctorId().equals(doctorId));
    }

    /**
     * Get appointments for a specific patient.
     */
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointmentStore.findAll(apt -> apt.getPatientId().equals(patientId));
    }

    /**
     * Get appointments by status.
     */
    public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
        return appointmentStore.findAll(apt -> apt.getStatus() == status);
    }

    /**
     * Confirm an appointment.
     */
    public void confirmAppointment(String appointmentId) throws AppointmentNotFoundException {
        Optional<Appointment> appointment = getAppointmentById(appointmentId);
        if (appointment.isPresent()) {
            appointment.get().setStatus(AppointmentStatus.CONFIRMED);
        } else {
            throw new AppointmentNotFoundException("Appointment not found", appointmentId);
        }
    }

    /**
     * Cancel an appointment.
     */
    public void cancelAppointment(String appointmentId) throws AppointmentNotFoundException {
        Optional<Appointment> appointment = getAppointmentById(appointmentId);
        if (appointment.isPresent()) {
            if (appointment.get().canBeCancelled()) {
                appointment.get().setStatus(AppointmentStatus.CANCELLED);
            } else {
                throw new AppointmentNotFoundException(
                    "Appointment cannot be cancelled from current status", appointmentId);
            }
        } else {
            throw new AppointmentNotFoundException("Appointment not found", appointmentId);
        }
    }

    /**
     * Reschedule an appointment.
     */
    public void rescheduleAppointment(String appointmentId, LocalDateTime newDateTime)
            throws AppointmentNotFoundException {
        Optional<Appointment> appointment = getAppointmentById(appointmentId);
        if (appointment.isPresent()) {
            if (appointment.get().canBeRescheduled()) {
                appointment.get().setAppointmentDateTime(newDateTime);
                appointment.get().setStatus(AppointmentStatus.RESCHEDULED);
            } else {
                throw new AppointmentNotFoundException(
                    "Appointment cannot be rescheduled from current status", appointmentId);
            }
        } else {
            throw new AppointmentNotFoundException("Appointment not found", appointmentId);
        }
    }

    /**
     * Add diagnosis and prescription to appointment.
     */
    public void addDiagnosisandPrescription(String appointmentId, String diagnosis,
                                           String prescription) throws Exception {
        Optional<Appointment> appointment = getAppointmentById(appointmentId);
        if (appointment.isPresent()) {
            appointment.get().setDiagnosis(diagnosis);
            appointment.get().setPrescription(prescription);
            appointment.get().setStatus(AppointmentStatus.COMPLETED);
        } else {
            throw new Exception("Appointment not found with ID: " + appointmentId);
        }
    }

    /**
     * Generate a bill for an appointment.
     */
    public Bill generateBill(String appointmentId, double consultationCharge)
            throws AppointmentNotFoundException {
        Optional<Appointment> appointment = getAppointmentById(appointmentId);
        if (appointment.isPresent()) {
            String billId = idGenerator.generateBillId();
            Bill bill = new Bill(billId, appointmentId, appointment.get().getDoctorId(),
                    appointment.get().getPatientId(), consultationCharge);

            billStore.add(bill);
            appointment.get().setConsultationCharge(consultationCharge);

            return bill;
        } else {
            throw new AppointmentNotFoundException("Appointment not found", appointmentId);
        }
    }

    /**
     * Get bill by ID.
     */
    public Optional<Bill> getBillById(String billId) {
        return billStore.findFirst(bill -> bill.getId().equals(billId));
    }

    /**
     * Get all bills.
     */
    public List<Bill> getAllBills() {
        return billStore.getAll();
    }

    /**
     * Mark bill as paid.
     */
    public void markBillAsPaid(String billId) throws Exception {
        Optional<Bill> bill = getBillById(billId);
        if (bill.isPresent()) {
            bill.get().markAsPaid();
        } else {
            throw new Exception("Bill not found with ID: " + billId);
        }
    }

    /**
     * Get total number of appointments.
     */
    public int getTotalAppointments() {
        return appointmentStore.size();
    }

    /**
     * Get total number of bills.
     */
    public int getTotalBills() {
        return billStore.size();
    }

    /**
     * Get total revenue from paid bills.
     */
    public double getTotalRevenue() {
        return billStore.getAll().stream()
                .filter(Bill::isPaid)
                .mapToDouble(Bill::getTotalAmount)
                .sum();
    }
}

