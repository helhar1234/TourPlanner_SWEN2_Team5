package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourLogViewModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;

import java.util.List;

@Slf4j
@Component
public class NavbarController {

    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;

    @Autowired
    private EventHandler eventHandler;

    private final TourViewModel tourViewModel;

    private final TourLogViewModel tourLogViewModel;

    @Autowired
    public NavbarController(TourViewModel tourViewModel, TourLogViewModel tourLogViewModel) {
        this.tourViewModel = tourViewModel;
        this.tourLogViewModel  = tourLogViewModel;
    }

    @FXML
    private void onAddTourClicked() {
        log.info("onAddTourClicked");
        eventHandler.openAddTour();
    }

    @FXML
    private void onMapPinClicked() {
        log.info("onMapPinClicked");
        eventHandler.openTourList();
    }

    @FXML
    public void initialize() {
        searchButton.setOnAction(event -> performSearch());
    }

    private void performSearch() {
        String query = searchBar.getText();
        if (query != null && !query.isEmpty()) {
            List<TourModel> tourResults = tourViewModel.searchTours(query);
            log.info("Searched for {} in tours", query);
            List<TourLogModel> tourLogResults = tourLogViewModel.searchTourLogs(query);
            log.info("Searched for {} in tour logs", query);
            displayTourSearchResults(tourResults);
            displayTourLogSearchResults(tourLogResults);
        }
    }

    private void displayTourSearchResults(List<TourModel> results) {
        // Code to update the UI with search results
        log.info("Found {} tours", results.size());
        results.forEach(tour -> log.info("Found tour: {}", tour.getName()));
        // Update your UI elements to show the search results
    }

    private void displayTourLogSearchResults(List<TourLogModel> results) {
        // Code to update the UI with search results
        log.info("Found {} tour logs", results.size());
        results.forEach(tourlog -> log.info("Found tourlog: {}", tourlog.getComment()));
        // Update your UI elements to show the search results
    }
}