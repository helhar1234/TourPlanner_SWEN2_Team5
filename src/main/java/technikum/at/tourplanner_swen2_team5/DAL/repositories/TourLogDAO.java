package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;

import java.util.List;

@Slf4j
public class TourLogDAO extends BaseDAO<TourLogModel> {

    public TourLogModel findById(String id) {
        try (Session session = getSession()) {
            Query<TourLogModel> query = session.createQuery("FROM TourLogModel WHERE id = :id", TourLogModel.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            log.error("Failed to find TourLog by id {}", id, e);
            return null;
        }
    }

    public List<TourLogModel> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM TourLogModel", TourLogModel.class).list();
        } catch (Exception e) {
            log.error("Failed to find all TourLog", e);
            return null;
        }
    }

    public void save(TourLogModel tourLog) {
        try {
            super.save(tourLog);
        } catch (Exception e) {
            log.error("Failed to save TourLog with id {}", tourLog.getId(), e);
        }
    }

    public void update(TourLogModel tourLog) {
        try {
            super.update(tourLog);
        } catch (Exception e) {
            log.error("Failed to update TourLog with id {}", tourLog.getId(), e);
        }
    }

    public void delete(TourLogModel tourLog) {
        try {
            super.delete(tourLog);
        } catch (Exception e) {
            log.error("Failed to delete TourLog with id {}", tourLog.getId(), e);
        }
    }
}
