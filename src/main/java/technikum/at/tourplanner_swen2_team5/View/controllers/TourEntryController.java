package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;
import technikum.at.tourplanner_swen2_team5.MainTourPlanner;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourLogViewModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;
import technikum.at.tourplanner_swen2_team5.util.ConfirmationWindow;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;
import technikum.at.tourplanner_swen2_team5.util.Formatter;
import technikum.at.tourplanner_swen2_team5.util.PDFGenerator;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Slf4j
@Controller
public class TourEntryController {

    @FXML
    private ImageView transportIcon;
    @FXML
    private Label nameLabel, startLabel, destinationLabel, distanceLabel, timeLabel;
    @FXML
    private Button editButton, detailButton, downloadButton, deleteButton;

    private final TourViewModel tourViewModel;
    private final EventHandler eventHandler;
    private final PDFGenerator pdfGenerator;
    @Setter
    private TourListController tourListController;

    private Formatter formatter;

    public void setTourData(TourModel tour) {
        tourViewModel = TourViewModel.getInstance();
        Formatter formatter = new Formatter();
    }
  
    public TourEntryController(TourViewModel tourViewModel, EventHandler eventHandler, PDFGenerator pdfGenerator) {
        this.tourViewModel = tourViewModel;
        this.eventHandler = eventHandler;
        this.pdfGenerator = pdfGenerator;
    }

    public void setTourData(TourModel tour) {
        bindTourData(tour);

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

    private void bindTourData(TourModel tour) {
        // Binding properties
        tour.initializeProperties();
        nameLabel.textProperty().bind(tour.nameProperty());
        startLabel.textProperty().bind(tour.startProperty());
        destinationLabel.textProperty().bind(tour.destinationProperty());

        formatter = new Formatter();
        distanceLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return formatter.formatDistance(tour.getDistance());
        }, tour.distanceProperty()));

        timeLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            return formatter.formatTime(0, tour.getTime());
        }, tour.timeProperty()));

        tour.transportTypeProperty().addListener((observable, oldValue, newValue) -> updateTransportIcon(newValue));
        updateTransportIcon(tour.getTransportType());
    }


    private void updateTransportIcon(TransportTypeModel transportType) {
        if (transportType != null) {
            String imageName = "img/icons/" + transportType.getName().toLowerCase() + "-icon.png";
            URL resource = MainTourPlanner.class.getResource(imageName);

            assert resource != null;
            Image image = new Image(resource.toString());
            transportIcon.setImage(image);
            transportIcon.setFitHeight(25);
            transportIcon.setFitWidth(25);
        }
    }

    public void onEditButtonClicked(String id) {
        eventHandler.openEditTour(tourViewModel.getTourById(id), tourListController);
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
        URL resource = MainTourPlanner.class.getResource(imageName);
        assert resource != null;
        Image image = new Image(resource.toString());
        animatedImage.setImage(image);

        StackPane contentPane = ApplicationContext.getHomeScreenController().getContentPane();
        contentPane.getChildren().add(animatedImage);

        TranslateTransition transition = new TranslateTransition();
        transition.setNode(animatedImage);
        transition.setFromX(-contentPane.getWidth() + animatedImage.getFitWidth() / 2);
        transition.setToX(contentPane.getWidth() / 2);
        transition.setDuration(Duration.seconds(2));
        transition.setOnFinished(event -> {
            contentPane.getChildren().remove(animatedImage);
            eventHandler.openTourDetail(currentTour);
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
        /*log.info("Download Tour Report Button clicked");
        PDFGenerator generator = new PDFGenerator();
        generator.generateTourReport(tourViewModel.getTourById(tourId));*/

        log.info("Export Tour Button clicked");
        JSONGenerator generator = new JSONGenerator();
        TourModel tour = tourViewModel.getTourById(tourId);
        TourLogViewModel tourLogViewModel = TourLogViewModel.getInstance();
        List<TourLogModel> logs = tourLogViewModel.getTourLogsForTour(tourId);
        generator.generateTourExportsJSON(tour, logs);


        /*log.info("Download Tour Report Button clicked");
        pdfGenerator.generateTourReport(tourViewModel.getTourById(tourId));*/
    }
}
