package technikum.at.tourplanner_swen2_team5.BL.services;

import technikum.at.tourplanner_swen2_team5.BL.models.TourMapModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.MapDAO;

import java.util.List;

public class MapService extends IMapService{
    private final MapDAO mapDAO;

    public MapService() {
        this.mapDAO = new MapDAO();
    }

    public List<TourMapModel> getAllMaps() {
        return mapDAO.findAll();
    }

    public void addMap(TourMapModel map) {
        mapDAO.save(map);
    }

    public void updateMap(TourMapModel map) {
        mapDAO.update(map);
    }

    public TourMapModel getMapByTourId(String tourId) {
        return mapDAO.findByTourId(tourId);
    }

    public void deleteMapByTourId(String tourId) {
        mapDAO.deleteByTourId(tourId);
    }
}
