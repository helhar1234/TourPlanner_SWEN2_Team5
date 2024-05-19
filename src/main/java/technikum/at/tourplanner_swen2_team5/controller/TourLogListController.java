package technikum.at.tourplanner_swen2_team5.controller;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.util.Formatter;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourLogViewModel;

import java.io.IOException;
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
        filteredList.setPredicate(tourLog -> tourLog.getTourId().equals(tourId));
        populateTourLogs(filteredList);
    }

    private void refreshTourLogs() {
        Platform.runLater(this::loadTourLogs);
    }

    private void populateTourLogs(List<TourLogModel> tourLogs) {
        tourLogsContainer.getChildren().clear();
        for (int i = 0; i < tourLogs.size(); i++) {
            TourLogModel log = tourLogs.get(i);
            try {
                FXMLLoader loader = new FXMLLoader(MainTourPlaner.class.getResource("tour_log_entry.fxml"));
                GridPane logEntry = loader.load();

                Label dateLabel = (Label) logEntry.lookup("#dateLabel");
                dateLabel.setText("Date: " + log.getDate());

                Label timeLabel = (Label) logEntry.lookup("#timeLabel");
                timeLabel.setText("Time: " + log.getTime());

                Label commentLabel = (Label) logEntry.lookup("#commentLabel");
                commentLabel.setText("Comment: " + log.getComment());

                Label difficultyLabel = (Label) logEntry.lookup("#difficultyLabel");
                difficultyLabel.setText("Difficulty: " + log.getDifficulty());

                Label distanceLabel = (Label) logEntry.lookup("#distanceLabel");
                distanceLabel.setText("Distance: " + log.getDistance());

                Label totalTimeLabel = (Label) logEntry.lookup("#totalTimeLabel");
                totalTimeLabel.setText("Total Time: " + log.getTotalTime());

                Label ratingLabel = (Label) logEntry.lookup("#ratingLabel");
                ratingLabel.setText("Rating: " + log.getRating());

                Label transportTypeLabel = (Label) logEntry.lookup("#transportTypeLabel");
                transportTypeLabel.setText("Transport Type: " + log.getTransportType());

                Button editButton = (Button) logEntry.lookup("#editButton");
                editButton.setOnAction(event -> onEditTourLogClicked(log.getId()));

                Button downloadButton = (Button) logEntry.lookup("#downloadButton");
                // TODO: Implement download functionality

                Button trashButton = (Button) logEntry.lookup("#trashButton");
                trashButton.setOnAction(event -> tourLogViewModel.deleteTourLogById(log.getId()));

                tourLogsContainer.getChildren().add(logEntry);

                // Add a separator between entries, but not after the last one
                if (i < tourLogs.size() - 1) {
                    Separator separator = new Separator();
                    separator.setPadding(new Insets(10, 0, 10, 0));
                    tourLogsContainer.getChildren().add(separator);
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
            Parent root = fxmlLoader.load(); // Lade die FXML und initialisiere den Controller

            EditTourLogController controller = fxmlLoader.getController();
            controller.setTourLog(tourLog);

            // Bildschirmgröße ermitteln
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double width = screenBounds.getWidth() * 0.8;
            double height = screenBounds.getHeight() * 0.8;

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Tour Log");
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Fenstergröße relativ zur Bildschirmgröße setzen
            stage.setWidth(width);
            stage.setHeight(height);

            stage.showAndWait(); // Zeige das Fenster und warte, bis es geschlossen wird

            // Aktualisiere die Tour-Logs, nachdem das Bearbeitungsfenster geschlossen wurde
            refreshTourLogs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
