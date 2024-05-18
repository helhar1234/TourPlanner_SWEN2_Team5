package technikum.at.tourplanner_swen2_team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.controller.HomeScreenController;

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

            // Bildschirmgröße ermitteln
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double width = screenBounds.getWidth() * 0.8;
            double height = screenBounds.getHeight() * 0.8;

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add New Tour");
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Fenstergröße relativ zur Bildschirmgröße setzen
            stage.setWidth(width);
            stage.setHeight(height);

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
        try {
            FXMLLoader loader = new FXMLLoader(MainTourPlaner.class.getResource("tour_list.fxml"));
            Node tourListView = loader.load();
            homeScreenController.changeMainContent(tourListView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
