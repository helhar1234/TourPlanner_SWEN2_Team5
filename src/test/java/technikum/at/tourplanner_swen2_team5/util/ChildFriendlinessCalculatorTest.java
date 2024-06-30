package technikum.at.tourplanner_swen2_team5.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import technikum.at.tourplanner_swen2_team5.BL.models.DifficultyModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChildFriendlinessCalculatorTest {

    private List<TourLogModel> tourLogs;

    @BeforeEach
    void setUp() {
        // Set up mock data
        DifficultyModel difficulty = new DifficultyModel("Easy");

        TourLogModel log1 = new TourLogModel();
        log1.setDistance(5);
        log1.setTimeInHours(1);
        log1.setDifficulty(difficulty);

        TourLogModel log2 = new TourLogModel();
        log2.setDistance(6);
        log2.setTimeInHours(1.5);
        log2.setDifficulty(difficulty);

        tourLogs = Arrays.asList(log1, log2);
    }

    @Test
    void testCalculateChildFriendlinessHike() {
        TransportTypeModel hike = new TransportTypeModel();
        hike.setName("Hike");

        float score = ChildFriendlinessCalculator.calculateChildFriendliness(tourLogs, hike);

        // Adjusted expected value
        assertEquals(81.67, score, 0.01);
    }

    @Test
    void testCalculateChildFriendlinessBike() {
        TransportTypeModel bike = new TransportTypeModel();
        bike.setName("Bike");

        float score = ChildFriendlinessCalculator.calculateChildFriendliness(tourLogs, bike);

        // Adjusted expected value
        assertEquals(87.78, score, 0.01);
    }

    @Test
    void testCalculateChildFriendlinessVacation() {
        TransportTypeModel vacation = new TransportTypeModel();
        vacation.setName("Vacation");

        float score = ChildFriendlinessCalculator.calculateChildFriendliness(tourLogs, vacation);

        // Adjusted expected value
        assertEquals(66.67, score, 0.01);
    }

    @Test
    void testCalculateChildFriendlinessRun() {
        TransportTypeModel run = new TransportTypeModel();
        run.setName("Run");

        float score = ChildFriendlinessCalculator.calculateChildFriendliness(tourLogs, run);

        // Adjusted expected value
        assertEquals(66.67, score, 0.01);
    }

    @Test
    void testCalculateChildFriendlinessUnknown() {
        TransportTypeModel unknown = new TransportTypeModel();
        unknown.setName("Unknown");

        float score = ChildFriendlinessCalculator.calculateChildFriendliness(tourLogs, unknown);

        // Assert score is as expected
        assertEquals(0, score, 0.01);
    }
}
