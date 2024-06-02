package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.BL.services.TourService;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;

import java.util.List;
import java.util.UUID;

public class TourViewModel {
    private static TourViewModel instance;
    private ObservableList<TourModel> tourModels = FXCollections.observableArrayList();
    private final TourService tourService = new TourService();

    private TourViewModel() {
        loadTours();
    }

    public static synchronized TourViewModel getInstance() {
        if (instance == null) {
            instance = new TourViewModel();
        }
        return instance;
    }

    public ObservableList<TourModel> getTours() {
        loadTours();
        return tourModels;
    }

    private void loadTours() {
        List<TourModel> tours = tourService.getAllTours();
        tourModels.setAll(tours); // Convert ArrayList to ObservableList
    }

    public void addTour(TourModel tour) {
        tour.setId(UUID.randomUUID().toString());
        tourService.addTour(tour);
        loadTours();
    }

    public void deleteTour(TourModel tour) {
        tourService.deleteTour(tour);
        loadTours();
    }

    public TourModel getTourById(String id) {
        return tourService.getTourById(id);
    }

    public void updateTour(TourModel tour) {
        tourService.updateTour(tour);
        loadTours();
    }


}
