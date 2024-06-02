package technikum.at.tourplanner_swen2_team5.BL.services;

import technikum.at.tourplanner_swen2_team5.BL.models.DifficultyModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.DifficultyDAO;

import java.util.List;

public class DifficultyService {
    private final DifficultyDAO difficultyDAO;

    public DifficultyService() {
        this.difficultyDAO = new DifficultyDAO();
    }

    public List<DifficultyModel> getAllDifficulties() {
        return difficultyDAO.findAll();
    }
}
