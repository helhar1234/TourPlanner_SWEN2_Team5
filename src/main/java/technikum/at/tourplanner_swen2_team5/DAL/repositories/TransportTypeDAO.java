package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;

import java.util.List;

@Slf4j
public class TransportTypeDAO extends BaseDAO<TransportTypeModel> {

    public List<TransportTypeModel> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM TransportTypeModel", TransportTypeModel.class).list();
        } catch (Exception e) {
            log.error("Failed to find all transportTypes", e);
            return null;
        }
    }
}

