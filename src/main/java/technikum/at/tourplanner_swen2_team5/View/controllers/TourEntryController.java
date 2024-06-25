package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.animation.TranslateTransition;
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
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;
import technikum.at.tourplanner_swen2_team5.util.*;

import java.io.IOException;
import java.net.URL;

@Slf4j
public class TourEntryController {

    @FXML
    private ImageView transportIcon;
    @FXML
    private Label nameLabel, startLabel, destinationLabel, distanceLabel, timeLabel;
    @FXML
    private Button editButton, detailButton, downloadButton, deleteButton;

    private TourViewModel tourViewModel;
    @Setter
    private TourListController tourListController;

    public void setTourData(TourModel tour) {
        tourViewModel = TourViewModel.getInstance();
        Formatter formatter = new Formatter();

        String imageName = "img/icons/" + tour.getTransportType().getName().toLowerCase() + "-icon.png";
        URL resource = MainTourPlaner.class.getResource(imageName);

        assert resource != null;
        Image image = new Image(resource.toString());
        transportIcon.setImage(image);
        transportIcon.setFitHeight(25);
        transportIcon.setFitWidth(25);
        nameLabel.setText(tour.getName());
        startLabel.setText(tour.getStart());
        destinationLabel.setText(tour.getDestination());
        distanceLabel.setText(Formatter.formatDistance(tour.getDistance()));
        timeLabel.setText(formatter.formatTime(0, tour.getTime()));

        editButton.setOnAction(e -> onEditButtonClicked(tour.getId()));
        detailButton.setOnAction(e -> onDetailButtonClicked(tour.getId()));
        downloadButton.setOnAction(e -> {
            try {
                onDownloadButtonClicked(tour.getId());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        deleteButton.setOnAction(e -> onDeleteButtonTour(tour.getId()));
    }

    public void onEditButtonClicked(String id) {
        EventHandler.openEditTour(tourViewModel.getTourById(id), tourListController);
    }

    public void onDetailButtonClicked(String tourId) {
        TourModel currentTour = tourViewModel.getTourById(tourId);
        loadAnimation(currentTour);
    }



    public void loadAnimation(TourModel currentTour) {
        log.info("Starting animation");
        ImageView animatedImage = new ImageView();
        animatedImage.setFitHeight(300);
        animatedImage.setFitWidth(300);
        animatedImage.setPreserveRatio(true);
        animatedImage.setOpacity(0.7);

        String transportType = currentTour.getTransportType().getName().toLowerCase();
        String imageName = "img/icons/" + transportType + "-color-icon.png";
        URL resource = MainTourPlaner.class.getResource(imageName);
        assert resource != null;
        Image image = new Image(resource.toString());
        animatedImage.setImage(image);

        StackPane contentPane = ApplicationContext.getHomeScreenController().getContentPane();
        contentPane.getChildren().add(animatedImage);

        TranslateTransition transition = new TranslateTransition();
        transition.setNode(animatedImage);
        transition.setFromX(-contentPane.getWidth()+animatedImage.getFitWidth()/2);
        transition.setToX(contentPane.getWidth()/2);
        transition.setDuration(Duration.seconds(2));
        transition.setOnFinished(event -> {
            contentPane.getChildren().remove(animatedImage);
            EventHandler.openTourDetail(currentTour);
        });

        transition.play();
    }



    private void onDeleteButtonTour(String tourId) {
        log.info("Delete Button for tour with id: {} clicked", tourId);
        TourModel tour = tourViewModel.getTourById(tourId);
        ConfirmationWindow dialog = new ConfirmationWindow(
                (Stage) deleteButton.getScene().getWindow(),
                "Delete Tour",
                "Deletion Confirmation",
                "Do you want to delete this tour?"
        );

        if (tour != null && dialog.showAndWait()) {
            try {
                tourViewModel.deleteTour(tour);
                log.info("Deleted tour with id: {}", tourId);
            } catch (IOException e) {
                log.error("Failed to delete tour with id: {}", tourId, e);
                throw new RuntimeException(e);
            }
            tourListController.onRefreshButtonClicked();
        }
    }

    private void onDownloadButtonClicked(String tourId) throws IOException {
        log.info("Download Tour Report Button clicked");
        PDFGenerator generator = new PDFGenerator();
        generator.generateTourReport(tourViewModel.getTourById(tourId));
    }
}
