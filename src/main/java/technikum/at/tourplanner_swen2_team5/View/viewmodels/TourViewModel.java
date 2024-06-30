package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.services.MapService;
import technikum.at.tourplanner_swen2_team5.BL.services.TourLogService;
import technikum.at.tourplanner_swen2_team5.BL.services.TourService;
import technikum.at.tourplanner_swen2_team5.util.ChildFriendlinessCalculator;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class TourViewModel {
    private static TourViewModel instance;
    private ObservableList<TourModel> tourModels = FXCollections.observableArrayList();

    private TourViewModel() {
        loadTours();
    }
  
    private final ObservableList<TourModel> tourModels = FXCollections.observableArrayList();
    @Autowired
    private TourService tourService;
    @Autowired
    private MapService mapService;
    @Autowired
    private TourLogViewModel logViewModel;
  @Autowired
    private TourLogService tourLogService;

    @Autowired
    public TourViewModel(TourService tourService, MapService mapService, TourLogViewModel logViewModel, TourLogService tourLogService) {
        this.tourService = tourService;
        this.mapService = mapService;
        this.logViewModel = logViewModel;
        this.tourLogService = tourLogService;
    }

    private void loadTours() {
        List<TourModel> tours = tourService.getAllTours();
        addTourPopularity(tours);
        addTourChildFriendliness(tours);
        tourModels.setAll(tours); // Convert ArrayList to ObservableList
    }

    public ObservableList<TourModel> getTours() {
        loadTours();
        return tourModels;
    }

    private void addTourPopularity(List<TourModel> tours) {
        for (TourModel tour : tours) {
            tour.setPopularity(logViewModel.getTourLogCountForTour(tour.getId()));
            log.info("Tour popularity: " + tour.getPopularity());
        }
    }

    private void addTourChildFriendliness(List<TourModel> tours) {
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

    public List<TourModel> searchTours(String keyword) {
        return tourService.searchTours(keyword);
    }
}
