package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.services.SearchService;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;

import java.util.List;

@Slf4j
@Component
public class NavbarController {

    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;

    private final SearchService searchService;

    @Autowired
    public NavbarController(SearchService searchService) {
        this.searchService = searchService;
    }

    @FXML
    private void onAddTourClicked() {
        EventHandler.openAddTour();
    }

    @FXML
    private void onMapPinClicked() {
        EventHandler.openTourList();
    }

    @FXML
    public void initialize() {
        searchButton.setOnAction(event -> performSearch());
    }

    private void performSearch() {
        String query = searchBar.getText();
        if (query != null && !query.isEmpty()) {
            List<TourModel> results = searchService.searchTours(query);
            displaySearchResults(results);
        }
    }

    private void displaySearchResults(List<TourModel> results) {
        // Code to update the UI with search results
        results.forEach(tour -> log.info("Found tour: {}", tour.getName()));
        // Update your UI elements to show the search results
    }
}