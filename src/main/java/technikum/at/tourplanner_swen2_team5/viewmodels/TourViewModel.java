package technikum.at.tourplanner_swen2_team5.viewmodels;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import java.time.Duration;
import technikum.at.tourplanner_swen2_team5.models.TourImage;
import technikum.at.tourplanner_swen2_team5.models.TourModel;

import java.util.UUID;

public class TourViewModel {
    private static TourViewModel instance;
    private final ObservableList<TourModel> tourModels = FXCollections.observableArrayList();

    private TourViewModel() {
        // Test Data
        int duration = 83;
        TourModel tour = new TourModel("Test Tour", "Beschreibung...", "Wien", "Graz", "Bike", 100, duration);
        tour.setId(UUID.randomUUID().toString());
        TourImage map = new TourImage(tour.getId(), "map-placeholder.png");
        tourModels.add(tour);


        // TEST DATA 2.0 with delay DELETE LATER!
        Task<Void> delayTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(15000); // Warte 15 Sekunden
                } catch (InterruptedException e) {
                }

                Platform.runLater(() -> {
                    TourModel secondTour = new TourModel("Zweite Test Tour", "Weitere Beschreibung...", "Salzburg", "Innsbruck", "Bike", 2755, duration);
                    secondTour.setId(UUID.randomUUID().toString());
                    TourImage map = new TourImage(secondTour.getId(), "map-placeholder.png");
                    tourModels.add(secondTour);
                });
                return null;
            }
        };
        new Thread(delayTask).start();
    }

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
        tour.setId(UUID.randomUUID().toString());
        tourModels.add(tour);
        System.out.println("Tour was created: " + tour.getId());
    }

    public void updateTour(TourModel tour) {
        for (int i = 0; i < tourModels.size(); i++) {
            if (tourModels.get(i).getId().equals(tour.getId())) {
                tourModels.set(i, tour);
                System.out.println("Tour was updated: " + tour.getId());
                break;
            }
        }
    }


    public TourModel getTourById(String id){
        for (TourModel tour : tourModels) {
            if (tour.getId().equalsIgnoreCase(id)) {
                return tour;
            }
        }
        return null;
    }

    public void deleteTourById(String id) {
        tourModels.removeIf(tour -> tour.getId().equals(id));
        System.out.println("Tour was deleted: " + id);
    }


    public String validateName(String name, String currentId) {
        if (name == null || name.trim().isEmpty()) {
            return "Name cannot be empty";
        } else if (name.length() > 50) {
            return "Name is too long (max 50 characters)";
        } else if (nameExists(name) && !isCurrentTourName(name, currentId)) {
            return "Name already exists";
        }
        return null;
    }

    private boolean isCurrentTourName(String name, String currentId) {
        TourModel existingTour = getTourById(currentId);
        return existingTour != null && existingTour.getName().equals(name);
    }

    public String validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            return "Description cannot be empty";
        } else if (description.length() > 500) {
            return "Description is too long (max 500 characters)";
        }
        return null;
    }

    public String validateStart(String start) {
        if (start == null || start.isEmpty()) {
            return "Start cannot be empty";
        } else if (start.length() > 100) {
            return "Start is too long (max 100 characters)";
        }
        return null;
    }

    public String validateDestination(String destination) {
        if (destination == null || destination.isEmpty()) {
            return "Destination cannot be empty";
        } else if (destination.length() > 100) {
            return "Destination is too long (max 100 characters)";
        }
        return null;
    }

    public String validateTransportationType(String transportationType) {
        if (transportationType == null || transportationType.isEmpty()) {
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

    public String formatTime(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        return String.format("%dh %02dmin", hours, remainingMinutes);
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


}

