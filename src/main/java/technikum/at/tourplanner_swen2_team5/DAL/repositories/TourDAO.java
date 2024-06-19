package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;

import java.util.List;

@Slf4j
public class TourDAO extends BaseDAO<TourModel> {

    public TourModel findById(String id) {
        try (Session session = getSession()) {
            Query<TourModel> query = session.createQuery("FROM TourModel WHERE id = :id", TourModel.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            log.error("Failed to find tour with id {}", id, e);
            return null;
        }
    }

    public TourModel findByName(String name) {
        try (Session session = getSession()) {
            Query<TourModel> query = session.createQuery("FROM TourModel WHERE name = :name", TourModel.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        } catch (Exception e) {
            log.error("Failed to find tour with name {}", name, e);
            return null;
        }
    }

    public List<TourModel> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM TourModel", TourModel.class).list();
        } catch (Exception e) {
            log.error("Failed to find all tours from database", e);
            return null;
        }
    }

    public List<TourModel> findByTransportType(String transportType) {
        try (Session session = getSession()) {
            Query<TourModel> query = session.createQuery("FROM TourModel WHERE transportType.name = :transportType", TourModel.class);
            query.setParameter("transportType", transportType);
            return query.list();
        } catch (Exception e) {
            log.error("Failed to find tours from database by transporttype {}", transportType, e);
            return null;
        }
    }

    @Override
    public void save(TourModel tour) {
        try {
            super.save(tour);
        } catch (Exception e) {
            log.error("Failed to save tour with id {}", tour.getId(), e);
        }
    }

    @Override
    public void update(TourModel tour) {
        try {
            super.update(tour);
        } catch (Exception e) {
            log.error("Failed to update tour with id {}", tour.getId(), e);
        }
    }

    @Override
    public void delete(TourModel tour) {
        try {
            super.delete(tour);
        } catch (Exception e) {
            log.error("Failed to delete tour with id {}", tour.getId(), e);
        }
    }
}
