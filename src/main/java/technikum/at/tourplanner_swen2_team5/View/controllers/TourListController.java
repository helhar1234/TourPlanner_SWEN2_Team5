package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class TourListController {

    @FXML
    private ImageView reloadIcon;
    @FXML
    private VBox tourEntryContainer;

    private final TourViewModel tourViewModel = TourViewModel.getInstance();

    private boolean isAscendingRecent = false;

    private boolean isAscendingPopularity = true;

    private boolean isAscendingChildFriendliness = true;

    public void initialize() {
        updateTourList();
    }

    private void updateTourList() {
        tourEntryContainer.getChildren().clear(); // Clear existing entries

        List<TourModel> tours = tourViewModel.getTours(); // Fetch tours
        for (TourModel tour : tours) {
            addTourEntry(tour);
        }
    }

    private void addTourEntry(TourModel tour) {
        try {
            FXMLLoader loader = new FXMLLoader(MainTourPlaner.class.getResource("tour_list_entry.fxml"));
            HBox tourEntry = loader.load();
            TourEntryController entryController = loader.getController();

            entryController.setTourData(tour);

            tourEntry.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    entryController.onDetailButtonClicked(tour.getId());
                }
            });

            tourEntry.setStyle("-fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 0 0 1 0; -fx-border-color: lightgray;");
            tourEntry.setOnMouseEntered(e -> tourEntry.setStyle("-fx-background-color: #e6e6e6; -fx-padding: 10;"));
            tourEntry.setOnMouseExited(e -> tourEntry.setStyle("-fx-background-color: transparent; -fx-padding: 10; -fx-border-style: solid inside; -fx-border-width: 0 0 1 0; -fx-border-color: lightgray;"));

            entryController.setTourListController(this);

            tourEntryContainer.getChildren().add(tourEntry);
        } catch (IOException e) {
            log.error("Failed to add Tour Entry with id {} to TourList", tour.getId(), e);
        }
    }

    @FXML
    public void onAddButtonClicked(ActionEvent actionEvent) {
        try {
            log.info("Add Tour Button clicked");
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
            onRefreshButtonClicked();
        } catch (IOException e) {
            log.error("Failed to open AddTour Window", e);
        }
    }

    @FXML
    public void onRefreshButtonClicked() {
        log.info("Refresh Button clicked");
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), reloadIcon);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.play();

        updateTourList();
        log.info("Refreshed tour list");
    }

    @FXML
    private void onSortByRecentButtonClicked(ActionEvent actionEvent) {
        tourEntryContainer.getChildren().clear(); // Clear existing entries

        List<TourModel> tours = tourViewModel.getTours(); // Fetch tours

        if (!isAscendingRecent) {
            Collections.reverse(tours);
        }

        for (TourModel tour : tours) {
            addTourEntry(tour);
        }

        // Toggle the sorting order for next click
        isAscendingRecent = !isAscendingRecent;
        isAscendingPopularity = true;
        isAscendingChildFriendliness = true;

        log.info("Sorted tour list by recency in {} order", isAscendingRecent ? "ascending" : "descending");
    }

    @FXML
    private void onSortByPopularityButtonClicked(ActionEvent actionEvent) {
        tourEntryContainer.getChildren().clear(); // Clear existing entries

        List<TourModel> tours = tourViewModel.getTours();

        if (!isAscendingPopularity) {
            tours.sort(Comparator.comparingInt(TourModel::getPopularity));
        } else {
            tours.sort(Comparator.comparingInt(TourModel::getPopularity).reversed());
        }

        for (TourModel tour : tours) {
            addTourEntry(tour);
        }

        // Toggle the sorting order for next click
        isAscendingPopularity = !isAscendingPopularity;
        isAscendingRecent = true;
        isAscendingChildFriendliness = true;

        log.info("Sorted tour list by popularity in {} order", isAscendingPopularity ? "ascending" : "descending");
    }

    @FXML
    private void onSortByChildFriendlinessButtonClicked(ActionEvent actionEvent) {
        isAscendingChildFriendliness = !isAscendingChildFriendliness;
        isAscendingRecent = true;
        isAscendingPopularity = true;

        log.info("Sorted tour list by child-friendliness in {} order", isAscendingChildFriendliness ? "ascending" : "descending");
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        HomeScreenController homeScreenController = ApplicationContext.getHomeScreenController();
        homeScreenController.gotoHomeScreen();
    }
}
