package technikum.at.tourplanner_swen2_team5.viewmodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import technikum.at.tourplanner_swen2_team5.models.MapModel;

public class MapViewModel {
    private static MapViewModel instance;
    private final ObservableList<MapModel> mapModels = FXCollections.observableArrayList();

    private MapViewModel() {}

    public static MapViewModel getInstance() {
        if (instance == null) {
            instance = new MapViewModel();
        }
        return instance;
    }

    public ObservableList<MapModel> getMaps() {
        return mapModels;
    }

    public void addMap(MapModel map) {
        // Test Data
        mapModels.add(map);
        System.out.println("Map was created: " + map.getTourId());
    }

    public void updateMap(MapModel map) {
        for (int i = 0; i < mapModels.size(); i++) {
            if (mapModels.get(i).getTourId().equals(map.getTourId())) {
                mapModels.set(i, map);
                System.out.println("Map was updated: " + map.getTourId());
                break;
            }
        }
    }

    public MapModel getMapById(String tourId) {
        for (MapModel map : mapModels) {
            if (map.getTourId().equalsIgnoreCase(tourId)) {
                return map;
            }
        }
        return null;
    }

    public void deleteMapById(String tourId) {
        mapModels.removeIf(map -> map.getTourId().equals(tourId));
        System.out.println("Map was deleted: " + tourId);
    }
}
