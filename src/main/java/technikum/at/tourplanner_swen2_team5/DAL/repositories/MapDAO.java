package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import org.hibernate.Session;
import org.hibernate.query.Query;
import technikum.at.tourplanner_swen2_team5.BL.models.TourMapModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;

import javax.persistence.EntityManager;
import java.util.List;

public class MapDAO extends BaseDAO<TourMapModel> {

    public List<TourMapModel> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM TourMapModel", TourMapModel.class).list();
        }
    }

    public TourMapModel findByTourId(String tourId) {
        try (Session session = getSession()) {
            Query<TourMapModel> query = session.createQuery("FROM TourMapModel WHERE tourId = :tourId", TourMapModel.class);
            query.setParameter("tourId", tourId);
            return query.uniqueResult();
        }
    }

    public void deleteByTourId(String tourId) {
        TourMapModel map = findByTourId(tourId);
        super.delete(map);
    }

    public void save(String tourId, String filename) {
        TourMapModel map = new TourMapModel();
        map.setTourId(tourId);
        map.setFilename(filename);
        super.save(map);
    }

    public void update(String tourId, String filename) {
        // It's better to find the existing TourMapModel and update it rather than creating a new one
        TourMapModel map = findByTourId(tourId);
        if (map == null) {
            map = new TourMapModel();
            map.setTourId(tourId);
        }
        map.setFilename(filename);
        super.save(map);
    }
}

