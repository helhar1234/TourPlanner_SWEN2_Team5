package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import org.hibernate.Session;
import org.hibernate.query.Query;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;

import java.util.List;

public class TourDAO extends BaseDAO<TourModel> {

    public TourModel findById(String id) {
        try (Session session = getSession()) {
            Query<TourModel> query = session.createQuery("FROM TourModel WHERE id = :id", TourModel.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
    }

    public TourModel findByName(String name) {
        try (Session session = getSession()) {
            Query<TourModel> query = session.createQuery("FROM TourModel WHERE name = :name", TourModel.class);
            query.setParameter("name", name);
            return query.uniqueResult();
        }
    }

    public List<TourModel> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM TourModel", TourModel.class).list();
        }
    }

    public List<TourModel> findByTransportType(String transportType) {
        try (Session session = getSession()) {
            Query<TourModel> query = session.createQuery("FROM TourModel WHERE transportType.name = :transportType", TourModel.class);
            query.setParameter("transportType", transportType);
            return query.list();
        }
    }

    @Override
    public void save(TourModel tour) {
        super.save(tour);
    }

    @Override
    public void update(TourModel tour) {
        super.update(tour);
    }

    @Override
    public void delete(TourModel tour) {
        super.delete(tour);
    }
}
