package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.DAL.repositories.TourDAO;

import java.util.List;

public class HomeScreenController {
    @FXML
    private VBox mainContentArea;
    private Stage stage;

    public void changeMainContent(Node content) {
        if (mainContentArea != null) {
            mainContentArea.getChildren().clear();
            mainContentArea.getChildren().add(content);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

