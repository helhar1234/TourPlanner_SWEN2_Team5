package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.services.MapService;
import technikum.at.tourplanner_swen2_team5.BL.services.TourLogService;
import technikum.at.tourplanner_swen2_team5.BL.services.TourService;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.util.ChildFriendlinessCalculator;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class TourViewModel {
    private static TourViewModel instance;
    private ObservableList<TourModel> tourModels = FXCollections.observableArrayList();
    private final TourService tourService = new TourService();
    private final MapService mapService = new MapService();
    private final TourLogService logService = new TourLogService();

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
        addTourPopularity(tours);
        addTourChildFriendliness(tours);
        tourModels.setAll(tours); // Convert ArrayList to ObservableList
    }

    private void addTourPopularity(List<TourModel> tours) {
        TourLogViewModel logViewModel = new TourLogViewModel();
        for (TourModel tour : tours) {
            tour.setPopularity(logViewModel.getTourLogCountForTour(tour.getId()));
        }
    }

    private void addTourChildFriendliness(List<TourModel> tours) {
        TourLogViewModel logViewModel = new TourLogViewModel();
        for (TourModel tour : tours) {
            tour.setChildFriendliness(ChildFriendlinessCalculator.calculateChildFriendliness(logViewModel.getTourLogsForTour(tour.getId()), tour.getTransportType()));
        }
    }

    public String addTour(TourModel tour) throws IOException {
        tour.setId(UUID.randomUUID().toString());
        tour.setDistance(MapRequester.getDistance(tour.getStart(), tour.getDestination()));
        tour.setTime(MapRequester.getTimeByTransportation(tour.getTransportType().getName(), tour.getDistance()));
        tourService.addTour(tour);
        loadTours();
        return tour.getId();
    }


    public void deleteTour(TourModel tour) throws IOException {
        mapService.deleteExistingMaps(tour.getId());
        mapService.deleteMapById(tour.getId());

        TourLogViewModel tourLogViewModel = TourLogViewModel.getInstance();
        List<TourLogModel> logs = tourLogViewModel.getTourLogsForTour(tour.getId());

        for (TourLogModel log : logs) {
            logService.deleteTourLog(log);
        }

        tourService.deleteTour(tour);
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
