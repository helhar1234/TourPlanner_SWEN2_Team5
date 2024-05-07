package technikum.at.tourplanner_swen2_team5.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.models.TourModel;

public class TourViewModel {
    private static TourViewModel instance;
    private final ObservableList<TourModel> tourModels = FXCollections.observableArrayList();

    private TourViewModel() {}

    public static TourViewModel getInstance() {
        if (instance == null) {
            instance = new TourViewModel();
        }
        return instance;
    }

    public ObservableList<TourModel> getTours() {
        return tourModels;
    }

    public void addTour(TourModel tour) {
        tourModels.add(tour);
    }

    public String validateName(String name) {
        if (name == null) {
            return "Name cannot be empty";
        } else if (name.length() > 50) {
            return "Name is too long (max 50 characters)";
        } else if (nameExists(name)) {
            return "Name already exists";
        }
        return null; // kein Fehler
    }

    public String validateDescription(String description) {
        if (description == null) {
            return "Description cannot be empty";
        } else if (description.length() > 500) {
            return "Description is too long (max 500 characters)";
        }
        return null;
    }

    public String validateStart(String start) {
        if (start == null) {
            return "Start cannot be empty";
        } else if (start.length() > 100) {
            return "Start is too long (max 100 characters)";
        }
        return null;
    }

    public String validateDestination(String destination) {
        if (destination == null) {
            return "Destination cannot be empty";
        } else if (destination.length() > 100) {
            return "Destination is too long (max 100 characters)";
        }
        return null;
    }

    public String validateTransportationType(String transportationType) {
        if (transportationType == null) {
            return "Transportation Type is not selected";
        }
        return null;
    }

    private boolean nameExists(String name) {
        for (TourModel tour : tourModels) {
            if (tour.getName().equalsIgnoreCase(name)) {
                return true; // Name existiert bereits
            }
        }
        return false; // Name existiert nicht
    }
}

