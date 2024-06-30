package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;
import technikum.at.tourplanner_swen2_team5.util.Formatter;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.MapViewModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;
import technikum.at.tourplanner_swen2_team5.util.MapRequester;
import technikum.at.tourplanner_swen2_team5.util.PDFGenerator;

import java.io.File;
import java.io.IOException;

@Slf4j
@Controller
public class TourDetailController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label tourDescription;
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

    @Autowired
    private TourViewModel tourViewModel;

    @Autowired
    private MapViewModel mapViewModel;

    @Autowired
    private Formatter formatter;

    @Autowired
    private EventHandler eventHandler;

    private TourModel currentTour;

    @FXML
    public void initialize() {
        // Initialization code, if any
    }

    public void setTourDetails(TourModel tour) {
        this.currentTour = tour;

        bindTourData(tour);

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

    private void bindTourData(TourModel tour) {
        // Ensure properties are initialized from JPA attributes
        tour.initializeProperties();

        // Binding properties
        titleLabel.textProperty().bind(tour.nameProperty());
        tourDescription.textProperty().bind(tour.descriptionProperty());
        tourStart.textProperty().bind(Bindings.createStringBinding(
                () -> "Start: " + tour.getStart(),
                tour.startProperty()
        ));
        tourDestination.textProperty().bind(Bindings.createStringBinding(
                () -> "Destination: " + tour.getDestination(),
                tour.destinationProperty()
        ));
        tourTransportationType.textProperty().bind(Bindings.createStringBinding(
                () -> "Transport Type: " + (tour.getTransportType() != null ? tour.getTransportType().getName() : ""),
                tour.transportTypeProperty()
        ));
        tourDistance.textProperty().bind(Bindings.createStringBinding(
                () -> "Distance: " + String.format("%.2f km", tour.getDistance()),
                tour.distanceProperty()
        ));
        tourTime.textProperty().bind(Bindings.createStringBinding(
                () -> "Time: " + formatter.formatTime(0, tour.getTime()),
                tour.timeProperty()
        ));
    }

    @FXML
    public void onAddLogButtonClicked(ActionEvent actionEvent) {
        eventHandler.openAddTourLog(currentTour);
    }

    @FXML
    public void onEditButtonClicked(ActionEvent actionEvent) {
        eventHandler.openEditTour(currentTour, null);
    }

    @FXML
    public void onBackButtonClicked(ActionEvent actionEvent) {
        eventHandler.openTourList();
    }

    public void onSummaryReportButtonClicked(ActionEvent actionEvent) {
    }

    public void onSingleReportButtonClicked(ActionEvent actionEvent) {
        /*PDFGenerator generator = new PDFGenerator();
        generator.generateTourReport(tourViewModel.getTourById(tourId));*/
    }
}
