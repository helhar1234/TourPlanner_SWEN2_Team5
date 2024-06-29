package technikum.at.tourplanner_swen2_team5.BL.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TourDAO;

import java.util.List;
import java.util.Optional;

@Service
public class TourService implements ITourService {
    @Autowired
    private TourDAO tourDAO;

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
        Optional<TourModel> optionalTour = tourDAO.findById(id);
        return optionalTour.orElse(null);
    }

    public TourModel getTourByName(String name) {
        return tourDAO.findByName(name);
    }

    public void updateTour(TourModel tour) {
        tourDAO.save(tour); // save kann sowohl zum Speichern als auch zum Aktualisieren verwendet werden
    }
}
