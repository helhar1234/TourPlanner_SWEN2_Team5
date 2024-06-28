package technikum.at.tourplanner_swen2_team5.util;

import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;

import java.util.List;

public class ChildFriendlinessCalculator {

    public static double calculateChildFriendliness(List<TourLogModel> tourLogs, String tourType) {
        double totalDistance = 0;
        double totalTime = 0;
        int totalDifficulty = 0;
        int numLogs = tourLogs.size();

        for (TourLogModel log : tourLogs) {
            totalDistance += log.getDistance();
            totalTime += log.getTimeInHours();
            totalDifficulty += log.getDifficultyValue();
        }

        double avgDistance = totalDistance / numLogs;
        double avgTime = totalTime / numLogs;
        double avgDifficulty = totalDifficulty / numLogs;

        // Maximalwerte für kinderfreundliche Touren
        double maxDistance = 0;
        double maxTime = 0;
        double maxDifficulty = 0;

        switch (tourType) {
            case "Wandern":
                maxDistance = 10;
                maxTime = 2;
                maxDifficulty = 2;
                break;
            case "Radfahren":
                maxDistance = 15;
                maxTime = 1.5;
                maxDifficulty = 2;
                break;
            case "Urlaub":
                maxDistance = 5;
                maxTime = 1;
                maxDifficulty = 1;
                break;
            case "Laufen":
                maxDistance = 5;
                maxTime = 1;
                maxDifficulty = 3;
                break;
            default:
                return 0; // Unknown tour type
        }

        // Berechnung der Abweichung und Normalisierung
        double distanceScore = Math.max(0, (maxDistance - avgDistance) / maxDistance);
        double timeScore = Math.max(0, (maxTime - avgTime) / maxTime);
        double difficultyScore = Math.max(0, (maxDifficulty - avgDifficulty) / maxDifficulty);

        // Durchschnitt der Scores und Umwandlung in Prozentsatz
        double childFriendlyScore = (distanceScore + timeScore + difficultyScore) / 3 * 100;

        return childFriendlyScore;
    }
