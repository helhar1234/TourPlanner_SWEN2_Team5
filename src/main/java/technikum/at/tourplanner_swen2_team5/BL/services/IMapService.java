package technikum.at.tourplanner_swen2_team5.BL.services;

import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;

import java.io.IOException;

public interface IMapService {
    void saveMap(TourModel tour) throws IOException;
    void updateMap(TourModel tour) throws IOException;
    boolean deleteExistingMaps(String tourId) throws IOException;
    void deleteMapById(String id) throws IOException;
}
