package technikum.at.tourplanner_swen2_team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import technikum.at.tourplanner_swen2_team5.models.TourModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

public class TourDetailController {

    @FXML private Label titleLabel;
    @FXML private Label tourName;
    @FXML private Label tourStart;
    @FXML private Label tourDestination;
    @FXML private Label tourTransportationType;
    @FXML private Label tourDistance;
    @FXML private Label tourTime;

    private TourViewModel viewModel;

    public void initialize (){
        viewModel = TourViewModel.getInstance();
    }

    public void setTourDetails(TourModel tour) {
        titleLabel.setText(tour.getName());
        tourName.setText("Tour Name: " + tour.getName());
        tourStart.setText("Start: " + tour.getStart());
        tourDestination.setText("Destination: " + tour.getDestination());
        tourTransportationType.setText("Transportation Type: " + tour.getTransportationType());
        tourDistance.setText("Distance: " + viewModel.formatDistance(tour.getDistance()));
        tourTime.setText("Time: " + viewModel.formatTime(tour.getTime()));
    }

    @FXML
    public void onRefreshButtonClicked(ActionEvent event) {
    }
}
