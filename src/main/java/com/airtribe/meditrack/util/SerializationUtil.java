package com.airtribe.meditrack.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for Java object serialization and deserialization.
 * Allows saving objects to files and restoring them.
 * Demonstrates try-with-resources pattern for resource management.
 */
public class SerializationUtil {

    private SerializationUtil() {
        throw new AssertionError("SerializationUtil class cannot be instantiated");
    }

    /**
     * Serialize an object to a file.
     * The object must implement Serializable interface.
     */
    public static <T> void serializeObject(String filePath, T object) throws IOException {
        File file = new File(filePath);

        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(object);
        }
    }

    /**
     * Serialize a list of objects to a file.
     */
    public static <T> void serializeList(String filePath, List<T> list) throws IOException {
        File file = new File(filePath);

        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeInt(list.size());
            for (T object : list) {
                oos.writeObject(object);
            }
        }
    }

    /**
     * Deserialize an object from a file.
     * Returns null if deserialization fails or file doesn't exist.
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserializeObject(String filePath) throws IOException, ClassNotFoundException {
        File file = new File(filePath);

        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (T) ois.readObject();
        }
    }

    /**
     * Deserialize a list of objects from a file.
     * Returns an empty list if deserialization fails or file doesn't exist.
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> deserializeList(String filePath) throws IOException, ClassNotFoundException {
        List<T> list = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return list;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            int size = ois.readInt();
            for (int i = 0; i < size; i++) {
                list.add((T) ois.readObject());
            }
        }

        return list;
    }

    /**
     * Check if a serialized file exists.
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * Delete a serialized file.
     */
    public static boolean deleteFile(String filePath) {
        return new File(filePath).delete();
    }

    /**
     * Get file size in bytes.
     */
    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }
}

