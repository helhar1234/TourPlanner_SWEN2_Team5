package technikum.at.tourplanner_swen2_team5.BL.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import technikum.at.tourplanner_swen2_team5.BL.models.DifficultyModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class TourLogValidationServiceTest {

    private TourLogValidationService tourLogValidationService;

    @BeforeEach
    void setUp() {
        tourLogValidationService = new TourLogValidationService();
    }

    @Test
    void testValidTourLog() {
        TourLogModel tourLog = new TourLogModel();
        DifficultyModel difficulty = mock(DifficultyModel.class);
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tourLog.setDate("13.03.2024");
        tourLog.setTimeHours(2);
        tourLog.setTimeMinutes(30);
        tourLog.setComment("Nice tour");
        tourLog.setDistance(100.5f);
        tourLog.setTotalTime("2h 30min");
        tourLog.setRating(9);
        tourLog.setDifficulty(difficulty);
        tourLog.setTransportType(transportType);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals(0, errors.size());
    }

    @Test
    void testEmptyTourLog() {
        TourLogModel tourLog = new TourLogModel();

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);

        assertEquals("Invalid or empty date!", errors.get("date"));
        assertEquals("Invalid or empty time.", errors.get("time"));
        assertEquals("Comment cannot be empty.", errors.get("comment"));
        assertEquals("Invalid or empty total time. Total time must be in format Xh Ymin.", errors.get("totalTime"));
        assertEquals("Rating must be between 1 and 10", errors.get("rating"));
        assertEquals("Difficulty cannot be empty.", errors.get("difficulty"));
        assertEquals("Transport Type cannot be empty.", errors.get("transportType"));
    }

    @Test
    void testInvalidTourLog() {
        TourLogModel tourLog = new TourLogModel();

        tourLog.setDate("13.03.204");
        tourLog.setTimeHours(25);
        tourLog.setTimeMinutes(-1);
        tourLog.setComment("");
        tourLog.setDistance(-1);
        tourLog.setTotalTime("2h 30m");
        tourLog.setRating(-1);
        tourLog.setDifficulty(null);
        tourLog.setTransportType(null);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);

        assertEquals("Invalid or empty date!", errors.get("date"));
        assertEquals("Invalid or empty time.", errors.get("time"));
        assertEquals("Comment cannot be empty.", errors.get("comment"));
        assertEquals("Invalid or empty total time. Total time must be in format Xh Ymin.", errors.get("totalTime"));
        assertEquals("Invalid or empty distance. Distance must be a number.", errors.get("distance"));
        assertEquals("Rating must be between 1 and 10", errors.get("rating"));
        assertEquals("Difficulty cannot be empty.", errors.get("difficulty"));
        assertEquals("Transport Type cannot be empty.", errors.get("transportType"));
    }
}
