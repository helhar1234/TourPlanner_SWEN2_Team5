package technikum.at.tourplanner_swen2_team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;
import technikum.at.tourplanner_swen2_team5.models.MapModel;
import technikum.at.tourplanner_swen2_team5.models.TourModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.MapViewModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

import java.net.URL;

public class TourDetailController {

    @FXML private Label titleLabel;
    @FXML private Label tourName;
    @FXML private Label tourStart;
    @FXML private Label tourDestination;
    @FXML private Label tourTransportationType;
    @FXML private Label tourDistance;
    @FXML private Label tourTime;

    @FXML private ImageView mapImage;

    private TourViewModel tourViewModel;
    private MapViewModel mapViewModel;

    public void initialize (){
        tourViewModel = TourViewModel.getInstance();
        mapViewModel = MapViewModel.getInstance();
    }

    public void setTourDetails(TourModel tour) {
        titleLabel.setText(tour.getName());
        tourName.setText("Tour Name: " + tour.getName());
        tourStart.setText("Start: " + tour.getStart());
        tourDestination.setText("Destination: " + tour.getDestination());
        tourTransportationType.setText("Transportation Type: " + tour.getTransportationType());
        tourDistance.setText("Distance: " + tourViewModel.formatDistance(tour.getDistance()));
        tourTime.setText("Time: " + tourViewModel.formatTime(tour.getTime()));

        String filename = (mapViewModel.getMapById(tour.getId())).getFilename();
        String imageName = "img/maps/" + filename.toLowerCase();
        URL resource = MainTourPlaner.class.getResource(imageName);

        Image map = new Image(resource.toString());
        mapImage.setPreserveRatio(true);
        mapImage.setImage(map);

    }

    @FXML
    public void onRefreshButtonClicked(ActionEvent event) {
    }

    public void onAddLogButtonClicked(ActionEvent actionEvent) {

    }

    public void onEditButtonClicked(ActionEvent actionEvent) {
    }
}
