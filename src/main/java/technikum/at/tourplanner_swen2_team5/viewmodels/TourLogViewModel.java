package technikum.at.tourplanner_swen2_team5.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.models.TourLogModel;

import java.time.LocalDate;
import java.util.UUID;

public class TourLogViewModel {
    private static TourLogViewModel instance;
    private final ObservableList<TourLogModel> tourLogModels = FXCollections.observableArrayList();

    private TourLogViewModel() {
    }

    public static TourLogViewModel getInstance() {
        if (instance == null) {
            instance = new TourLogViewModel();
        }
        return instance;
    }

    public ObservableList<TourLogModel> getTourLogs() {
        return tourLogModels;
    }

    public void addTourLog(TourLogModel tourLog) {
        tourLog.setId(UUID.randomUUID().toString());
        tourLogModels.add(tourLog);
        System.out.println("Tour was created: " + tourLog.getId());
    }

    public void updateTourLog(TourLogModel tourLog) {
        for (int i = 0; i < tourLogModels.size(); i++) {
            if (tourLogModels.get(i).getId().equals(tourLog.getId())) {
                tourLogModels.set(i, tourLog);
                System.out.println("Tour Log was updated: " + tourLog.getId());
                break;
            }
        }
    }

    public TourLogModel getTourLogById(String id) {
        for (TourLogModel tourLog : tourLogModels) {
            if (tourLog.getId().equalsIgnoreCase(id)) {
                return tourLog;
            }
        }
        return null;
    }

    public void deleteTourLogById(String id) {
        tourLogModels.removeIf(tourLog -> tourLog.getId().equals(id));
        System.out.println("Tour was deleted: " + id);
    }

    public String validateDate(LocalDate date) {
        if (date == null) {
            return "Date cannot be empty";
        }
        return null;
    }

    public String validateTime(String time) {
        if (time == null || time.isEmpty()) {
            return "Time cannot be empty";
        } else if (!time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
            return "Time must be written in the correct format (hh:mm)";
        }
        return null;
    }

    public String validateComment(String comment) {
        if (comment == null || comment.isEmpty()) {
            return "Comment cannot be empty";
        } else if (comment.length() > 500) {
            return "Comment is too long (max 500 characters)";
        }
        return null;
    }

    public String validateDifficulty(String difficulty) {
        if (difficulty == null || difficulty.isEmpty()) {
            return "Difficulty is not selected";
        }
        return null;
    }

    public String validateDistance(String distance) {
        if (distance == null || distance.isEmpty()) {
            return "Please enter the distance";
        } else if (!distance.matches("^-?\\d+(?:[.]\\d{1,2})?")) {
            return "Distance must be entered in the correct format (for example: 12.4)";
        }
        return null;
    }

    public String validateTotalTime(String totalTime) {
        if (totalTime == null || totalTime.isEmpty()) {
            return "Please enter the total time spent on the tour";
        } else if (!totalTime.matches("^(\\d+h)?(0?[0-9]|[1-5][0-9]|60)min")) {
            return "Total time must be entered in the correct format (for example: 1h23min)";
        }
        return null;
    }

    public String validateTransportationType(String transportationType) {
        if (transportationType == null || transportationType.isEmpty()) {
            return "Transportation Type is not selected";
        }
        return null;
    }

}
