package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;
import technikum.at.tourplanner_swen2_team5.util.Formatter;

import java.io.IOException;
import java.net.URL;

public class TourEntryController {

    @FXML
    private ImageView transportIcon;
    @FXML
    private Label nameLabel, startLabel, destinationLabel, distanceLabel, timeLabel;
    @FXML
    private Button editButton, detailButton, downloadButton, deleteButton;

    private Formatter formatter;
    private TourViewModel tourViewModel;
    private TourListController tourListController;

    public void setTourListController(TourListController controller) {
        this.tourListController = controller;
    }

    public void setTourData(TourModel tour) {
        tourViewModel = TourViewModel.getInstance();
        formatter = new Formatter();

        String imageName = "img/icons/" + tour.getTransportType().getName().toLowerCase() + "-icon.png";
        URL resource = MainTourPlaner.class.getResource(imageName);

        Image image = new Image(resource.toString());
        transportIcon.setImage(image);
        transportIcon.setFitHeight(25);
        transportIcon.setFitWidth(25);
        nameLabel.setText(tour.getName());
        startLabel.setText(tour.getStart());
        destinationLabel.setText(tour.getDestination());
        distanceLabel.setText(formatter.formatDistance(tour.getDistance()));
        timeLabel.setText(formatter.formatTime(tour.getTime()));

        editButton.setOnAction(e -> onEditButtonClicked(tour.getId()));
        detailButton.setOnAction(e -> onDetailButtonClicked(tour.getId()));
        downloadButton.setOnAction(e -> onDownloadButtonClicked(tour.getId()));
        deleteButton.setOnAction(e -> onDeleteButtonTour(tour.getId()));
    }

    private void onEditButtonClicked(String id) {
        try {
            TourModel tour = tourViewModel.getTourById(id);
            FXMLLoader fxmlLoader = new FXMLLoader(MainTourPlaner.class.getResource("edit_tour.fxml"));
            Parent root = fxmlLoader.load();

            EditTourController controller = fxmlLoader.getController();
            controller.setTour(tour);

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            double width = screenBounds.getWidth() * 0.8;
            double height = screenBounds.getHeight() * 0.8;

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Tour");
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.setWidth(width);
            stage.setHeight(height);

            stage.showAndWait();
            tourListController.onRefreshButtonClicked();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onDetailButtonClicked(String tourId) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(MainTourPlaner.class.getResource("tour_detail.fxml"));
                Node detailView = loader.load();
                TourDetailController detailController = loader.getController();
                TourModel selectedTour = tourViewModel.getTourById(tourId);
                detailController.setTourDetails(selectedTour);

                HomeScreenController homeController = ApplicationContext.getHomeScreenController();
                homeController.changeMainContent(detailView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void onDeleteButtonTour(String tourId) {
        TourModel tour = tourViewModel.getTourById(tourId);
        ConfirmationWindow dialog = new ConfirmationWindow(
                (Stage) deleteButton.getScene().getWindow(),
                "Delete Tour",
                "Deletion Confirmation",
                "Do you want to delete this tour?"
        );

        if (tour != null && dialog.showAndWait()) {
            tourViewModel.deleteTour(tour);
            tourListController.onRefreshButtonClicked();
        }
    }

    private void onDownloadButtonClicked(String tourId) {
        //TODO: implement download button
    }
}
