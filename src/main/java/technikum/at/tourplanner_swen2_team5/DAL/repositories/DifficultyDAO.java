package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technikum.at.tourplanner_swen2_team5.BL.models.DifficultyModel;

@Repository
public interface DifficultyDAO extends JpaRepository<DifficultyModel, Integer> {
}
