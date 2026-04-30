package com.airtribe.meditrack.interfaces;

import java.io.Serializable;

/**
 * Interface for entities that can be paid for.
 * Objects implementing this interface represent billable items in the system.
 * Extends Serializable to support bill persistence via serialization.
 */
public interface Payable extends Serializable {

    /**
     * Get the total amount to be paid, including any applicable taxes.
     *
     * @return The total payable amount as a double
     */
    double getTotalAmount();

    /**
     * Get the base amount before taxes.
     *
     * @return The base amount
     */
    double getBaseAmount();

    /**
     * Get the tax amount.
     *
     * @return The tax amount
     */
    double getTaxAmount();

    /**
     * Mark this item as paid.
     */
    void markAsPaid();

    /**
     * Check if this item has been paid.
     *
     * @return true if paid, false otherwise
     */
    boolean isPaid();

    /**
     * Get the payment date in string format.
     *
     * @return The payment date
     */
    String getPaymentDate();
}

