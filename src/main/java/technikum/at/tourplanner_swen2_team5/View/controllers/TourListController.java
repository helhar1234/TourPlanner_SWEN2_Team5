package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.MainTourPlanner;
import technikum.at.tourplanner_swen2_team5.util.ConfirmationWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;
import technikum.at.tourplanner_swen2_team5.util.JSONGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class TourListController {

    private final ConfigurableApplicationContext springContext;

    @FXML
    private ImageView reloadIcon;
    @FXML
    private Button uploadButton;
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
    private final JSONGenerator jsonGenerator;

    private boolean isDescendingRecent = true;
    private boolean isAscendingPopularity = true;
    private boolean isAscendingChildFriendliness = true;

    private List<TourModel> currentTourList = new ArrayList<>();

    @Autowired
    public TourListController(ConfigurableApplicationContext springContext, TourViewModel tourViewModel, EventHandler eventHandler, JSONGenerator jsonGenerator) {
        this.springContext = springContext;
        this.tourViewModel = tourViewModel;
        this.eventHandler = eventHandler;
        this.jsonGenerator = jsonGenerator;
    }

    @FXML
    public void initialize() {
        updateTourList();
    }

    public void updateTourList() {
        List<TourModel> tours = new ArrayList<>(tourViewModel.getTours()); // Create modifiable list
        updateTourListWithResults(tours, Collections.emptyList());
    }

    public void updateTourListWithResults(List<TourModel> tours, List<TourLogModel> tourLogs) {
        tourEntryContainer.getChildren().clear();
        currentTourList.clear(); // Clear the current list
        isDescendingRecent = false;

        List<TourModel> modifiableTours = new ArrayList<>(tours); // Ensure the tours list is modifiable

        if (!tourLogs.isEmpty()) {
            for (TourLogModel tourLog : tourLogs) {
                log.debug("Updating tour entry container with {}", tourLog.getTour().getName());
            }
            List<TourModel> logTours = tourLogs.stream()
                    .map(TourLogModel::getTour)
                    .distinct()
                    .collect(Collectors.toList());
            modifiableTours.addAll(logTours);
        }

        currentTourList.addAll(modifiableTours); // Store the current tours list as a modifiable list

        Collections.reverse(currentTourList);

        for (TourModel tour : currentTourList) {
            addTourEntry(tour);
            log.debug("New tour added to current list: {} (popularity: {}, child-friendliness: {})", tour.getName(), tour.getPopularity(), tour.getChildFriendliness());
        }
        updateSortButtons();
    }



    private void addTourEntry(TourModel tour) {
        try {
            FXMLLoader loader = new FXMLLoader(MainTourPlanner.class.getResource("/technikum/at/tourplanner_swen2_team5/tour_list_entry.fxml"));
            loader.setControllerFactory(springContext::getBean);
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
    public void onUploadButtonClicked(ActionEvent actionEvent) {
        try {
            jsonGenerator.uploadJSON((Stage) uploadButton.getScene().getWindow());
            onRefreshButtonClicked();
        } catch (IOException e) {
            ConfirmationWindow dialog = new ConfirmationWindow(
                    (Stage) uploadButton.getScene().getWindow(),
                    "Error",
                    "An Error Occurred",
                    "An error occurred while uploading. Please make sure that the file is correct!");

            dialog.showAndWait();
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
        sortTourList((tours) -> {
            if (!isDescendingRecent) {
                Collections.reverse(tours);
            }

            // Toggle the sorting order for next click
            isDescendingRecent = !isDescendingRecent;
            isAscendingPopularity = true;
            isAscendingChildFriendliness = true;

            sortByRecentButton.setText("recent " + (isDescendingRecent ? "↑" : "↓"));
            sortByPopularityButton.setText("popularity");
            sortByChildFriendlinessButton.setText("child-friendliness");

            log.info("Sorted tour list by recency in {} order", !isDescendingRecent ? "ascending" : "descending");
        });
    }

    @FXML
    private void onSortByPopularityButtonClicked(ActionEvent actionEvent) {
        sortTourList((tours) -> {
            if (!isAscendingPopularity) {
                tours.sort(Comparator.comparingInt(TourModel::getPopularity));
            } else {
                tours.sort(Comparator.comparingInt(TourModel::getPopularity).reversed());
            }

            // Toggle the sorting order for next click
            isDescendingRecent = true;
            isAscendingPopularity = !isAscendingPopularity;
            isAscendingChildFriendliness = true;

            sortByRecentButton.setText("recent");
            sortByPopularityButton.setText("popularity " + (isAscendingPopularity ? "↑" : "↓"));
            sortByChildFriendlinessButton.setText("child-friendliness");

            log.info("Sorted tour list by popularity in {} order", isAscendingPopularity ? "ascending" : "descending");
        });
    }

    @FXML
    private void onSortByChildFriendlinessButtonClicked(ActionEvent actionEvent) {
        sortTourList((tours) -> {
            if (!isAscendingChildFriendliness) {
                tours.sort(Comparator.comparingDouble(TourModel::getChildFriendliness));
            } else {
                tours.sort(Comparator.comparingDouble(TourModel::getChildFriendliness).reversed());
            }

            isDescendingRecent = true;
            isAscendingPopularity = true;
            isAscendingChildFriendliness = !isAscendingChildFriendliness;

            sortByRecentButton.setText("recent");
            sortByPopularityButton.setText("popularity");
            sortByChildFriendlinessButton.setText("child-friendliness " + (isAscendingChildFriendliness ? "↑" : "↓"));
        });
    }

    private void sortTourList(java.util.function.Consumer<List<TourModel>> sortFunction) {
        List<TourModel> tours = new ArrayList<>(currentTourList); // Use the current tour list for sorting
        sortFunction.accept(tours);
        tourEntryContainer.getChildren().clear();
        for (TourModel tour : tours) {
            addTourEntry(tour);
        }
    }

    private void updateSortButtons() {
        sortByRecentButton.setText("recent " + (!isDescendingRecent ? "↓" : "↑"));
        sortByPopularityButton.setText("popularity");
        sortByChildFriendlinessButton.setText("child-friendliness");
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        ApplicationContext.getHomeScreenController().gotoHomeScreen();
    }
}
