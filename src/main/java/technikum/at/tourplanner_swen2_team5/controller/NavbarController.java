package technikum.at.tourplanner_swen2_team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;

import java.io.IOException;

public class NavbarController {

    @FXML
    private void initialize() {
    }

    public void onSearch(ActionEvent actionEvent) {
    }

    @FXML
    private void onAddTourClicked() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainTourPlaner.class.getResource("add_tour.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add New Tour");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HomeScreenController homeScreenController;

    public void setHomeScreenController(HomeScreenController controller) {
        this.homeScreenController = controller;
    }

    @FXML
    private void onMapPinClicked() {
        homeScreenController.changeMainContent("tour_list.fxml");
    }


}
