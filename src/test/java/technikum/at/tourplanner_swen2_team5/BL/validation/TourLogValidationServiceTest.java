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
    void testEmptyDate() {
        TourLogModel tourLog = new TourLogModel();
        DifficultyModel difficulty = mock(DifficultyModel.class);
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tourLog.setDate("");
        tourLog.setTimeHours(2);
        tourLog.setTimeMinutes(30);
        tourLog.setComment("Comment");
        tourLog.setDistance(100.5f);
        tourLog.setTotalTime("2h 30min");
        tourLog.setRating(9);
        tourLog.setDifficulty(difficulty);
        tourLog.setTransportType(transportType);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals("Invalid or empty date!", errors.get("date"));
    }

    @Test
    void testInvalidDate() {
        TourLogModel tourLog = new TourLogModel();
        DifficultyModel difficulty = mock(DifficultyModel.class);
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tourLog.setDate("13.03.204");
        tourLog.setTimeHours(2);
        tourLog.setTimeMinutes(30);
        tourLog.setComment("Nice tour");
        tourLog.setDistance(100.5f);
        tourLog.setTotalTime("2h 30min");
        tourLog.setRating(9);
        tourLog.setDifficulty(difficulty);
        tourLog.setTransportType(transportType);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals("Invalid or empty date!", errors.get("date"));
    }

    @Test
    void testInvalidTimeHours() {
        TourLogModel tourLog = new TourLogModel();
        DifficultyModel difficulty = mock(DifficultyModel.class);
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tourLog.setDate("13.03.2024");
        tourLog.setTimeHours(25);
        tourLog.setTimeMinutes(30);
        tourLog.setComment("Nice tour");
        tourLog.setDistance(100.5f);
        tourLog.setTotalTime("2h 30min");
        tourLog.setRating(9);
        tourLog.setDifficulty(difficulty);
        tourLog.setTransportType(transportType);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals("Invalid or empty time.", errors.get("time"));
    }

    @Test
    void testInvalidTimeMinutes() {
        TourLogModel tourLog = new TourLogModel();
        DifficultyModel difficulty = mock(DifficultyModel.class);
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tourLog.setDate("13.03.2024");
        tourLog.setTimeHours(23);
        tourLog.setTimeMinutes(-1);
        tourLog.setComment("Nice tour");
        tourLog.setDistance(100.5f);
        tourLog.setTotalTime("2h 30min");
        tourLog.setRating(9);
        tourLog.setDifficulty(difficulty);
        tourLog.setTransportType(transportType);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals("Invalid or empty time.", errors.get("time"));
    }

    @Test
    void testEmptyComment() {
        TourLogModel tourLog = new TourLogModel();
        DifficultyModel difficulty = mock(DifficultyModel.class);
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tourLog.setDate("13.03.2024");
        tourLog.setTimeHours(23);
        tourLog.setTimeMinutes(30);
        tourLog.setComment("");
        tourLog.setDistance(100.5f);
        tourLog.setTotalTime("2h 30min");
        tourLog.setRating(9);
        tourLog.setDifficulty(difficulty);
        tourLog.setTransportType(transportType);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals("Comment cannot be empty.", errors.get("comment"));
    }

    @Test
    void testInvalidDistance() {
        TourLogModel tourLog = new TourLogModel();
        DifficultyModel difficulty = mock(DifficultyModel.class);
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tourLog.setDate("13.03.2024");
        tourLog.setTimeHours(23);
        tourLog.setTimeMinutes(30);
        tourLog.setComment("Nice tour");
        tourLog.setDistance(-1);
        tourLog.setTotalTime("2h 30min");
        tourLog.setRating(9);
        tourLog.setDifficulty(difficulty);
        tourLog.setTransportType(transportType);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals("Invalid or empty distance. Distance must be a number.", errors.get("distance"));
    }

    @Test
    void testEmptyTotalTime() {
        TourLogModel tourLog = new TourLogModel();
        DifficultyModel difficulty = mock(DifficultyModel.class);
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tourLog.setDate("13.03.2024");
        tourLog.setTimeHours(23);
        tourLog.setTimeMinutes(30);
        tourLog.setComment("Nice tour");
        tourLog.setDistance(100.5f);
        tourLog.setTotalTime("");
        tourLog.setRating(9);
        tourLog.setDifficulty(difficulty);
        tourLog.setTransportType(transportType);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals("Invalid or empty total time. Total time must be in format Xh Ymin.", errors.get("totalTime"));
    }

    @Test
    void testInvalidTotalTime() {
        TourLogModel tourLog = new TourLogModel();
        DifficultyModel difficulty = mock(DifficultyModel.class);
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tourLog.setDate("13.03.2024");
        tourLog.setTimeHours(23);
        tourLog.setTimeMinutes(30);
        tourLog.setComment("Nice tour");
        tourLog.setDistance(100.5f);
        tourLog.setTotalTime("2h 30m");
        tourLog.setRating(9);
        tourLog.setDifficulty(difficulty);
        tourLog.setTransportType(transportType);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals("Invalid or empty total time. Total time must be in format Xh Ymin.", errors.get("totalTime"));
    }

    @Test
    void testInvalidRating() {
        TourLogModel tourLog = new TourLogModel();
        DifficultyModel difficulty = mock(DifficultyModel.class);
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tourLog.setDate("01.01.2022");
        tourLog.setTimeHours(2);
        tourLog.setTimeMinutes(30);
        tourLog.setComment("Nice tour");
        tourLog.setDistance(100.5f);
        tourLog.setTotalTime("2h 30min");
        tourLog.setRating(11);
        tourLog.setDifficulty(difficulty);
        tourLog.setTransportType(transportType);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals("Rating must be between 1 and 10", errors.get("rating"));
    }

    @Test
    void testEmptyDifficulty() {
        TourLogModel tourLog = new TourLogModel();
        TransportTypeModel transportType = mock(TransportTypeModel.class);

        tourLog.setDate("13.03.2024");
        tourLog.setTimeHours(23);
        tourLog.setTimeMinutes(30);
        tourLog.setComment("Nice tour");
        tourLog.setDistance(100.5f);
        tourLog.setTotalTime("2h 30min");
        tourLog.setRating(9);
        tourLog.setDifficulty(null);
        tourLog.setTransportType(transportType);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals("Difficulty cannot be empty.", errors.get("difficulty"));
    }

    @Test
    void testEmptyTransportType() {
        TourLogModel tourLog = new TourLogModel();
        DifficultyModel difficulty = mock(DifficultyModel.class);

        tourLog.setDate("13.03.2024");
        tourLog.setTimeHours(2);
        tourLog.setTimeMinutes(30);
        tourLog.setComment("Nice tour");
        tourLog.setDistance(100.5f);
        tourLog.setTotalTime("2h 30min");
        tourLog.setRating(5);
        tourLog.setDifficulty(difficulty);
        tourLog.setTransportType(null);

        Map<String, String> errors = tourLogValidationService.validateTourLog(tourLog);
        assertEquals("Transport Type cannot be empty.", errors.get("transportType"));
    }
}
