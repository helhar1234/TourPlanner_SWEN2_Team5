package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;

@Slf4j
public class NavbarController {
    @FXML
    private ImageView logo;

    @FXML
    private void onAddTourClicked() {
        EventHandler.openAddTour();
    }


    @FXML
    private void onMapPinClicked() {
        EventHandler.openTourList();
    }

}
