package technikum.at.tourplanner_swen2_team5.BL.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TourLogDAO;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TourLogServiceTest {

    @Mock
    private TourLogDAO tourLogDAO;

    @InjectMocks
    private TourLogService tourLogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTourLogs() {
        // Arrange
        TourLogModel log1 = new TourLogModel();
        TourLogModel log2 = new TourLogModel();
        List<TourLogModel> expectedLogs = Arrays.asList(log1, log2);

        when(tourLogDAO.findAll()).thenReturn(expectedLogs);

        // Act
        List<TourLogModel> actualLogs = tourLogService.getAllTourLogs();

        // Assert
        assertEquals(expectedLogs, actualLogs);
    }

    @Test
    void testGetTourLogById() {
        // Arrange
        TourLogModel expectedLog = new TourLogModel();
        String id = "some-uuid";
        when(tourLogDAO.findById(id)).thenReturn(Optional.of(expectedLog));

        // Act
        TourLogModel actualLog = tourLogService.getTourLogById(id);

        // Assert
        assertEquals(expectedLog, actualLog);
    }
}
