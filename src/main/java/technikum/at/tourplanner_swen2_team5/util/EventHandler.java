package technikum.at.tourplanner_swen2_team5.util;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.View.controllers.*;

import java.io.IOException;

@Slf4j
public class EventHandler {

    public static void openAddTour() {
        try {
            log.info("Add Tour Button clicked");
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
            openTourList();
        } catch (Exception e) {
            log.error("Failed to open Add Tour Window", e);
        }
    }

    public static void openAddTourLog(TourModel currentTour) {
        try {
            log.info("Add Tour Log Button clicked");
            FXMLLoader fxmlLoader = new FXMLLoader(EventHandler.class.getResource(ApplicationContext.FXML_BASE_PATH + "add_tour_log.fxml"));
            Parent root = fxmlLoader.load();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double width = screenBounds.getWidth() * 0.8;
            double height = screenBounds.getHeight() * 0.8;

            AddTourLogController addTourLogController = fxmlLoader.getController();
            addTourLogController.setTourLogTour(currentTour);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add New Tour Log");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);
            stage.showAndWait();
            openTourDetail(currentTour);
        } catch (Exception e) {
            log.error("Failed to open Add Tour Log Window", e);
        }
    }

    public static void openTourDetail(TourModel currentTour) {
        try {
            log.info("Loading tour detail view");
            FXMLLoader loader = new FXMLLoader(MainTourPlaner.class.getResource("tour_detail.fxml"));
            Node detailView = loader.load();
            TourDetailController detailController = loader.getController();
            detailController.setTourDetails(currentTour);
            Platform.runLater(() -> {
                ApplicationContext.getHomeScreenController().changeMainContent(detailView);
            });
        } catch (IOException e) {
            log.error("Failed to load tour detail view", e);
        }

    }

    public static void openEditTour(TourModel currentTour, TourListController tourListController) {
        try {
            log.info("Edit tour button clicked");
            FXMLLoader fxmlLoader = new FXMLLoader(MainTourPlaner.class.getResource("edit_tour.fxml"));
            Parent root = fxmlLoader.load(); // Lade die FXML und initialisiere den Controller

            EditTourController controller = fxmlLoader.getController();
            controller.setTour(currentTour);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Tour");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (tourListController != null){
                tourListController.onRefreshButtonClicked();
            }
        } catch (IOException e) {
            log.error("Failed to load Edit Tour Window for tour {}", currentTour.getId(), e);
        }
    }



    public static void openEditTourLog(TourLogModel currentLog) {
        try {
            log.info("Edit button clicked for tourLog with id {}", currentLog.getId());

            FXMLLoader fxmlLoader = new FXMLLoader(MainTourPlaner.class.getResource("edit_tour_log.fxml"));
            Parent root = fxmlLoader.load();

            EditTourLogController controller = fxmlLoader.getController();
            controller.setTourLog(currentLog);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double width = screenBounds.getWidth() * 0.8;
            double height = screenBounds.getHeight() * 0.8;

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Tour Log");
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.setWidth(width);
            stage.setHeight(height);

            stage.showAndWait();
        } catch (IOException e) {
            log.error("Failed to load EditTourLog Window for tour with id: {}", currentLog.getId(), e);
        }
    }


    public static void openTourList() {
        try {
            log.info("Map pin clicked");
            FXMLLoader loader = new FXMLLoader(EventHandler.class.getResource(ApplicationContext.FXML_BASE_PATH + "tour_list.fxml"));
            Node tourListView = loader.load();
            ApplicationContext.getHomeScreenController().changeMainContent(tourListView);
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

