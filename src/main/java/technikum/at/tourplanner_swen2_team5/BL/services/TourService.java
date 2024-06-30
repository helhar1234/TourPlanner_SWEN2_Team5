package technikum.at.tourplanner_swen2_team5.BL.services;

import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TourDAO;

import java.util.List;
import java.util.Optional;

@Service
public class TourService implements ITourService {

    private final TourDAO tourDAO;
    private final EntityManager entityManager;

    @Autowired
    public TourService(EntityManager entityManager, TourDAO tourDAO) {
        this.entityManager = entityManager;
        this.tourDAO = tourDAO;
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
        Optional<TourModel> optionalTour = tourDAO.findById(id);
        return optionalTour.orElse(null);
    }

    public TourModel getTourByName(String name) {
        return tourDAO.findByName(name);
    }

    public void updateTour(TourModel tour) {
        tourDAO.save(tour);
    }

    @Transactional
    public List<TourModel> searchTours(String keyword) {
        SearchSession searchSession = Search.session(entityManager);
        return searchSession.search(TourModel.class)
                .where(f -> f.match().fields("name", "description").matching(keyword))
                .fetchHits(20); // Fetch the top 20 results
    }
}
