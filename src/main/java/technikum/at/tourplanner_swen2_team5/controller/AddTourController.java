package technikum.at.tourplanner_swen2_team5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.models.Tour;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

public class AddTourController {
    @FXML private TextField nameField;
    @FXML private TextArea descriptionArea;
    @FXML private TextField fromField;
    @FXML private TextField toField;
    @FXML private ComboBox<String> transportTypeBox;

    @FXML private Button saveButton;

    private TourViewModel viewModel;

    public void initialize() {
        viewModel = TourViewModel.getInstance();

        Tour newTour = new Tour();
        nameField.textProperty().bindBidirectional(newTour.nameProperty());
        descriptionArea.textProperty().bindBidirectional(newTour.descriptionProperty());
        fromField.textProperty().bindBidirectional(newTour.fromProperty());
        toField.textProperty().bindBidirectional(newTour.toProperty());
        transportTypeBox.valueProperty().bindBidirectional(newTour.transportTypeProperty());

        transportTypeBox.getItems().setAll("Hike", "Bike", "Running", "Vacation");
    }

    @FXML
    private void onSaveButtonClicked() {
        Tour tour = new Tour(
                nameField.getText(),
                descriptionArea.getText(),
                fromField.getText(),
                toField.getText(),
                transportTypeBox.getSelectionModel().getSelectedItem()
        );
        viewModel.addTour(tour);
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) saveButton.getScene().getWindow();  // Hole die Stage über den Save-Button
        stage.close();  // Schließe die Stage
    }
}

