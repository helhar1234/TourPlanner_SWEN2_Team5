package technikum.at.tourplanner_swen2_team5.util;

import org.springframework.stereotype.Component;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;

import java.util.List;

@Component
public class ChildFriendlinessCalculator {

    public static float calculateChildFriendliness(List<TourLogModel> tourLogs, TransportTypeModel transportType) {
        double totalDistance = 0;
        double totalTime = 0;
        int totalDifficulty = 0;
        int numLogs = tourLogs.size();

        for (TourLogModel log : tourLogs) {
            totalDistance += log.getDistance();
            totalTime += log.getTimeInHours();
            totalDifficulty += log.getDifficulty().getId();
        }

        double avgDistance = totalDistance / numLogs;
        double avgTime = totalTime / numLogs;
        double avgDifficulty = (double) totalDifficulty / numLogs;

        // Maximalwerte für kinderfreundliche Touren
        double maxDistance = 0;
        double maxTime = 0;
        double maxDifficulty = 0;

        switch (transportType.getName()) {
            case "Hike":
                maxDistance = 10;
                maxTime = 2;
                maxDifficulty = 2;
                break;
            case "Bike":
                maxDistance = 15;
                maxTime = 1.5;
                maxDifficulty = 2;
                break;
            case "Vacation":
                maxDistance = 5;
                maxTime = 1;
                maxDifficulty = 1;
                break;
            case "Run":
                maxDistance = 5;
                maxTime = 1;
                maxDifficulty = 3;
                break;
            default:
                return 0; // Unknown tour type
        }

        // Calculation
        double distanceScore = Math.max(0, (maxDistance - avgDistance) / maxDistance);
        double timeScore = Math.max(0, (maxTime - avgTime) / maxTime);
        double difficultyScore = Math.max(0, (maxDifficulty - avgDifficulty) / maxDifficulty);

        // Average
        return (float) ((distanceScore + timeScore + difficultyScore) / 3 * 100);
    }
}
