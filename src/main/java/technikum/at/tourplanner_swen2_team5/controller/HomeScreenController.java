package technikum.at.tourplanner_swen2_team5.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;

import java.io.IOException;

public class HomeScreenController {
    private static HomeScreenController instance;

    @FXML private VBox mainContentArea;


    public HomeScreenController() {}

    @FXML
    private void initialize() {
        if (mainContentArea == null) {
            System.out.println("mainContentArea is null during initialization");
        } else {
            System.out.println("mainContentArea is initialized");
        }
    }

    public static HomeScreenController getInstance() {
        if (instance == null) {
            instance = new HomeScreenController();
        }
        return instance;
    }

    public void changeMainContent(Node content) {
        if (mainContentArea == null) {
            System.out.println("Attempting to change content when mainContentArea is null");
        } else {
            mainContentArea.getChildren().clear();
            mainContentArea.getChildren().add(content);
        }
    }
}
