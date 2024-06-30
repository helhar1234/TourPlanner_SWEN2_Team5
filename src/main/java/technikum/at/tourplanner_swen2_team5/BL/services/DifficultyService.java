package technikum.at.tourplanner_swen2_team5.BL.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import technikum.at.tourplanner_swen2_team5.BL.models.DifficultyModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.DifficultyDAO;

import java.util.List;

@Service
public class DifficultyService implements IDifficultyService {
    private DifficultyDAO difficultyDAO;

    @Autowired
    public DifficultyService(DifficultyDAO difficultyDAO) {
        this.difficultyDAO = difficultyDAO;
    }

    public List<DifficultyModel> getAllDifficulties() {
        return difficultyDAO.findAll();
    }
}
