package technikum.at.tourplanner_swen2_team5.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.View.controllers.ConfirmationWindow;
import technikum.at.tourplanner_swen2_team5.View.controllers.HomeScreenController;

@Slf4j
public class EventHandler {

    public static void openAddTourWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(EventHandler.class.getResource(ApplicationContext.FXML_BASE_PATH + "add_tour.fxml"));
            Parent root = fxmlLoader.load();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double width = screenBounds.getWidth() * 0.8;
            double height = screenBounds.getHeight() * 0.8;

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add New Tour");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);
            stage.showAndWait();
        } catch (Exception e) {
            log.error("Failed to open Add Tour Window", e);
        }
    }

    public static void showMapPinContent(HomeScreenController homeScreenController) {
        try {
            FXMLLoader loader = new FXMLLoader(EventHandler.class.getResource(ApplicationContext.FXML_BASE_PATH + "tour_list.fxml"));
            Node tourListView = loader.load();
            homeScreenController.changeMainContent(tourListView);
        } catch (Exception e) {
            log.error("Failed to open Tour List View", e);
        }
    }

    public static boolean confirmBack(Stage stage, String title, String header, String content) {
        try {
            ConfirmationWindow dialog = new ConfirmationWindow(stage, title, header, content);
            return dialog.showAndWait();
        } catch (Exception e) {
            log.error("Failed to confirm back", e);
            return false;
        }
    }
}

