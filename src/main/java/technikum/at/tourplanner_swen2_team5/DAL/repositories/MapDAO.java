package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import org.hibernate.Session;
import org.hibernate.query.Query;
import technikum.at.tourplanner_swen2_team5.BL.models.TourMapModel;

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
        try (Session session = getSession()) {
            Query query = session.createQuery("DELETE FROM TourMapModel WHERE tourId = :tourId");
            query.setParameter("tourId", tourId);
            query.executeUpdate();
        }
    }
}
