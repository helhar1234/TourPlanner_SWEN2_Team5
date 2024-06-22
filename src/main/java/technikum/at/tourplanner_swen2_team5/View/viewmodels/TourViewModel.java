package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.BL.services.MapService;
import technikum.at.tourplanner_swen2_team5.BL.services.TourService;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class TourViewModel {
    private static TourViewModel instance;
    private ObservableList<TourModel> tourModels = FXCollections.observableArrayList();
    private final TourService tourService = new TourService();
    private final MapService mapService = new MapService();

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

    public void addTour(TourModel tour) throws IOException {
        tour.setId(UUID.randomUUID().toString());
        tour.setDistance(MapRequester.getDistance(tour.getStart(), tour.getDestination()));
        tour.setTime(MapRequester.getTimeByTransportation(tour.getTransportType().getName(), tour.getDistance()));
        tourService.addTour(tour);
        loadTours();
    }

    public void deleteTour(TourModel tour) throws IOException {
        tourService.deleteTour(tour);
        mapService.deleteExistingMaps(tour.getId());
        mapService.deleteMapById(tour.getId());
        loadTours();
    }

    public TourModel getTourById(String id) {
        return tourService.getTourById(id);
    }

    public void updateTour(TourModel tour) throws IOException {
        tour.setDistance(MapRequester.getDistance(tour.getStart(), tour.getDestination()));
        tour.setTime(MapRequester.getTimeByTransportation(tour.getTransportType().getName(), tour.getDistance()));
        tourService.updateTour(tour);
        loadTours();
    }
}
