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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;
import technikum.at.tourplanner_swen2_team5.util.Formatter;
import technikum.at.tourplanner_swen2_team5.util.PDFGenerator;

import java.io.FileNotFoundException;
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
        try {
            TourModel tour = tourViewModel.getTourById(id);
            log.info("Edit button for tour with id {} clicked", tour.getId());

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
            log.error("Failed to open EditTour Window for tour with id {}", id, e);
        }
    }

    public void onDetailButtonClicked(String tourId) {
        log.info("Detail Button clicked");
        // Create the image view for the animation
        ImageView animatedImage = new ImageView();
        animatedImage.setFitHeight(300);  // Adjust height as needed
        animatedImage.setFitWidth(300);   // Adjust width as needed
        animatedImage.setPreserveRatio(true);
        animatedImage.setOpacity(0.7);    // Set the opacity to make the image a little bit transparent

        // Set the image based on the transport type
        TourModel selectedTour = tourViewModel.getTourById(tourId);
        String transportType = selectedTour.getTransportType().getName().toLowerCase();
        String imageName = "img/icons/" + transportType + "-color-icon.png";
        URL resource = MainTourPlaner.class.getResource(imageName);
        assert resource != null;
        Image image = new Image(resource.toString());
        animatedImage.setImage(image);

        // Get the HomeScreenController and StackPane
        HomeScreenController homeScreenController = ApplicationContext.getHomeScreenController();
        StackPane contentPane = homeScreenController.getContentPane();
        contentPane.getChildren().add(animatedImage);

        // Preload the detail view in the background
        final Node[] detailView = new Node[1];
        final TourDetailController[] detailController = new TourDetailController[1];

        Thread preloadThread = new Thread(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(MainTourPlaner.class.getResource("tour_detail.fxml"));
                detailView[0] = loader.load();
                detailController[0] = loader.getController();
                detailController[0].setTourDetails(selectedTour);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        preloadThread.setDaemon(true);
        preloadThread.start();

        // Create the translate transition
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(animatedImage);
        transition.setFromX(-contentPane.getWidth()+animatedImage.getFitWidth()/2);  // Start from outside the left edge
        transition.setToX(contentPane.getWidth()/2);          // Move to the right edge
        transition.setDuration(Duration.seconds(2));

        // Set the event handler to load the detail view after the animation
        transition.setOnFinished(event -> {
            // Remove the animated image from the StackPane
            contentPane.getChildren().remove(animatedImage);

            // Wait for the preload thread to finish
            try {
                preloadThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Load the detail view
            Platform.runLater(() -> {
                homeScreenController.changeMainContent(detailView[0]);
            });
        });

        // Play the animation
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
