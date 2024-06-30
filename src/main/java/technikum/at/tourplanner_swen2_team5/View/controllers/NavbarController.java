package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
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
    @FXML
    private VBox searchResultsContainer;

    @Autowired
    private EventHandler eventHandler;

    private final TourViewModel tourViewModel;
    private final TourLogViewModel tourLogViewModel;

    @Autowired
    public NavbarController(TourViewModel tourViewModel, TourLogViewModel tourLogViewModel) {
        this.tourViewModel = tourViewModel;
        this.tourLogViewModel = tourLogViewModel;
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
        searchBar.setOnKeyPressed(this::handleEnterKey);
    }

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            performSearch();
        }
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
        searchResultsContainer.getChildren().clear();
        if (results.isEmpty()) {
            searchResultsContainer.getChildren().add(new Label("No tour results found."));
        } else {
            results.forEach(tour -> {
                Label label = new Label("Tour: " + tour.getName());
                searchResultsContainer.getChildren().add(label);
            });
        }
        log.info("Found {} tours", results.size());
    }

    private void displayTourLogSearchResults(List<TourLogModel> results) {
        if (results.isEmpty()) {
            searchResultsContainer.getChildren().add(new Label("No tour log results found."));
        } else {
            results.forEach(tourLog -> {
                Label label = new Label("Tour Log: " + tourLog.getComment());
                searchResultsContainer.getChildren().add(label);
            });
        }
        log.info("Found {} tour logs", results.size());
    }
}
