package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import org.hibernate.Session;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;

import java.util.List;

public class TransportTypeDAO extends BaseDAO<TransportTypeModel> {

    public List<TransportTypeModel> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM TransportTypeModel", TransportTypeModel.class).list();
        }
    }
}

