package technikum.at.tourplanner_swen2_team5.util;

import technikum.at.tourplanner_swen2_team5.controller.HomeScreenController;

public class ApplicationContext {
    private static HomeScreenController homeScreenController;

    public static void setHomeScreenController(HomeScreenController controller) {
        homeScreenController = controller;
    }

    public static HomeScreenController getHomeScreenController() {
        return homeScreenController;
    }
}

