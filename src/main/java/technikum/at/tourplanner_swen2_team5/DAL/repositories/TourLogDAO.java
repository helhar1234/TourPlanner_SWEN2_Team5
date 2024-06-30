package technikum.at.tourplanner_swen2_team5.DAL.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;

import java.util.Optional;

@Repository
public interface TourLogDAO extends JpaRepository<TourLogModel, String> {
    @NonNull
    Optional<TourLogModel> findById(@NonNull String id);

    void deleteById(@NonNull String id);
}
