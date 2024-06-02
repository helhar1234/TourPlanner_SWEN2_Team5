package technikum.at.tourplanner_swen2_team5.BL.services;

import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TourDAO;

import java.util.List;

public class TourService implements ITourService {
    private final TourDAO tourDAO;

    public TourService() {
        this.tourDAO = new TourDAO();
    }

    @Override
    public List<TourModel> getAllTours() {
        return tourDAO.findAll();
    }

    @Override
    public void addTour(TourModel tour) {
        tourDAO.save(tour);
    }

    @Override
    public void deleteTour(TourModel tour) {
        tourDAO.delete(tour);
    }

    public TourModel getTourById(String id) {
        return tourDAO.findById(id);
    }

    public TourModel getTourByName(String name) {
        return tourDAO.findByName(name);
    }

    public void updateTour(TourModel tour) {
        tourDAO.update(tour);
    }

}
