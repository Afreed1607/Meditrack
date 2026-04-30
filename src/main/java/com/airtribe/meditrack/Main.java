package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Main entry point for the MediTrack application.
 * Provides a menu-driven console interface for all operations.
 * Demonstrates application orchestration and user interaction.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DoctorService doctorService = new DoctorService();
    private static final PatientService patientService = new PatientService();
    private static final AppointmentService appointmentService = new AppointmentService(doctorService);
    private static boolean running = true;

    public static void main(String[] args) {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║     " + Constants.APP_NAME + "║");
        System.out.println("║           Version " + Constants.APP_VERSION + "             ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        // Load data if --loadData argument provided
        if (args.length > 0 && "--loadData".equals(args[0])) {
            loadSampleData();
        }

        while (running) {
            displayMainMenu();
            processMainMenuChoice();
        }

        System.out.println("\nThank you for using MediTrack. Goodbye!");
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n┌─ MAIN MENU ─────────────────────┐");
        System.out.println("│ 1. Doctor Management             │");
        System.out.println("│ 2. Patient Management            │");
        System.out.println("│ 3. Appointment Management        │");
        System.out.println("│ 4. Billing Management            │");
        System.out.println("│ 5. Search Operations             │");
        System.out.println("│ 6. System Statistics             │");
        System.out.println("│ 7. Run Tests                     │");
        System.out.println("│ 8. Exit                          │");
        System.out.println("└──────────────────────────────────┘");
        System.out.print("Select option: ");
    }

    private static void processMainMenuChoice() {
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                manageDoctors();
                break;
            case "2":
                managePatients();
                break;
            case "3":
                manageAppointments();
                break;
            case "4":
                manageBilling();
                break;
            case "5":
                performSearches();
                break;
            case "6":
                displayStatistics();
                break;
            case "7":
                runTests();
                break;
            case "8":
                running = false;
                break;
            default:
                System.out.println("❌ Invalid option. Please try again.");
        }
    }

    private static void manageDoctors() {
        System.out.println("\n┌─ DOCTOR MANAGEMENT ─────────────────┐");
        System.out.println("│ 1. Register New Doctor              │");
        System.out.println("│ 2. View All Doctors                 │");
        System.out.println("│ 3. Search Doctor by Name            │");
        System.out.println("│ 4. Search by Specialization         │");
        System.out.println("│ 5. Add Available Slot               │");
        System.out.println("│ 6. Back to Main Menu                │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Select option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                registerDoctor();
                break;
            case "2":
                viewAllDoctors();
                break;
            case "3":
                searchDoctorByName();
                break;
            case "4":
                searchBySpecialization();
                break;
            case "5":
                addAvailableSlot();
                break;
            case "6":
                break;
            default:
                System.out.println("❌ Invalid option.");
        }
    }

    private static void registerDoctor() {
        System.out.println("\n--- Register New Doctor ---");
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Phone: ");
            String phone = scanner.nextLine().trim();

            System.out.print("Address: ");
            String address = scanner.nextLine().trim();

            System.out.println("Specialization:");
            int specCount = 0;
            for (Specialization spec : Specialization.values()) {
                System.out.println((++specCount) + ". " + spec.getDisplayName());
            }
            System.out.print("Select specialization: ");
            int specChoice = Integer.parseInt(scanner.nextLine().trim());
            Specialization specialization = Specialization.values()[specChoice - 1];

            System.out.print("Years of Experience: ");
            int experience = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Consultation Fee: ");
            double fee = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("License Number: ");
            String license = scanner.nextLine().trim();

            Doctor doctor = doctorService.registerDoctor(
                name, email, phone, "1980-01-01", address, specialization, experience, fee, license
            );

            System.out.println("\n✅ Doctor registered successfully!");
            System.out.println("📋 " + doctor.getDescription());

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void viewAllDoctors() {
        System.out.println("\n--- All Doctors ---");
        List<Doctor> doctors = doctorService.getAllDoctors();

        if (doctors.isEmpty()) {
            System.out.println("No doctors registered yet.");
        } else {
            doctors.forEach(doc -> System.out.println("• " + doc.getDescription()));
        }
    }

    private static void searchDoctorByName() {
        System.out.print("\nSearch for doctor name: ");
        String name = scanner.nextLine().trim();

        List<Doctor> results = doctorService.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("❌ No doctors found.");
        } else {
            System.out.println("\n📋 Search Results:");
            results.forEach(doc -> System.out.println("• " + doc.getDescription()));
        }
    }

    private static void searchBySpecialization() {
        System.out.println("\nSelect Specialization:");
        int count = 0;
        for (Specialization spec : Specialization.values()) {
            System.out.println((++count) + ". " + spec.getDisplayName());
        }
        System.out.print("Choice: ");
        int choice = Integer.parseInt(scanner.nextLine().trim());
        Specialization specialization = Specialization.values()[choice - 1];

        List<Doctor> results = doctorService.searchBySpecialization(specialization);
        if (results.isEmpty()) {
            System.out.println("❌ No doctors found in this specialization.");
        } else {
            System.out.println("\n📋 Doctors in " + specialization.getDisplayName() + ":");
            results.forEach(doc -> System.out.println("• " + doc.getDescription()));
        }
    }

    private static void addAvailableSlot() {
        System.out.print("\nEnter Doctor ID: ");
        String doctorId = scanner.nextLine().trim();
        System.out.print("Enter available slot (e.g., 14:00-14:30): ");
        String slot = scanner.nextLine().trim();

        try {
            doctorService.addAvailableSlot(doctorId, slot);
            System.out.println("✅ Slot added successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void managePatients() {
        System.out.println("\n┌─ PATIENT MANAGEMENT ────────────────┐");
        System.out.println("│ 1. Register New Patient             │");
        System.out.println("│ 2. View All Patients                │");
        System.out.println("│ 3. Search Patient by Name           │");
        System.out.println("│ 4. Add Allergy                      │");
        System.out.println("│ 5. Add Medical History              │");
        System.out.println("│ 6. Back to Main Menu                │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Select option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                registerPatient();
                break;
            case "2":
                viewAllPatients();
                break;
            case "3":
                searchPatientByName();
                break;
            case "4":
                addAllergy();
                break;
            case "5":
                addMedicalHistory();
                break;
            case "6":
                break;
            default:
                System.out.println("❌ Invalid option.");
        }
    }

    private static void registerPatient() {
        System.out.println("\n--- Register New Patient ---");
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Phone: ");
            String phone = scanner.nextLine().trim();

            System.out.print("Date of Birth (YYYY-MM-DD): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Address: ");
            String address = scanner.nextLine().trim();

            System.out.print("Blood Group (A+, A-, B+, B-, AB+, AB-, O+, O-): ");
            String bloodGroup = scanner.nextLine().trim().toUpperCase();

            Patient patient = patientService.registerPatient(
                name, email, phone, dob, address, bloodGroup
            );

            System.out.println("\n✅ Patient registered successfully!");
            System.out.println("📋 " + patient.getDescription());

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void viewAllPatients() {
        System.out.println("\n--- All Patients ---");
        List<Patient> patients = patientService.getAllPatients();

        if (patients.isEmpty()) {
            System.out.println("No patients registered yet.");
        } else {
            patients.forEach(patient -> System.out.println("• " + patient.getDescription()));
        }
    }

    private static void searchPatientByName() {
        System.out.print("\nSearch for patient name: ");
        String name = scanner.nextLine().trim();

        List<Patient> results = patientService.searchByName(name);
        if (results.isEmpty()) {
            System.out.println("❌ No patients found.");
        } else {
            System.out.println("\n📋 Search Results:");
            results.forEach(patient -> System.out.println("• " + patient.getDescription()));
        }
    }

    private static void addAllergy() {
        System.out.print("\nEnter Patient ID: ");
        String patientId = scanner.nextLine().trim();
        System.out.print("Enter allergy: ");
        String allergy = scanner.nextLine().trim();

        try {
            patientService.addAllergy(patientId, allergy);
            System.out.println("✅ Allergy added successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void addMedicalHistory() {
        System.out.print("\nEnter Patient ID: ");
        String patientId = scanner.nextLine().trim();
        System.out.print("Enter medical history entry: ");
        String history = scanner.nextLine().trim();

        try {
            patientService.addMedicalHistory(patientId, history);
            System.out.println("✅ Medical history added successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void manageAppointments() {
        System.out.println("\n┌─ APPOINTMENT MANAGEMENT ───────────┐");
        System.out.println("│ 1. Book Appointment                 │");
        System.out.println("│ 2. View All Appointments            │");
        System.out.println("│ 3. Confirm Appointment              │");
        System.out.println("│ 4. Cancel Appointment               │");
        System.out.println("│ 5. Back to Main Menu                │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Select option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                bookAppointment();
                break;
            case "2":
                viewAllAppointments();
                break;
            case "3":
                confirmAppointment();
                break;
            case "4":
                cancelAppointment();
                break;
            case "5":
                break;
            default:
                System.out.println("❌ Invalid option.");
        }
    }

    private static void bookAppointment() {
        System.out.println("\n--- Book Appointment ---");
        try {
            System.out.print("Doctor ID: ");
            String doctorId = scanner.nextLine().trim();

            System.out.print("Patient ID: ");
            String patientId = scanner.nextLine().trim();

            System.out.print("Appointment Date & Time (yyyy-MM-dd HH:mm): ");
            LocalDateTime dateTime = LocalDateTime.parse(
                scanner.nextLine().trim(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            );

            appointmentService.bookAppointment(doctorId, patientId, dateTime);
            System.out.println("✅ Appointment booked successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void viewAllAppointments() {
        System.out.println("\n--- All Appointments ---");
        List<com.airtribe.meditrack.entity.Appointment> appointments = appointmentService.getAllAppointments();

        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled yet.");
        } else {
            appointments.forEach(apt -> System.out.println("• " + apt.toString()));
        }
    }

    private static void confirmAppointment() {
        System.out.print("\nEnter Appointment ID: ");
        String appointmentId = scanner.nextLine().trim();

        try {
            appointmentService.confirmAppointment(appointmentId);
            System.out.println("✅ Appointment confirmed successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void cancelAppointment() {
        System.out.print("\nEnter Appointment ID: ");
        String appointmentId = scanner.nextLine().trim();

        try {
            appointmentService.cancelAppointment(appointmentId);
            System.out.println("✅ Appointment cancelled successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void manageBilling() {
        System.out.println("\n┌─ BILLING MANAGEMENT ────────────────┐");
        System.out.println("│ 1. View All Bills                   │");
        System.out.println("│ 2. Mark Bill as Paid                │");
        System.out.println("│ 3. Back to Main Menu                │");
        System.out.println("└─────────────────────────────────────��");
        System.out.print("Select option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                viewAllBills();
                break;
            case "2":
                markBillPaid();
                break;
            case "3":
                break;
            default:
                System.out.println("❌ Invalid option.");
        }
    }

    private static void viewAllBills() {
        System.out.println("\n--- All Bills ---");
        List<com.airtribe.meditrack.entity.Bill> bills = appointmentService.getAllBills();

        if (bills.isEmpty()) {
            System.out.println("No bills generated yet.");
        } else {
            bills.forEach(bill -> System.out.println("• " + bill.toString()));
        }
    }

    private static void markBillPaid() {
        System.out.print("\nEnter Bill ID: ");
        String billId = scanner.nextLine().trim();

        try {
            appointmentService.markBillAsPaid(billId);
            System.out.println("✅ Bill marked as paid!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private static void performSearches() {
        System.out.println("\n┌─ SEARCH OPERATIONS ─────────────────┐");
        System.out.println("│ 1. Search Doctors by Experience     │");
        System.out.println("│ 2. Search Patients by Blood Group   │");
        System.out.println("│ 3. Search Patients by Age Range     │");
        System.out.println("│ 4. Back to Main Menu                │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Select option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                searchDoctorsByExperience();
                break;
            case "2":
                searchPatientsByBloodGroup();
                break;
            case "3":
                searchPatientsByAgeRange();
                break;
            case "4":
                break;
            default:
                System.out.println("❌ Invalid option.");
        }
    }

    private static void searchDoctorsByExperience() {
        System.out.print("\nMinimum years of experience: ");
        int years = Integer.parseInt(scanner.nextLine().trim());

        List<Doctor> results = doctorService.searchByExperience(years);
        if (results.isEmpty()) {
            System.out.println("❌ No doctors found with " + years + "+ years experience.");
        } else {
            System.out.println("\n📋 Doctors with " + years + "+ years experience:");
            results.forEach(doc -> System.out.println("• " + doc.getDescription()));
        }
    }

    private static void searchPatientsByBloodGroup() {
        System.out.print("\nEnter blood group: ");
        String bloodGroup = scanner.nextLine().trim().toUpperCase();

        List<Patient> results = patientService.searchByBloodGroup(bloodGroup);
        if (results.isEmpty()) {
            System.out.println("❌ No patients found with blood group " + bloodGroup);
        } else {
            System.out.println("\n📋 Patients with blood group " + bloodGroup + ":");
            results.forEach(patient -> System.out.println("• " + patient.getDescription()));
        }
    }

    private static void searchPatientsByAgeRange() {
        System.out.print("\nMinimum age: ");
        int minAge = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Maximum age: ");
        int maxAge = Integer.parseInt(scanner.nextLine().trim());

        List<Patient> results = patientService.searchByAgeRange(minAge, maxAge);
        if (results.isEmpty()) {
            System.out.println("❌ No patients found in age range " + minAge + "-" + maxAge);
        } else {
            System.out.println("\n📋 Patients aged " + minAge + "-" + maxAge + ":");
            results.forEach(patient -> System.out.println("• " + patient.getDescription()));
        }
    }

    private static void displayStatistics() {
        System.out.println("\n┌─ SYSTEM STATISTICS ─────────────────┐");
        System.out.println("│ Total Doctors: " + String.format("%27d", doctorService.getTotalDoctors()) + "│");
        System.out.println("│ Total Patients: " + String.format("%26d", patientService.getTotalPatients()) + "│");
        System.out.println("│ Total Appointments: " + String.format("%20d", appointmentService.getTotalAppointments()) + "│");
        System.out.println("│ Total Bills: " + String.format("%28d", appointmentService.getTotalBills()) + "│");
        System.out.println("│ Total Revenue: " + String.format("₹%.2f", appointmentService.getTotalRevenue()) + " │");
        System.out.println("│ Avg Doctor Fee: " + String.format("₹%.2f", doctorService.getAverageConsultationFee()) + "  │");
        System.out.println("└─────────────────────────────────────┘");
    }

    private static void runTests() {
        System.out.println("\nRunning manual test suite...\n");
        com.airtribe.meditrack.test.TestRunner.main(new String[]{});
    }

    private static void loadSampleData() {
        System.out.println("Loading sample data...");

        try {
            // Add sample doctors
            doctorService.registerDoctor("Dr. Rajesh Kumar", "rajesh@hospital.com", "9876543210",
                "1980-05-15", "123 Medical Street, Mumbai", Specialization.CARDIOLOGY, 12, 500.0, "MED123456");

            doctorService.registerDoctor("Dr. Priya Singh", "priya@hospital.com", "9876543211",
                "1985-07-20", "456 Health Road, Delhi", Specialization.NEUROLOGY, 8, 400.0, "NEU234567");

            // Add sample patients
            patientService.registerPatient("Ramesh Gupta", "ramesh@email.com", "9998887776",
                LocalDate.of(1990, 1, 15), "Delhi", "B+");

            patientService.registerPatient("Anjali Sharma", "anjali@email.com", "9887776665",
                LocalDate.of(1995, 3, 20), "Mumbai", "O+");

            System.out.println("✅ Sample data loaded successfully!\n");

        } catch (Exception e) {
            System.err.println("Error loading sample data: " + e.getMessage());
        }
    }
}

