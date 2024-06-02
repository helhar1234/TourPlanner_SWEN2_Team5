package technikum.at.tourplanner_swen2_team5.BL.validation;

import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.services.TourService;

import java.util.HashMap;
import java.util.Map;

public class TourValidationService {

    private final TourService tourService = new TourService();

    public Map<String, String> validateTour(TourModel tour) {
        Map<String, String> errors = new HashMap<>();

        if (tour.getName() == null || tour.getName().trim().isEmpty()) {
            errors.put("name", "Name cannot be empty");
        } else if (tour.getName().length() > 50) {
            errors.put("name", "Name is too long (max 50 characters)");
        }

        if (tour.getDescription() == null || tour.getDescription().isEmpty()) {
            errors.put("description", "Description cannot be empty");
        } else if (tour.getDescription().length() > 500) {
            errors.put("description", "Description is too long (max 500 characters)");
        }

        if (tour.getStart() == null || tour.getStart().isEmpty()) {
            errors.put("start", "Start location cannot be empty");
        } else if (tour.getStart().length() > 100) {
            errors.put("start", "Start is too long (max 100 characters)");
        }

        if (tour.getDestination() == null || tour.getDestination().isEmpty()) {
            errors.put("destination", "Destination cannot be empty");
        } else if (tour.getDestination().length() > 100) {
            errors.put("destination", "Destination is too long (max 100 characters)");
        }

        if (tour.getTransportType() == null) {
            errors.put("transportType", "Transportation type must be selected");
        }

        return errors;
    }

    public Map<String, String> validateNameExists(String newName, String oldName) {
        Map<String, String> errors = new HashMap<>();

        if (!newName.equals(oldName) && tourService.getTourByName(newName.trim()) != null) {
            errors.put("name", "Name already exists!");
        }

        return errors;
    }
}

