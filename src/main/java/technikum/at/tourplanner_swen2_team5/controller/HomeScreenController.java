package technikum.at.tourplanner_swen2_team5.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;

import java.io.IOException;

public class HomeScreenController {

    @FXML
    private void initialize() {
        // Controller initialization after FXML load
    }

    @FXML
    private VBox mainContentArea;

    public void changeMainContent(String fxmlFile) {
        try {
            mainContentArea.getChildren().clear(); // Entfernen Sie bestehende Inhalte
            mainContentArea.getChildren().add(FXMLLoader.load(MainTourPlaner.class.getResource(fxmlFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
