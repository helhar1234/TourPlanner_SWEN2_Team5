package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import technikum.at.tourplanner_swen2_team5.MainTourPlanner;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Controller
public class TourListController {

    private final ConfigurableApplicationContext springContext;

    @FXML
    private ImageView reloadIcon;
    @FXML
    private VBox tourEntryContainer;
    @FXML
    private Button sortByRecentButton;
    @FXML
    private Button sortByPopularityButton;
    @FXML
    private Button sortByChildFriendlinessButton;

    private final TourViewModel tourViewModel;
    private final EventHandler eventHandler;

    private boolean isDescendingRecent = false;
    private boolean isAscendingPopularity = true;
    private boolean isAscendingChildFriendliness = true;

    @Autowired
    public TourListController(ConfigurableApplicationContext springContext, TourViewModel tourViewModel, EventHandler eventHandler) {
        this.springContext = springContext;
        this.tourViewModel = tourViewModel;
        this.eventHandler = eventHandler;
    }

    @FXML
    public void initialize() {
        updateTourList();
    }

    private void updateTourList() {
        tourEntryContainer.getChildren().clear(); // Clear existing entries

        List<TourModel> tours = tourViewModel.getTours(); // Fetch tours
        Collections.reverse(tours); // Sort by recent descending

        for (TourModel tour : tours) {
            addTourEntry(tour);
        }

        isDescendingRecent = true;
        isAscendingPopularity = true;
        isAscendingChildFriendliness = true;

        sortByRecentButton.setText("recent ↓");
        sortByPopularityButton.setText("popularity");
        sortByChildFriendlinessButton.setText("child-friendliness");
    }

    private void addTourEntry(TourModel tour) {
        try {
            FXMLLoader loader = new FXMLLoader(MainTourPlanner.class.getResource("/technikum/at/tourplanner_swen2_team5/tour_list_entry.fxml"));
            loader.setControllerFactory(springContext::getBean); // Use Spring context to create controllers
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
        eventHandler.openAddTour();
        onRefreshButtonClicked();
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

        if (!isDescendingRecent) {
            Collections.reverse(tours);
        }

        for (TourModel tour : tours) {
            addTourEntry(tour);
        }

        // Toggle the sorting order for next click
        isDescendingRecent = !isDescendingRecent;
        isAscendingPopularity = true;
        isAscendingChildFriendliness = true;

        sortByRecentButton.setText("recent " + (!isDescendingRecent ? "↑" : "↓"));
        sortByPopularityButton.setText("popularity");
        sortByChildFriendlinessButton.setText("child-friendliness");

        log.info("Sorted tour list by recency in {} order", !isDescendingRecent ? "ascending" : "descending");
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
        isDescendingRecent = false;
        isAscendingChildFriendliness = true;

        sortByRecentButton.setText("recent");
        sortByPopularityButton.setText("popularity " + (isAscendingPopularity ? "↑" : "↓"));
        sortByChildFriendlinessButton.setText("child-friendliness");

        log.info("Sorted tour list by popularity in {} order", isAscendingPopularity ? "ascending" : "descending");
    }

    @FXML
    private void onSortByChildFriendlinessButtonClicked(ActionEvent actionEvent) {
        tourEntryContainer.getChildren().clear(); // Clear existing entries

        List<TourModel> tours = tourViewModel.getTours();

        if (!isAscendingChildFriendliness) {
            tours.sort(Comparator.comparingDouble(TourModel::getChildFriendliness));
        } else {
            tours.sort(Comparator.comparingDouble(TourModel::getChildFriendliness).reversed());
        }

        for (TourModel tour : tours) {
            addTourEntry(tour);
        }

        isAscendingChildFriendliness = !isAscendingChildFriendliness;
        isDescendingRecent = false;
        isAscendingPopularity = true;

        sortByRecentButton.setText("recent");
        sortByPopularityButton.setText("popularity");
        sortByChildFriendlinessButton.setText("child-friendliness " + (isAscendingChildFriendliness ? "↑" : "↓"));

        log.info("Sorted tour list by child-friendliness in {} order", isAscendingChildFriendliness ? "ascending" : "descending");
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        ApplicationContext.getHomeScreenController().gotoHomeScreen();
    }
}
