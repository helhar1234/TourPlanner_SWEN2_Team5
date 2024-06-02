package technikum.at.tourplanner_swen2_team5.util;

import technikum.at.tourplanner_swen2_team5.View.controllers.HomeScreenController;

public class ApplicationContext {
    // Basispfad zu den FXML-Dateien im Ressourcenverzeichnis
    public static final String FXML_BASE_PATH = "/technikum/at/tourplanner_swen2_team5/";

    private static HomeScreenController homeScreenController;

    public static void setHomeScreenController(HomeScreenController controller) {
        homeScreenController = controller;
    }

    public static HomeScreenController getHomeScreenController() {
        return homeScreenController;
    }
}

