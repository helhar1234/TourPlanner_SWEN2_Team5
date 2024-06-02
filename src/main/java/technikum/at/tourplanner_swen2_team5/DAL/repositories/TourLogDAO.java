package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import org.hibernate.Session;
import org.hibernate.query.Query;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;

import java.util.List;
import java.util.UUID;

public class TourLogDAO extends BaseDAO<TourLogModel> {

    public TourLogModel findById(String id) {
        try (Session session = getSession()) {
            Query<TourLogModel> query = session.createQuery("FROM TourLogModel WHERE id = :id", TourLogModel.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
    }

    public List<TourLogModel> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM TourLogModel", TourLogModel.class).list();
        }
    }

    public void save(TourLogModel tourLog) {
        super.save(tourLog);
    }

    public void update(TourLogModel tourLog) {
        super.update(tourLog);
    }

    public void delete(TourLogModel tourLog) {
        super.delete(tourLog);
    }
}
