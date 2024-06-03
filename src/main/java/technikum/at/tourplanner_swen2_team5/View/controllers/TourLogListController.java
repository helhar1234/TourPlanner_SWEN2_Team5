package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
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
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.util.Formatter;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourLogViewModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TourLogListController {
    @FXML
    private VBox tourLogsContainer;

    private TourLogViewModel tourLogViewModel;
    private Formatter formatter;
    private String tourId;

    public void initialize() {
        tourLogViewModel = TourLogViewModel.getInstance();
        formatter = new Formatter();
        tourLogViewModel.getTourLogs().addListener((ListChangeListener<TourLogModel>) change -> refreshTourLogs());
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
        loadTourLogs();
    }

    private void loadTourLogs() {
        FilteredList<TourLogModel> filteredList = new FilteredList<>(tourLogViewModel.getTourLogs());
        filteredList.setPredicate(tourLog -> tourLog.getTour().getId().equals(tourId));
        populateTourLogs(filteredList);
    }

    private void refreshTourLogs() {
        Platform.runLater(this::loadTourLogs);
    }

    private void populateTourLogs(List<TourLogModel> tourLogs) {
        tourLogsContainer.getChildren().clear();
        boolean first = true;
        for (TourLogModel log : tourLogs) {
            try {
                FXMLLoader loader = new FXMLLoader(MainTourPlaner.class.getResource("tour_log_entry.fxml"));
                VBox logEntry = loader.load();

                String imageName = "img/icons/" + log.getTransportType().getName().toLowerCase() + "-icon.png";
                URL resource = MainTourPlaner.class.getResource(imageName);
                if (resource != null) {
                    Image transportIcon = new Image(resource.toString());
                    ImageView transportIconView = (ImageView) logEntry.lookup("#transportTypeIcon");
                    transportIconView.setFitWidth(30);
                    transportIconView.setFitHeight(30);
                    transportIconView.setPreserveRatio(true);
                    transportIconView.setImage(transportIcon);
                } else {
                    System.out.println("Image not found: " + imageName);
                }

                ((Label) logEntry.lookup("#dateLabel")).setText(log.getDate().toString());
                ((Label) logEntry.lookup("#ratingLabel")).setText(log.getRating() + "/10");
                ((Label) logEntry.lookup("#detailsLabel")).setText("Time: " + log.getTime() + " | Difficulty: " + log.getDifficulty().getDifficulty() +
                        " | Distance: " + log.getDistance() + " km | Total Time: " + log.getTotalTime());
                ((Label) logEntry.lookup("#commentLabel")).setText(log.getComment());

                Button editButton = (Button) logEntry.lookup("#editButton");
                editButton.setOnAction(event -> onEditTourLogClicked(log.getId()));

                Button trashButton = (Button) logEntry.lookup("#deleteButton");
                trashButton.setOnAction(event -> tourLogViewModel.deleteTourLogById(log.getId()));

                if (!first) {
                    Separator separator = new Separator();
                    separator.setPadding(new Insets(10, 0, 10, 0));
                    tourLogsContainer.getChildren().add(separator);
                } else {
                    first = false;
                }

                tourLogsContainer.getChildren().add(logEntry);

                Label ratingLabel = (Label) logEntry.lookup("#ratingLabel");
                int rating = log.getRating();
                ratingLabel.setText(rating + "/10");
                if (rating >= 6) {
                    ratingLabel.setStyle("-fx-text-fill: green;");
                } else if (rating >= 3) {
                    ratingLabel.setStyle("-fx-text-fill: orange;");
                } else {
                    ratingLabel.setStyle("-fx-text-fill: red;");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onEditTourLogClicked(String id) {
        try {
            TourLogModel tourLog = tourLogViewModel.getTourLogById(id);
            FXMLLoader fxmlLoader = new FXMLLoader(MainTourPlaner.class.getResource("edit_tour_log.fxml"));
            Parent root = fxmlLoader.load();

            EditTourLogController controller = fxmlLoader.getController();
            controller.setTourLog(tourLog);

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
            refreshTourLogs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}