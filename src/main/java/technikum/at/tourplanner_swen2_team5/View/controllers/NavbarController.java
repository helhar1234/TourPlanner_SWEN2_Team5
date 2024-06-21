package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;

@Slf4j
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
        log.info("Add tour clicked");
    }

    @FXML
    private void onMapPinClicked() {
        EventHandler.showMapPinContent(homeScreenController);
        log.info("Map pin clicked");
    }

    private void handleLogoClick(MouseEvent event) {
        if (homeScreenController != null) {
            homeScreenController.gotoHomeScreen();
            log.info("Logo clicked");
        }
    }
}
