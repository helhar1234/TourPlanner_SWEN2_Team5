package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import org.hibernate.Session;
import technikum.at.tourplanner_swen2_team5.BL.models.DifficultyModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.BaseDAO;

import java.util.List;

public class DifficultyDAO extends BaseDAO<TransportTypeModel> {

    public List<DifficultyModel> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM DifficultyModel ", DifficultyModel.class).list();
        }
    }
}
