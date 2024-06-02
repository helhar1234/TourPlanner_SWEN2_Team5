package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import org.hibernate.Session;
import org.hibernate.query.Query;
import technikum.at.tourplanner_swen2_team5.BL.models.TourMapModel;

import java.util.List;

public class TourMapDAO extends BaseDAO<TourMapModel> {

    public TourMapModel findById(String tourId) {
        try (Session session = getSession()) {
            Query<TourMapModel> query = session.createQuery("FROM TourMapModel WHERE tourId = :tourId", TourMapModel.class);
            query.setParameter("tourId", tourId);
            return query.uniqueResult();
        }
    }

    public List<TourMapModel> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM TourMapModel", TourMapModel.class).list();
        }
    }

    @Override
    public void save(TourMapModel tourMap) {
        super.save(tourMap);
    }

    @Override
    public void update(TourMapModel tourMap) {
        super.update(tourMap);
    }

    @Override
    public void delete(TourMapModel tourMap) {
        super.delete(tourMap);
    }
}
