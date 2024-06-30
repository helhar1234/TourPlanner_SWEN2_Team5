package technikum.at.tourplanner_swen2_team5.BL.services;

import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TourLogDAO;

import java.util.List;
import java.util.UUID;

@Service
public class TourLogService implements ITourLogService {
    private final TourLogDAO tourLogDAO;
    private final EntityManager entityManager;

    @Autowired
    public TourLogService(EntityManager entityManager, TourLogDAO tourLogDAO) {
        this.entityManager = entityManager;
        this.tourLogDAO = tourLogDAO;
    }

    @Override
    public List<TourLogModel> getAllTourLogs() {
        return tourLogDAO.findAll();
    }

    @Override
    public void addTourLog(TourLogModel tourLog) {
        tourLog.setId(UUID.randomUUID().toString());
        tourLogDAO.save(tourLog);
    }

    @Override
    public void deleteTourLog(TourLogModel tourLog) {
        tourLogDAO.delete(tourLog);
    }

    public TourLogModel getTourLogById(String id) {
        return tourLogDAO.findById(id).orElse(null);
    }

    public void updateTourLog(TourLogModel tourLog) {
        tourLogDAO.save(tourLog);
    }

    @Transactional
    public List<TourLogModel> searchTourLogs(String keyword) {
        SearchSession searchSession = Search.session(entityManager);
        return searchSession.search(TourLogModel.class)
                .where(f -> f.match().fields("date", "comment", "totalTime").matching(keyword))
                .fetchHits(20); // Fetch the top 20 results
    }
}
