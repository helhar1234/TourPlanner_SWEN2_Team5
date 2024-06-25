package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;
import technikum.at.tourplanner_swen2_team5.util.Formatter;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.MapViewModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;

import java.io.File;
import java.io.IOException;

@Slf4j
public class TourDetailController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label tourDescription;
    @FXML
    private Label tourName;
    @FXML
    private Label tourStart;
    @FXML
    private Label tourDestination;
    @FXML
    private Label tourTransportationType;
    @FXML
    private Label tourDistance;
    @FXML
    private Label tourTime;
    @FXML
    private ImageView mapView;


    @FXML
    private TourLogListController tourLogListViewController;

    private TourViewModel tourViewModel;
    private MapViewModel mapViewModel;
    private Formatter formatter;

    private TourModel currentTour;

    public void initialize() {
        tourViewModel = TourViewModel.getInstance();
        mapViewModel = MapViewModel.getInstance();
        formatter = new Formatter();
    }

    public void setTourDetails(TourModel tour) {
        this.currentTour = tour;
        titleLabel.setText(currentTour.getName());
        tourDescription.setText(currentTour.getDescription());
        tourStart.setText("Start: " + currentTour.getStart());
        tourDestination.setText("Destination: " + currentTour.getDestination());
        tourTransportationType.setText("Transportation Type: " + currentTour.getTransportType().getName());
        tourDistance.setText("Distance: " + Formatter.formatDistance(currentTour.getDistance()));
        tourTime.setText("Time: " + formatter.formatTime(0, currentTour.getTime()));

        try {
            String filename = tour.getId() + "_map.png";
            File mapFile = new File(System.getProperty("user.home") + "/TourPlanner/maps", filename);

            if (!mapFile.exists()) {
                filename = MapRequester.fetchMapImage(tour);
                mapFile = new File(System.getProperty("user.home") + "/TourPlanner/maps", filename);
            }

            Image map = new Image(mapFile.toURI().toString());
            mapView.setPreserveRatio(true);
            mapView.setImage(map);
            mapView.setOnMouseClicked(event -> MapRequester.openMapInBrowser(currentTour.getStart(), currentTour.getDestination()));
        } catch (IOException e) {
            log.error("Failed to load map image for tour {}", tour.getId(), e);
        }

        // Set the tourId in the TourLogListController
        tourLogListViewController.setTourId(currentTour.getId());
    }


    public void onAddLogButtonClicked(ActionEvent actionEvent) {
        EventHandler.openAddTourLog(currentTour);
    }

    public void onEditButtonClicked(ActionEvent actionEvent) {
        EventHandler.openEditTour(currentTour, null);
    }


    public void onBackButtonClicked(ActionEvent actionEvent) {
        EventHandler.openTourList();
    }
}
