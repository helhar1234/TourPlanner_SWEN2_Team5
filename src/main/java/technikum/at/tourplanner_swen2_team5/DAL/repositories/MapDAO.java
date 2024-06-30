package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import technikum.at.tourplanner_swen2_team5.BL.models.TourMapModel;

import java.util.Optional;

@Repository
public interface MapDAO extends JpaRepository<TourMapModel, Integer> {
    Optional<TourMapModel> findByTourId(String tourId);
    void deleteByTourId(String tourId);
}
