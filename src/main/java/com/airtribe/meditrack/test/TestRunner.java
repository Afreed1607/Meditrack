package com.airtribe.meditrack.test;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.enums.AppointmentStatus;
import com.airtribe.meditrack.enums.Specialization;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Manual test runner for the MediTrack application.
 * Tests all major components and functionality manually without JUnit.
 * Demonstrates all learning objectives in action.
 */
public class TestRunner {

    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  MediTrack - Manual Test Suite");
        System.out.println("========================================\n");

        try {
            testDoctorService();
            testPatientService();
            testAppointmentService();
            testValidator();
            testDateUtil();
            testIdGenerator();
            testDataStore();
            testCloning();
            testImmutability();
            testSerialization();

            printResults();
        } catch (Exception e) {
            System.err.println("Fatal error during testing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testDoctorService() {
        System.out.println("\n=== Testing DoctorService ===");
        DoctorService doctorService = new DoctorService();

        try {
            Doctor doc = doctorService.registerDoctor(
                "Dr. Rajesh Kumar", "rajesh@hospital.com", "9876543210",
                "1980-05-15", "123 Medical Street, Mumbai",
                Specialization.CARDIOLOGY, 12, 500.0, "MED123456"
            );
            assertTrue("Register Doctor", doc != null && doc.getId().startsWith("DOC"));

            Optional<Doctor> retrieved = doctorService.getDoctorById(doc.getId());
            assertTrue("Get Doctor by ID", retrieved.isPresent());

            List<Doctor> cardio = doctorService.searchBySpecialization(Specialization.CARDIOLOGY);
            assertTrue("Search by Specialization", cardio.size() > 0);

            doctorService.addAvailableSlot(doc.getId(), "10:00-10:30");
            assertTrue("Add Available Slot", doc.hasSlotAvailable("10:00-10:30"));

        } catch (Exception e) {
            fail("DoctorService error: " + e.getMessage());
        }
    }

    private static void testPatientService() {
        System.out.println("\n=== Testing PatientService ===");
        PatientService patientService = new PatientService();

        try {
            Patient patient = patientService.registerPatient(
                "Priya Singh", "priya@email.com", "8765432109",
                LocalDate.of(1995, 3, 20), "456 Health Road, Bangalore",
                "O+"
            );
            assertTrue("Register Patient", patient != null && patient.getId().startsWith("PAT"));

            Optional<Patient> retrieved = patientService.getPatientById(patient.getId());
            assertTrue("Get Patient by ID", retrieved.isPresent());

            patientService.addAllergy(patient.getId(), "Penicillin");
            assertTrue("Add Allergy", patientService.hasAllergy(patient.getId(), "Penicillin"));

            patientService.addMedicalHistory(patient.getId(), "Diabetes - Type 2");
            assertTrue("Add Medical History", patient.getMedicalHistory().size() > 0);

        } catch (Exception e) {
            fail("PatientService error: " + e.getMessage());
        }
    }

    private static void testAppointmentService() {
        System.out.println("\n=== Testing AppointmentService ===");
        DoctorService doctorService = new DoctorService();
        AppointmentService appointmentService = new AppointmentService(doctorService);

        try {
            Doctor doc = doctorService.registerDoctor(
                "Dr. Amit Patel", "amit@hospital.com", "9123456789",
                "1985-07-10", "789 Med Lane, Delhi",
                Specialization.NEUROLOGY, 8, 400.0, "NEU234567"
            );

            String doctorId = doc.getId();
            String patientId = "PAT-ABC123";
            LocalDateTime appointmentTime = LocalDateTime.now().plusDays(5).withHour(14).withMinute(0);

            Appointment apt = appointmentService.bookAppointment(doctorId, patientId, appointmentTime);
            assertTrue("Book Appointment", apt != null && apt.getStatus() == AppointmentStatus.PENDING);

            appointmentService.confirmAppointment(apt.getId());
            Optional<Appointment> confirmed = appointmentService.getAppointmentById(apt.getId());
            assertTrue("Confirm Appointment", confirmed.isPresent() &&
                    confirmed.get().getStatus() == AppointmentStatus.CONFIRMED);

            Bill bill = appointmentService.generateBill(apt.getId(), 400.0);
            assertTrue("Generate Bill", bill != null && !bill.isPaid());

            appointmentService.markBillAsPaid(bill.getId());
            Optional<Bill> paidBill = appointmentService.getBillById(bill.getId());
            assertTrue("Mark Bill as Paid", paidBill.isPresent() && paidBill.get().isPaid());

        } catch (Exception e) {
            fail("AppointmentService error: " + e.getMessage());
        }
    }

    private static void testValidator() {
        System.out.println("\n=== Testing Validator ===");

        assertTrue("Valid Email", Validator.isValidEmail("user@example.com"));
        assertTrue("Invalid Email", !Validator.isValidEmail("invalid-email"));

        assertTrue("Valid Phone", Validator.isValidPhone("9876543210"));
        assertTrue("Invalid Phone", !Validator.isValidPhone("12345"));

        assertTrue("Valid Name", Validator.isValidName("John Doe"));
        assertTrue("Invalid Name", !Validator.isValidName("123"));
    }

    private static void testDateUtil() {
        System.out.println("\n=== Testing DateUtil ===");

        String formattedDate = DateUtil.formatDate(LocalDate.now());
        assertTrue("Format Date", formattedDate != null && !formattedDate.isEmpty());

        LocalDate parsed = DateUtil.parseDate(formattedDate);
        assertTrue("Parse Date", parsed != null);

        long daysBetween = DateUtil.daysBetween(LocalDate.now(), LocalDate.now().plusDays(5));
        assertTrue("Days Between", daysBetween == 5);
    }

    private static void testIdGenerator() {
        System.out.println("\n=== Testing IdGenerator ===");
        IdGenerator idGen = IdGenerator.getInstance();

        String uuid = idGen.generateUUID();
        assertTrue("Generate UUID", uuid != null && !uuid.isEmpty());

        String aptId = idGen.generateAppointmentId();
        assertTrue("Generate Appointment ID", aptId.startsWith("APT-"));

        String billId = idGen.generateBillId();
        assertTrue("Generate Bill ID", billId.startsWith("BILL-"));

        IdGenerator idGen2 = IdGenerator.getInstance();
        assertTrue("Singleton Pattern", idGen == idGen2);
    }

    private static void testDataStore() {
        System.out.println("\n=== Testing DataStore<T> ===");

        DataStore<String> store = new DataStore<>("Test Store");
        store.add("Item 1");
        store.add("Item 2");
        store.add("Item 3");

        assertTrue("Add Items", store.size() == 3);

        List<String> filtered = store.findAll(item -> item.contains("1"));
        assertTrue("Find with Predicate", filtered.size() == 1);

        store.remove("Item 2");
        assertTrue("Remove Item", store.size() == 2);
    }

    private static void testCloning() throws CloneNotSupportedException {
        System.out.println("\n=== Testing Cloning (Deep Copy) ===");

        Patient original = new Patient(
            "PAT-001", "Ramesh Gupta", "ramesh@email.com", "9998887776",
            LocalDate.of(1990, 1, 15), "Delhi", "B+"
        );
        original.addAllergy("Aspirin");

        Patient cloned = original.clone();
        cloned.addAllergy("Penicillin");

        assertTrue("Deep Clone - Original unchanged",
                original.getAllergies().size() == 1 && !original.hasAllergy("Penicillin"));
        assertTrue("Deep Clone - Clone modified",
                cloned.getAllergies().size() == 2);
    }

    private static void testImmutability() {
        System.out.println("\n=== Testing Immutability (BillSummary) ===");

        BillSummary summary = new BillSummary(
            "BILL-1", "APT-1", "DOC-1", "PAT-1",
            500.0, 90.0, 590.0, false, null, LocalDate.now()
        );

        assertTrue("BillSummary Immutable", summary.getTotalAmount() == 590.0);
        assertTrue("BillSummary Status", summary.getStatus().equals("Unpaid"));
    }

    private static void testSerialization() throws Exception {
        System.out.println("\n=== Testing Serialization ===");

        String testPath = "data/test_bill.ser";
        Bill bill = new Bill("BILL-TEST", "APT-TEST", "DOC-TEST", "PAT-TEST", 1000.0);

        SerializationUtil.serializeObject(testPath, bill);
        assertTrue("Serialize Bill", SerializationUtil.fileExists(testPath));

        Bill deserialized = SerializationUtil.deserializeObject(testPath);
        assertTrue("Deserialize Bill", deserialized != null && deserialized.getTotalAmount() > 0);

        SerializationUtil.deleteFile(testPath);
    }

    // Helper methods
    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("✅ " + testName);
            passedTests++;
        } else {
            System.out.println("❌ " + testName);
            failedTests++;
        }
    }

    private static void fail(String message) {
        System.out.println("❌ " + message);
        failedTests++;
    }

    private static void printResults() {
        System.out.println("\n========================================");
        System.out.println("  Test Results Summary");
        System.out.println("========================================");
        System.out.println("✅ Passed: " + passedTests);
        System.out.println("❌ Failed: " + failedTests);
        System.out.println("📊 Total: " + (passedTests + failedTests));
        System.out.println("========================================");
    }
}

