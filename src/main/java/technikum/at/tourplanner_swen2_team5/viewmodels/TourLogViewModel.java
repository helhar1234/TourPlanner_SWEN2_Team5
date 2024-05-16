package technikum.at.tourplanner_swen2_team5.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.models.TourLogModel;

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
}
