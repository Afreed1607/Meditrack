package com.airtribe.meditrack.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for reading and writing CSV files.
 * Demonstrates file I/O operations and try-with-resources pattern.
 * Handles data persistence in CSV format for doctors, patients, and appointments.
 */
public class CSVUtil {

    private CSVUtil() {
        throw new AssertionError("CSVUtil class cannot be instantiated");
    }

    /**
     * Read CSV file and return list of string arrays.
     * Each inner array represents a row.
     * Demonstrates try-with-resources for automatic resource closure.
     */
    public static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return data; // Return empty list if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] values = line.split(",");
                    // Trim whitespace from each value
                    for (int i = 0; i < values.length; i++) {
                        values[i] = values[i].trim();
                    }
                    data.add(values);
                }
            }
        }

        return data;
    }

    /**
     * Write data to CSV file.
     * Each inner list represents a row.
     */
    public static void writeCSV(String filePath, List<List<String>> data) throws IOException {
        File file = new File(filePath);

        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (List<String> row : data) {
                String csvLine = String.join(",", row);
                writer.write(csvLine);
                writer.newLine();
            }
        }
    }

    /**
     * Read CSV file with header and return list of maps.
     * Each map represents a row with column names as keys.
     */
    public static List<Map<String, String>> readCSVWithHeader(String filePath) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        List<String[]> rows = readCSV(filePath);

        if (rows.isEmpty()) {
            return data;
        }

        String[] headers = rows.get(0);

        for (int i = 1; i < rows.size(); i++) {
            String[] values = rows.get(i);
            Map<String, String> row = new HashMap<>();

            for (int j = 0; j < headers.length && j < values.length; j++) {
                row.put(headers[j], values[j]);
            }

            data.add(row);
        }

        return data;
    }

    /**
     * Write CSV with header.
     */
    public static void writeCSVWithHeader(String filePath, List<String> headers,
                                         List<List<String>> data) throws IOException {
        List<List<String>> allData = new ArrayList<>();
        allData.add(new ArrayList<>(headers));
        allData.addAll(data);
        writeCSV(filePath, allData);
    }

    /**
     * Check if CSV file exists.
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Delete CSV file.
     */
    public static boolean deleteFile(String filePath) {
        return new File(filePath).delete();
    }

    /**
     * Append row to existing CSV file.
     */
    public static void appendToCSV(String filePath, List<String> row) throws IOException {
        File file = new File(filePath);

        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String csvLine = String.join(",", row);
            writer.write(csvLine);
            writer.newLine();
        }
    }
}

