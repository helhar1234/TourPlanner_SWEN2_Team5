package technikum.at.tourplanner_swen2_team5.BL.validation;

import org.springframework.stereotype.Service;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TourLogValidationService {

    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Map<String, String> validateTourLog(TourLogModel tourLog) {
        Map<String, String> errors = new HashMap<>();

        if (!isValidDate(tourLog.getDate())) {
            errors.put("date", "Invalid or empty date!");
        }

        if (!isValidTime(tourLog.getTimeHours(), tourLog.getTimeMinutes())) {
            errors.put("time", "Invalid or empty time.");
        }

        if (!isValidComment(tourLog.getComment())) {
            errors.put("comment", "Comment cannot be empty.");
        }

        if (!isValidDistance(tourLog.getDistance())) {
            errors.put("distance", "Invalid or empty distance. Distance must be a number.");
        }

        if (!isValidTotalTime(tourLog.getTotalTime())) {
            errors.put("totalTime", "Invalid or empty total time. Total time must be in format Xh Ymin.");
        }

        if (tourLog.getRating() < 1 || tourLog.getRating() > 10) {
            errors.put("rating", "Rating must be between 1 and 10");
        }

        if (tourLog.getDifficulty() == null) {
            errors.put("difficulty", "Difficulty cannot be empty.");
        }

        if (tourLog.getTransportType() == null) {
            errors.put("transportType", "Transport Type cannot be empty.");
        }

        return errors;
    }

    private boolean isValidDate(String date) {
        if (date == null || date.trim().isEmpty()) {
            return false;
        }
        try {
            if (!date.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                return false;
            }
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
            return !parsedDate.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidTime(int timeHours, int timeMinutes) {
        return timeHours >= 0 && timeHours < 24 && timeMinutes >= 0 && timeMinutes < 60 && (timeHours != 0 || timeMinutes != 0);
    }

    private boolean isValidComment(String comment) {
        return comment != null && !comment.trim().isEmpty();
    }

    private boolean isValidDistance(float distance) {
        return distance >= 0 && distance <= 30000;
    }

    private boolean isValidTotalTime(String totalTime) {
        if (totalTime == null || totalTime.trim().isEmpty()) {
            return false;
        }
        String[] parts = totalTime.split(" ");
        if (parts.length < 1 || parts.length > 2) {
            return false;
        }
        try {
            if (parts.length == 1) {
                return isValidHourOrMinute(parts[0]);
            }
            return isValidHourOrMinute(parts[0]) && isValidHourOrMinute(parts[1]);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidHourOrMinute(String part) {
        if (part.endsWith("h")) {
            Integer.parseInt(part.replace("h", ""));
            return true;
        }
        if (part.endsWith("min")) {
            Integer.parseInt(part.replace("min", ""));
            return true;
        }
        return false;
    }
}
