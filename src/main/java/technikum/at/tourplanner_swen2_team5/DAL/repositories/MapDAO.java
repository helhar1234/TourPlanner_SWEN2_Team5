package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import technikum.at.tourplanner_swen2_team5.BL.models.TourMapModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MapDAO extends BaseDAO<TourMapModel> {

    public List<TourMapModel> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM TourMapModel", TourMapModel.class).list();
        } catch (Exception e) {
            log.error("Failed to find all TourMaps", e);
            return null;
        }
    }

    public TourMapModel findByTourId(String tourId) {
        try (Session session = getSession()) {
            Query<TourMapModel> query = session.createQuery("FROM TourMapModel WHERE tourId = :tourId", TourMapModel.class);
            query.setParameter("tourId", tourId);
            return query.uniqueResult();
        } catch (Exception e) {
            log.error("Failed to find TourMap by id {}", tourId, e);
            return null;
        }
    }

    public void deleteByTourId(String tourId) {
        try {
            TourMapModel map = findByTourId(tourId);
            super.delete(map);
        } catch (Exception e) {
            log.error("Failed to delete TourMap by TourId {}", tourId, e);
        }
    }

    public void save(String tourId, String filename) {
        try {
            TourMapModel map = new TourMapModel();
            map.setTourId(tourId);
            map.setFilename(filename);
            super.save(map);
        } catch (Exception e) {
            log.error("Failed to save TourMap by TourId {}", tourId, e);
        }
    }

    public void update(String tourId, String filename) {
        // It's better to find the existing TourMapModel and update it rather than creating a new one
        try {
            TourMapModel map = findByTourId(tourId);
            if (map == null) {
                map = new TourMapModel();
                map.setTourId(tourId);
            }
            map.setFilename(filename);
            super.save(map);
        } catch (Exception e) {
            log.error("Failed to update TourMap by TourId {}", tourId, e);
        }
    }
}

