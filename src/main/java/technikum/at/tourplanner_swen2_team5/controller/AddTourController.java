package technikum.at.tourplanner_swen2_team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.models.TourModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

import java.io.IOException;

public class AddTourController {
    @FXML private TextField nameField;
    @FXML private TextArea descriptionArea;
    @FXML private TextField startField;
    @FXML private TextField destinationField;
    @FXML private ComboBox<String> transportTypeBox;

    @FXML private Button saveButton;
    @FXML private Button deleteButton;
    @FXML private Button backButton;

    @FXML private Label warningLabelName;
    @FXML private Label warningLabelDescription;
    @FXML private Label warningLabelStart;
    @FXML private Label warningLabelDestination;
    @FXML private Label warningLabelTransportationType;

    private TourViewModel tourViewModel;
    private TourModel currentTour = new TourModel();

    public void initialize() {
        tourViewModel = TourViewModel.getInstance();
        bindFieldsToModel(currentTour);
        transportTypeBox.getItems().setAll("Hike", "Bike", "Running", "Vacation");
    }

    private void bindFieldsToModel(TourModel tour) {
        nameField.textProperty().bindBidirectional(tour.nameProperty());
        descriptionArea.textProperty().bindBidirectional(tour.descriptionProperty());
        startField.textProperty().bindBidirectional(tour.startProperty());
        destinationField.textProperty().bindBidirectional(tour.destinationProperty());
        transportTypeBox.valueProperty().bindBidirectional(tour.transportTypeProperty());
    }

    @FXML
    private void onSaveButtonClicked() {
        if (validateInputs()) {
            updateTourModelFromFields();
            tourViewModel.addTour(currentTour);
            closeStage();
        }
    }

    private void updateTourModelFromFields() {
        currentTour.setName(nameField.getText());
        currentTour.setDescription(descriptionArea.getText());
        currentTour.setStart(startField.getText());
        currentTour.setDestination(destinationField.getText());
        currentTour.setTransportType(transportTypeBox.getValue());
    }

    private boolean validateInputs() {
        boolean hasError = false;
        hasError |= setFieldError(nameField, warningLabelName, tourViewModel.validateName(nameField.getText(), currentTour.getId()));
        hasError |= setFieldError(descriptionArea, warningLabelDescription, tourViewModel.validateDescription(descriptionArea.getText()));
        hasError |= setFieldError(startField, warningLabelStart, tourViewModel.validateStart(startField.getText()));
        hasError |= setFieldError(destinationField, warningLabelDestination, tourViewModel.validateDestination(destinationField.getText()));
        hasError |= setFieldError(transportTypeBox, warningLabelTransportationType, tourViewModel.validateTransportationType(transportTypeBox.getValue()));
        return !hasError;
    }

    private void closeStage() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private boolean setFieldError(Control field, Label warningLabel, String errorMessage) {
        if (errorMessage != null) {
            warningLabel.setText(errorMessage);
            field.setStyle("-fx-border-color: red;"); // Setzt die Umrandung auf rot
            return true;
        } else {
            warningLabel.setText("");
            field.setStyle(""); // Setzt den Stil zurück
            return false;
        }
    }


    public void onDeleteButtonClicked(ActionEvent actionEvent) {
        ConfirmationWindow dialog = new ConfirmationWindow(
                (Stage) deleteButton.getScene().getWindow(),
                "Delete Tour",
                "Deletion Confirmation",
                "Do you want to delete this tour?"
        );

        // Überprüfe die Benutzerantwort
        if (dialog.showAndWait()) {
            closeStage();
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        ConfirmationWindow dialog = new ConfirmationWindow(
                (Stage) backButton.getScene().getWindow(),
                "Return to Tour Planner",
                "Return to Tour Planner",
                "If you go back now, your Tour will not be saved!"
        );

        // Überprüfe die Benutzerantwort
        if (dialog.showAndWait()) {
            closeStage();
        }
    }
}

