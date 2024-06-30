package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;

@Repository
public interface TransportTypeDAO extends JpaRepository<TransportTypeModel, Integer> {
}
