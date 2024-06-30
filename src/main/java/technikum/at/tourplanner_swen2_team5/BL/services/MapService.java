package technikum.at.tourplanner_swen2_team5.BL.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technikum.at.tourplanner_swen2_team5.BL.models.TourMapModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TourMapDAO;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class MapService implements IMapService {
    private TourMapDAO tourMapDAO;
    private MapRequester mapRequester;

    @Autowired
    public MapService(TourMapDAO tourMapDAO, MapRequester mapRequester) {
        this.tourMapDAO = tourMapDAO;
        this.mapRequester = mapRequester;
    }

    @Override
    public void saveMap(TourModel tour) throws IOException {
        mapRequester.fetchMapImage(tour);
        tourMapDAO.save(new TourMapModel(tour.getId(), tour.getId() + "_map.png"));
    }

    @Override
    public void updateMap(TourModel tour) throws IOException {
        mapRequester.fetchMapImage(tour);
        TourMapModel map = tourMapDAO.findByTourId(tour.getId()).orElse(new TourMapModel(tour.getId(), tour.getId() + "_map.png"));
        map.setFilename(tour.getId() + "_map.png");
        tourMapDAO.save(map);
    }

    @Override
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

    @Override
    public void deleteMapById(String id) {
        tourMapDAO.findByTourId(id).ifPresent(tourMapDAO::delete);
    }
}
