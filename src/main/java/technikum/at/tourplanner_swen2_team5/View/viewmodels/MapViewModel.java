package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.BL.models.TourMapModel;
import technikum.at.tourplanner_swen2_team5.BL.services.MapService;

public class MapViewModel {
    private static MapViewModel instance;
    private final ObservableList<TourMapModel> mapModels = FXCollections.observableArrayList();
    private final MapService mapService = new MapService();

    private MapViewModel() {
        loadMaps();
    }

    public static MapViewModel getInstance() {
        if (instance == null) {
            instance = new MapViewModel();
        }
        return instance;
    }

    public ObservableList<TourMapModel> getMaps() {
        return mapModels;
    }

    private void loadMaps() {
        mapModels.setAll(mapService.getAllMaps());
    }

    public void addMap(TourMapModel map) {
        mapService.addMap(map);
        loadMaps();
        System.out.println("Map was created: " + map.getTourId());
    }

    public void updateMap(TourMapModel map) {
        mapService.updateMap(map);
        loadMaps();
        System.out.println("Map was updated: " + map.getTourId());
    }

    public TourMapModel getMapById(String tourId) {
        return mapService.getMapByTourId(tourId);
    }

    public void deleteMapById(String tourId) {
        mapService.deleteMapByTourId(tourId);
        loadMaps();
        System.out.println("Map was deleted: " + tourId);
    }
}
