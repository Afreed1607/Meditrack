package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for date and time operations.
 * Provides methods for formatting, parsing, and calculating date differences.
 * Centralizes all date-related logic.
 */
public class DateUtil {

    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);

    private static final DateTimeFormatter TIME_FORMATTER =
        DateTimeFormatter.ofPattern(Constants.TIME_FORMAT);

    private static final DateTimeFormatter DATETIME_FORMATTER =
        DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT);

    private DateUtil() {
        throw new AssertionError("DateUtil class cannot be instantiated");
    }

    /**
     * Format LocalDate to string using application default format.
     */
    public static String formatDate(LocalDate date) {
        if (date == null) return "N/A";
        return date.format(DATE_FORMATTER);
    }

    /**
     * Format LocalDateTime to date string (ignoring time).
     */
    public static String formatDateOnly(LocalDateTime dateTime) {
        if (dateTime == null) return "N/A";
        return dateTime.toLocalDate().format(DATE_FORMATTER);
    }

    /**
     * Format LocalDateTime to time string (ignoring date).
     */
    public static String formatTimeOnly(LocalDateTime dateTime) {
        if (dateTime == null) return "N/A";
        return dateTime.format(TIME_FORMATTER);
    }

    /**
     * Format LocalDateTime to full datetime string.
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "N/A";
        return dateTime.format(DATETIME_FORMATTER);
    }

    /**
     * Parse date string to LocalDate.
     */
    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DATE_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parse datetime string to LocalDateTime.
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, DATETIME_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Calculate number of days between two dates.
     */
    public static long daysBetween(LocalDate from, LocalDate to) {
        return ChronoUnit.DAYS.between(from, to);
    }

    /**
     * Calculate number of days between two datetimes.
     */
    public static long daysBetween(LocalDateTime from, LocalDateTime to) {
        return ChronoUnit.DAYS.between(from, to);
    }

    /**
     * Calculate number of hours between two datetimes.
     */
    public static long hoursBetween(LocalDateTime from, LocalDateTime to) {
        return ChronoUnit.HOURS.between(from, to);
    }

    /**
     * Check if a date is in the past.
     */
    public static boolean isPastDate(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    /**
     * Check if a datetime is in the past.
     */
    public static boolean isPastDateTime(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }

    /**
     * Check if a date is in the future.
     */
    public static boolean isFutureDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    /**
     * Check if a datetime is in the future.
     */
    public static boolean isFutureDateTime(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }

    /**
     * Get current date.
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * Get current datetime.
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    /**
     * Add days to a date.
     */
    public static LocalDate addDays(LocalDate date, int days) {
        return date.plusDays(days);
    }

    /**
     * Check if year is leap year.
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
}

