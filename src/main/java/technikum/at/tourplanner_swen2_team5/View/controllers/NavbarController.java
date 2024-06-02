package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.fxml.FXML;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;

public class NavbarController {
    private HomeScreenController homeScreenController;

    public void setHomeScreenController(HomeScreenController controller) {
        this.homeScreenController = controller;
    }

    @FXML
    private void onAddTourClicked() {
        EventHandler.openAddTourWindow();
    }

    @FXML
    private void onMapPinClicked() {
        EventHandler.showMapPinContent(homeScreenController);
    }
}

