package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.Payable;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Bill entity representing an invoice for medical services.
 * Implements Payable interface and Serializable for persistence.
 * Demonstrates interface implementation and serialization.
 */
public class Bill extends MedicalEntity implements Payable, Serializable {

    private static final long serialVersionUID = 1L;

    private String appointmentId;
    private String doctorId;
    private String patientId;
    private double baseAmount;
    private double taxAmount;
    private double totalAmount;
    private boolean isPaid;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String description;

    /**
     * Constructor for creating a Bill.
     */
    public Bill(String id, String appointmentId, String doctorId, String patientId,
                double baseAmount) {
        super(id);
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.baseAmount = baseAmount;
        this.taxAmount = baseAmount * Constants.TAX_RATE;
        this.totalAmount = baseAmount + taxAmount;
        this.isPaid = false;
        this.paymentDate = null;
        this.paymentMethod = "Not Paid";
        this.description = "Medical consultation fee";
    }

    // Getters and Setters
    public String getAppointmentId() {
        return appointmentId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    @Override
    public double getBaseAmount() {
        return baseAmount;
    }

    @Override
    public double getTaxAmount() {
        return taxAmount;
    }

    @Override
    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public boolean isPaid() {
        return isPaid;
    }

    @Override
    public void markAsPaid() {
        this.isPaid = true;
        this.paymentDate = LocalDate.now();
        this.paymentMethod = "Paid";
        updateTimestamp();
    }

    @Override
    public String getPaymentDate() {
        if (paymentDate == null) return "Not Paid";
        return paymentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        updateTimestamp();
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Apply an additional discount to the bill.
     */
    public void applyDiscount(double discountPercentage) {
        double discountAmount = baseAmount * (discountPercentage / 100.0);
        this.baseAmount -= discountAmount;
        this.taxAmount = baseAmount * Constants.TAX_RATE;
        this.totalAmount = baseAmount + taxAmount;
        updateTimestamp();
    }

    /**
     * Implement abstract method from MedicalEntity.
     */
    @Override
    public String getDescription() {
        return "Bill for appointment " + appointmentId + " - Amount: " + totalAmount;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", baseAmount=" + baseAmount +
                ", tax=" + String.format("%.2f", taxAmount) +
                ", total=" + String.format("%.2f", totalAmount) +
                ", paid=" + isPaid +
                ", paymentDate=" + getPaymentDate() +
                '}';
    }
}

