package technikum.at.tourplanner_swen2_team5.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.models.TourModel;

import java.util.UUID;

public class TourLogViewModel {
    private static TourLogViewModel instance;
    private final ObservableList<TourLogModel> tourLogModels = FXCollections.observableArrayList();
    private TourLogViewModel() {}

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
}
