package com.airtribe.meditrack.enums;

/**
 * Enumeration representing different medical specializations.
 * Each specialization defines a specific area of medical practice.
 */
public enum Specialization {
    CARDIOLOGY("Cardiology", "Heart and cardiovascular system specialist"),
    NEUROLOGY("Neurology", "Nervous system and brain specialist"),
    ORTHOPEDICS("Orthopedics", "Bone, joint, and muscle specialist"),
    DERMATOLOGY("Dermatology", "Skin and dermatological conditions specialist"),
    PEDIATRICS("Pediatrics", "Children and infant care specialist"),
    GENERAL_PRACTICE("General Practice", "General medical practitioner"),
    PSYCHIATRY("Psychiatry", "Mental health and behavioral specialist"),
    ONCOLOGY("Oncology", "Cancer treatment specialist"),
    GASTROENTEROLOGY("Gastroenterology", "Digestive system specialist"),
    PULMONOLOGY("Pulmonology", "Respiratory system specialist");

    private final String displayName;
    private final String description;

    Specialization(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Find specialization by its display name.
     * Useful for CSV parsing and user input.
     */
    public static Specialization fromDisplayName(String name) {
        for (Specialization spec : Specialization.values()) {
            if (spec.displayName.equalsIgnoreCase(name)) {
                return spec;
            }
        }
        return GENERAL_PRACTICE; // Default fallback
    }
}

