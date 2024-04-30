package technikum.at.tourplanner_swen2_team5.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.models.Tour;

public class TourViewModel {
    private static TourViewModel instance;
    private final ObservableList<Tour> tours = FXCollections.observableArrayList();

    // Privater Konstruktor für Singleton
    private TourViewModel() {}

    // Öffentliche Methode zur Abfrage der Singleton-Instanz
    public static TourViewModel getInstance() {
        if (instance == null) {
            instance = new TourViewModel();
        }
        return instance;
    }

    public ObservableList<Tour> getTours() {
        return tours;
    }

    public void addTour(Tour tour) {
        tours.add(tour);
    }
}
