package com.airtribe.meditrack.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Immutable class representing a summary of a bill.
 * Demonstrates the immutability pattern - all fields are final, no setters.
 * Thread-safe by design, no external modification possible.
 * This is a read-only view of billing information.
 */
public final class BillSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String billId;
    private final String appointmentId;
    private final String doctorId;
    private final String patientId;
    private final double baseAmount;
    private final double taxAmount;
    private final double totalAmount;
    private final boolean isPaid;
    private final LocalDate paymentDate;
    private final LocalDate createdDate;

    /**
     * Constructor for creating an immutable BillSummary.
     * All parameters are final and cannot be changed after instantiation.
     */
    public BillSummary(String billId, String appointmentId, String doctorId, String patientId,
                       double baseAmount, double taxAmount, double totalAmount,
                       boolean isPaid, LocalDate paymentDate, LocalDate createdDate) {
        this.billId = billId;
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.baseAmount = baseAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
        this.paymentDate = paymentDate;
        this.createdDate = createdDate;
    }

    // Only getters - no setters (immutability)
    public String getBillId() {
        return billId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    /**
     * Format the payment date for display.
     * Returns N/A if not yet paid.
     */
    public String getFormattedPaymentDate() {
        if (paymentDate == null) return "Not Paid";
        return paymentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    /**
     * Get the status of the bill.
     */
    public String getStatus() {
        return isPaid ? "Paid" : "Unpaid";
    }

    /**
     * Get formatted created date.
     */
    public String getFormattedCreatedDate() {
        if (createdDate == null) return "N/A";
        return createdDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    /**
     * Custom equals implementation for immutable object comparison.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BillSummary)) return false;

        BillSummary that = (BillSummary) o;

        if (Double.compare(that.baseAmount, baseAmount) != 0) return false;
        if (Double.compare(that.taxAmount, taxAmount) != 0) return false;
        if (Double.compare(that.totalAmount, totalAmount) != 0) return false;
        if (isPaid != that.isPaid) return false;
        if (!billId.equals(that.billId)) return false;
        if (!appointmentId.equals(that.appointmentId)) return false;
        if (!doctorId.equals(that.doctorId)) return false;
        return patientId.equals(that.patientId);
    }

    /**
     * Custom hashCode implementation for immutable objects.
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = billId.hashCode();
        result = 31 * result + appointmentId.hashCode();
        result = 31 * result + doctorId.hashCode();
        result = 31 * result + patientId.hashCode();
        temp = Double.doubleToLongBits(baseAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(taxAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isPaid ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BillSummary{" +
                "billId='" + billId + '\'' +
                ", appointmentId='" + appointmentId + '\'' +
                ", baseAmount=" + baseAmount +
                ", tax=" + String.format("%.2f", taxAmount) +
                ", total=" + String.format("%.2f", totalAmount) +
                ", status=" + getStatus() +
                ", created=" + getFormattedCreatedDate() +
                '}';
    }
}

