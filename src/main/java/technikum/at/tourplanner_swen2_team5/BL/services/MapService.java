package technikum.at.tourplanner_swen2_team5.BL.services;

import technikum.at.tourplanner_swen2_team5.BL.models.TourMapModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.MapDAO;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MapService extends IMapService {
    private final MapDAO mapDAO;

    public MapService() {
        this.mapDAO = new MapDAO();
    }

    public void saveMap(TourModel tour) throws IOException {
        MapRequester.fetchMapImage(tour);
        mapDAO.save(tour.getId(), tour.getId() + "_map.png");
    }

    public void updateMap(TourModel tour) throws IOException {
        MapRequester.fetchMapImage(tour);
    }

    public boolean deleteExistingMaps(String tourId) throws IOException {
        String USER_DIR = System.getProperty("user.home") + "/TourPlanner/maps";
        File dir = new File(USER_DIR);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String filename = tourId + "_map.png";
        File file = new File(dir, filename);

        if (file.exists()) {
            Files.delete(file.toPath());
        }
        return true;
    }

    public void deleteMapById(String id) throws IOException {
        mapDAO.deleteByTourId(id);
    }
}

