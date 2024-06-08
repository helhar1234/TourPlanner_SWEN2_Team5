package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.services.MapService;

import java.io.IOException;

public class MapViewModel {
    private static MapViewModel instance;
    private final MapService mapService;

    private MapViewModel() {
        this.mapService = new MapService();
    }

    public static MapViewModel getInstance() {
        if (instance == null) {
            instance = new MapViewModel();
        }
        return instance;
    }

    public void addMap(TourModel tour) {
        try {
            if (mapService.deleteExistingMaps(tour.getId())) {
                mapService.saveMap(tour);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMap(TourModel tour) {
        try {
            if (mapService.deleteExistingMaps(tour.getId())) {
                mapService.updateMap(tour);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

