package technikum.at.tourplanner_swen2_team5.util;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Formatter {
    public String formatTime(int hours, int minutes) {
        hours = hours + (minutes / 60);
        minutes = minutes % 60;
        return String.format("%dh %02dmin", hours, minutes);
    }

    public static String formatDistance(double distance) {
        if (distance < 1.0) {
            // Umrechnung von Kilometern in Meter, wenn die Distanz kleiner als ein Kilometer ist
            int meters = (int) (distance * 1000);
            return String.format("%d m", meters);
        } else {
            // Runde auf zwei Dezimalstellen, wenn die Distanz mindestens ein Kilometer ist
            return String.format("%.2f km", distance);
        }
    }

    private static final DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter OUTPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String formatDate(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, INPUT_DATE_FORMATTER);
            return parsedDate.format(OUTPUT_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            // Falls das Datum nicht geparst werden kann, geben wir es einfach unverändert zurück
            return date;
        }
    }

    public static String formatDateReverse(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, OUTPUT_DATE_FORMATTER);
            return parsedDate.format(INPUT_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            // Falls das Datum nicht geparst werden kann, geben wir es einfach unverändert zurück
            return date;
        }
    }

    private static final DecimalFormat TIME_FORMATTER = new DecimalFormat("00");

    // Formats the time to "hh:mm"
    public static String formatTime_hhmm(String time) {
        if (time == null || time.isEmpty()) {
            return null;
        }

        String[] parts = time.split(":");
        int hours = 0;
        int minutes = 0;

        try {
            if (parts.length > 0) {
                hours = Integer.parseInt(parts[0]);
            }
            if (parts.length > 1) {
                minutes = Integer.parseInt(parts[1]);
            }
        } catch (NumberFormatException e) {
            // Handle invalid input gracefully
            return "00:00";
        }

        return TIME_FORMATTER.format(hours) + ":" + TIME_FORMATTER.format(minutes);
    }

    // Formats the time to "Xh Ymin"
    public static String formatTime_hm(String time) {
        if (time == null || time.isEmpty()) {
            return null;
        }

        String[] parts = time.split(" ");
        int hours = 0;
        int minutes = 0;

        try {
            if (parts.length > 0 && isValidHourOrMinute(parts[0])) {
                if (parts[0].endsWith("h")) {
                    hours = Integer.parseInt(parts[0].replace("h", "").trim());
                } else if (parts[0].endsWith("min")) {
                    minutes = Integer.parseInt(parts[0].replace("min", "").trim());
                } else {
                    hours = Integer.parseInt(parts[0].trim());
                }
            }
            if (parts.length > 1 && isValidHourOrMinute(parts[1])) {
                if (parts[1].endsWith("min")) {
                    minutes = Integer.parseInt(parts[1].replace("min", "").trim());
                } else {
                    minutes = Integer.parseInt(parts[1].trim());
                }
            }
        } catch (NumberFormatException e) {
            return "0h 0min";
        }

        // Ensure both hours and minutes are included in the output
        return String.format("%dh %dmin", hours, minutes);
    }

    private static boolean isValidHourOrMinute(String part) {
        if (part == null || part.isEmpty()) {
            return false;
        }
        try {
            if (part.endsWith("h")) {
                Integer.parseInt(part.replace("h", "").trim());
                return true;
            }
            if (part.endsWith("min")) {
                Integer.parseInt(part.replace("min", "").trim());
                return true;
            }
            Integer.parseInt(part.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
