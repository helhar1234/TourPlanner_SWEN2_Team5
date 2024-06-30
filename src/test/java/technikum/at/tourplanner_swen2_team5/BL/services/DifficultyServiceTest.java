package technikum.at.tourplanner_swen2_team5.BL.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import technikum.at.tourplanner_swen2_team5.BL.models.DifficultyModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.DifficultyDAO;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DifficultyServiceTest {

    @Mock
    private DifficultyDAO difficultyDAO;

    @InjectMocks
    private DifficultyService difficultyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDifficulties() {
        // Arrange
        DifficultyModel difficulty1 = new DifficultyModel("Easy");
        DifficultyModel difficulty2 = new DifficultyModel("Medium");
        DifficultyModel difficulty3 = new DifficultyModel("Hard");

        List<DifficultyModel> expectedDifficulties = Arrays.asList(difficulty1, difficulty2, difficulty3);
        when(difficultyDAO.findAll()).thenReturn(expectedDifficulties);

        // Act
        List<DifficultyModel> actualDifficulties = difficultyService.getAllDifficulties();

        // Assert
        assertEquals(expectedDifficulties, actualDifficulties);
    }
}
