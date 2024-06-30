package technikum.at.tourplanner_swen2_team5.View.viewmodels;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.services.MapService;

import java.io.IOException;

@Slf4j
@Component
public class MapViewModel {

    private MapService mapService;

    @Autowired
    public MapViewModel(MapService mapService) {
        this.mapService = mapService;
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
                log.info("Map has been updated for tour with id {}", tour.getId());
            }
        } catch (Exception e) {
            log.error("Failed to update map for tour with id {}", tour.getId(), e);
        }
    }
}
