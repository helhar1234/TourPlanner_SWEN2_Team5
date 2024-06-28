package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourLogViewModel;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Slf4j
public class TourLogListController {
    @FXML
    private VBox tourLogsContainer;

    private TourLogViewModel tourLogViewModel;
    private String tourId;

    public void initialize() {
        tourLogViewModel = TourLogViewModel.getInstance();
        tourLogViewModel.getTourLogsForTour(tourId).addListener((ListChangeListener<TourLogModel>) change -> refreshTourLogs());
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
        loadTourLogs();
    }

    private void loadTourLogs() {
        populateTourLogs(tourLogViewModel.getTourLogsForTour(tourId));
    }

    private void refreshTourLogs() {
        Platform.runLater(this::loadTourLogs);
    }

    private void populateTourLogs(List<TourLogModel> tourLogs) {
        tourLogsContainer.getChildren().clear();
        if (tourLogs.isEmpty()) {
            Label noLogsLabel = new Label("No tour logs added yet...");
            tourLogsContainer.getChildren().add(noLogsLabel);
        } else {
            boolean first = true;
            for (TourLogModel tourLog : tourLogs) {
                try {
                    FXMLLoader loader = new FXMLLoader(MainTourPlaner.class.getResource("tour_log_entry.fxml"));
                    VBox logEntry = loader.load();

                    String imageName = "img/icons/" + tourLog.getTransportType().getName().toLowerCase() + "-icon.png";
                    URL resource = MainTourPlaner.class.getResource(imageName);
                    if (resource != null) {
                        Image transportIcon = new Image(resource.toString());
                        ImageView transportIconView = (ImageView) logEntry.lookup("#transportTypeIcon");
                        transportIconView.setFitWidth(30);
                        transportIconView.setFitHeight(30);
                        transportIconView.setPreserveRatio(true);
                        transportIconView.setImage(transportIcon);
                    } else {
                        log.warn("Failed to load transport icon {}", imageName);
                    }

                    ((Label) logEntry.lookup("#dateLabel")).setText(tourLog.getDate());
                    ((Label) logEntry.lookup("#ratingLabel")).setText(tourLog.getRating() + "/10");
                    ((Label) logEntry.lookup("#detailsLabel")).setText(
                            String.format("Time: %02d:%02d | Difficulty: %s | Distance: %.2f km | Total Time: %s",
                                    tourLog.getTimeHours(),
                                    tourLog.getTimeMinutes(),
                                    tourLog.getDifficulty().getDifficulty(),
                                    tourLog.getDistance(),
                                    tourLog.getTotalTime())
                    );
                    ((Label) logEntry.lookup("#commentLabel")).setText(tourLog.getComment());

                    Button editButton = (Button) logEntry.lookup("#editButton");
                    editButton.setOnAction(event -> onEditTourLogClicked(tourLog.getId()));

                    Button trashButton = (Button) logEntry.lookup("#deleteButton");
                    trashButton.setOnAction(event -> {
                        tourLogViewModel.deleteTourLogById(tourLog.getId());
                        refreshTourLogs();
                    });

                    if (!first) {
                        Separator separator = new Separator();
                        separator.setPadding(new Insets(10, 0, 10, 0));
                        tourLogsContainer.getChildren().add(separator);
                    } else {
                        first = false;
                    }

                    tourLogsContainer.getChildren().add(logEntry);

                    Label ratingLabel = (Label) logEntry.lookup("#ratingLabel");
                    int rating = tourLog.getRating();
                    ratingLabel.setText(rating + "/10");
                    if (rating >= 6) {
                        ratingLabel.setStyle("-fx-text-fill: green;");
                    } else if (rating >= 3) {
                        ratingLabel.setStyle("-fx-text-fill: orange;");
                    } else {
                        ratingLabel.setStyle("-fx-text-fill: red;");
                    }

                    log.info("Successfully loaded tour logs for tour with id: {}", tourId);
                } catch (IOException e) {
                    log.error("Failed to load tour logs for tour with id: {}", tourId, e);
                }
            }
        }
    }

    private void onEditTourLogClicked(String id) {
        TourLogModel tourLog = tourLogViewModel.getTourLogById(id);
        EventHandler.openEditTourLog(tourLog);
        refreshTourLogs();
    }
}
