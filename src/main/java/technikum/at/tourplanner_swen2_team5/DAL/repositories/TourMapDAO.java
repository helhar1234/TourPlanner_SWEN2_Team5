package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import technikum.at.tourplanner_swen2_team5.BL.models.TourMapModel;

import java.util.List;

@Slf4j
public class TourMapDAO extends BaseDAO<TourMapModel> {

    public TourMapModel findById(String tourId) {
        try (Session session = getSession()) {
            Query<TourMapModel> query = session.createQuery("FROM TourMapModel WHERE tourId = :tourId", TourMapModel.class);
            query.setParameter("tourId", tourId);
            return query.uniqueResult();
        } catch (Exception e) {
            log.error("Failed to find TourMap by id {}", tourId, e);
            return null;
        }
    }

    public List<TourMapModel> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM TourMapModel", TourMapModel.class).list();
        } catch (Exception e) {
            log.error("Failed to find All TourMaps", e);
            return null;
        }
    }

    @Override
    public void save(TourMapModel tourMap) {
        try {
            super.save(tourMap);
        } catch (Exception e) {
            log.error("Failed to save TourMap with id {}", tourMap.getId(), e);
        }
    }

    @Override
    public void update(TourMapModel tourMap) {
        try {
            super.update(tourMap);
        } catch (Exception e) {
            log.error("Failed to update TourMap with id {}", tourMap.getId(), e);
        }
    }

    @Override
    public void delete(TourMapModel tourMap) {
        try {
            super.delete(tourMap);
        } catch (Exception e) {
            log.error("Failed to delete TourMap with id {}", tourMap.getId(), e);
        }
    }
}
