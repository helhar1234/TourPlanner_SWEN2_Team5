package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.services.TourLogService;

public class TourLogViewModel {
    private static TourLogViewModel instance;
    private final ObservableList<TourLogModel> tourLogModels = FXCollections.observableArrayList();
    private final TourLogService tourLogService = new TourLogService();

    private TourLogViewModel() {
        loadTourLogs();
    }

    public static synchronized TourLogViewModel getInstance() {
        if (instance == null) {
            instance = new TourLogViewModel();
        }
        return instance;
    }

    public ObservableList<TourLogModel> getTourLogs() {
        return tourLogModels;
    }

    private void loadTourLogs() {
        tourLogModels.setAll(tourLogService.getAllTourLogs());
    }

    public void addTourLog(TourLogModel tourLog) {
        tourLogService.addTourLog(tourLog);
        loadTourLogs();
    }

    public void deleteTourLogById(String id) {
        TourLogModel tourLog = tourLogService.getTourLogById(id);
        if (tourLog != null) {
            tourLogService.deleteTourLog(tourLog);
            loadTourLogs();
        }
    }

    public TourLogModel getTourLogById(String id) {
        return tourLogService.getTourLogById(id);
    }

    public void updateTourLog(TourLogModel tourLog) {
        tourLogService.updateTourLog(tourLog);
        loadTourLogs();

    }
}
