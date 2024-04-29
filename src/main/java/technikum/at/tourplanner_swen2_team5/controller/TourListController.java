package technikum.at.tourplanner_swen2_team5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import technikum.at.tourplanner_swen2_team5.models.Tour;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

public class TourListController {
    @FXML private TableView<Tour> toursTable;
    @FXML private TableColumn<Tour, String> colType;
    @FXML private TableColumn<Tour, String> colName;
    @FXML private TableColumn<Tour, String> colStart;
    @FXML private TableColumn<Tour, String> colDestination;
    @FXML private TableColumn<Tour, String> colDistance;
    @FXML private TableColumn<Tour, String> colTime;

    private TourViewModel viewModel;

    public void initialize() {
        viewModel = TourViewModel.getInstance();

        // Hier werden die Zellen der Spalten an die Properties gebunden
        colType.setCellValueFactory(new PropertyValueFactory<>("transportType"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("from"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("to"));
        // colDistance.setCellValueFactory(new PropertyValueFactory<>("distance"));
        // colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        toursTable.setItems(viewModel.getTours());
    }
}



