package technikum.at.tourplanner_swen2_team5.util;

import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.View.controllers.HomeScreenController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class ApplicationContext {
    // Basispfad zu den FXML-Dateien im Ressourcenverzeichnis
    public static final String FXML_BASE_PATH = "/technikum/at/tourplanner_swen2_team5/";

   public static final String API_KEY_ORS = getApiKey("API_KEY.txt");
   public static final String API_KEY_MB = getApiKey("MAPBOX_KEY.txt");

    private static HomeScreenController homeScreenController;

    public static void setHomeScreenController(HomeScreenController controller) {
        homeScreenController = controller;
    }

    public static HomeScreenController getHomeScreenController() {
        return homeScreenController;
    }

    public static String getApiKey(String filename) {
        // Create the directory if it does not exist
        String userDir = System.getProperty("user.home") + "/TourPlanner/";
        File dir = new File(userDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String apiKeyFilePath = System.getProperty("user.home") + "/TourPlanner/" + filename; // Pfad zur API_KEY.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(apiKeyFilePath))) {
            return reader.readLine().trim(); // Nimmt an, dass der API-Schlüssel in der ersten Zeile steht
        } catch (IOException e) {
            log.error("Failed to read api key file", e);
            return null; // Rückgabe von null, wenn der Schlüssel nicht gelesen werden kann
        }
    }
}

