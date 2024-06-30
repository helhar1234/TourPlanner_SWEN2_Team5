package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.services.TourLogService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class TourLogViewModel {
    private final ObservableList<TourLogModel> tourLogModels = FXCollections.observableArrayList();
    private final TourLogService tourLogService;

    @Autowired
    public TourLogViewModel(TourLogService tourLogService) {
        this.tourLogService = tourLogService;
        loadTourLogs();
    }

    private void loadTourLogs() {
        tourLogModels.setAll(tourLogService.getAllTourLogs());
    }

    public FilteredList<TourLogModel> getTourLogsForTour(String tourId) {
        log.info("Loading tour logs for tour {}", tourId);
        FilteredList<TourLogModel> tourLogs = new FilteredList<>(tourLogModels);
        tourLogs.setPredicate(tourLog -> tourLog.getTour().getId().equals(tourId));
        log.debug("Loading tour logs for {} number {}", tourId, tourLogs.size());
        return tourLogs;
    }

    public int getTourLogCountForTour(String tourId) {
        List<TourLogModel> tourLogs = getTourLogsForTour(tourId);
        return tourLogs.size();
    }

    public void addTourLog(TourLogModel tourLog) {
        tourLogService.addTourLog(tourLog);
        loadTourLogs();
    }

    public void addTourLogFromUpload(TourLogModel tourLog) {
        tourLogService.addTourLog(tourLog);
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

    public List<TourLogModel> searchTourLogs(String keyword) {
        return tourLogService.searchTourLogs(keyword);
    }
}
