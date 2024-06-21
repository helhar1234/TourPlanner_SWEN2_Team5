package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.services.MapService;

import java.io.IOException;

@Slf4j
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
                log.info("Map has been saved for tour with id {}", tour.getId());
            }
        } catch (Exception e) {
           log.error("Failed to save map for tour with id {}", tour.getId(), e);
        }
    }

    public void updateMap(TourModel tour) {
        try {
            if (mapService.deleteExistingMaps(tour.getId())) {
                mapService.updateMap(tour);
                log.info("Map has been deleted for tour with id {}", tour.getId());
            }
        } catch (Exception e) {
            log.error("Failed to delete map for tour with id {}", tour.getId(), e);
        }
    }
}

