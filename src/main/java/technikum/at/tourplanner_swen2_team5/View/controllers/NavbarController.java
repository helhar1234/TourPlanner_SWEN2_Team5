package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;

public class NavbarController {
    @FXML
    private ImageView logo;

    private HomeScreenController homeScreenController;

    public void setHomeScreenController(HomeScreenController controller) {
        this.homeScreenController = controller;
        logo.setOnMouseClicked(this::handleLogoClick);
    }

    @FXML
    private void onAddTourClicked() {
        EventHandler.openAddTourWindow();
    }

    @FXML
    private void onMapPinClicked() {
        EventHandler.showMapPinContent(homeScreenController);
    }

    private void handleLogoClick(MouseEvent event) {
        if (homeScreenController != null) {
            homeScreenController.gotoHomeScreen();
        }
    }
}
