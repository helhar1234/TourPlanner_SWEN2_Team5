package technikum.at.tourplanner_swen2_team5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.models.TourModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

import java.io.IOException;

public class AddTourController {
    @FXML private TextField nameField;
    @FXML private TextArea descriptionArea;
    @FXML private TextField fromField;
    @FXML private TextField toField;
    @FXML private ComboBox<String> transportTypeBox;

    @FXML private Button saveButton;

    @FXML private Label warningLabelName;
    @FXML private Label warningLabelDescription;
    @FXML private Label warningLabelStart;
    @FXML private Label warningLabelDestination;
    @FXML private Label warningLabelTransportationType;

    private TourViewModel tourViewModel;

    public void initialize() {
        tourViewModel = TourViewModel.getInstance();

        TourModel newTour = new TourModel();
        nameField.textProperty().bindBidirectional(newTour.nameProperty());
        descriptionArea.textProperty().bindBidirectional(newTour.descriptionProperty());
        fromField.textProperty().bindBidirectional(newTour.fromProperty());
        toField.textProperty().bindBidirectional(newTour.toProperty());
        transportTypeBox.valueProperty().bindBidirectional(newTour.transportTypeProperty());

        transportTypeBox.getItems().setAll("Hike", "Bike", "Running", "Vacation");
    }

    @FXML
    private void onSaveButtonClicked() {
        boolean hasError = false;

        hasError |= setFieldError(nameField, warningLabelName, tourViewModel.validateName(nameField.getText()));
        hasError |= setFieldError(descriptionArea, warningLabelDescription, tourViewModel.validateDescription(descriptionArea.getText()));
        hasError |= setFieldError(fromField, warningLabelStart, tourViewModel.validateStart(fromField.getText()));
        hasError |= setFieldError(toField, warningLabelDestination, tourViewModel.validateDestination(toField.getText()));
        hasError |= setFieldError(transportTypeBox, warningLabelTransportationType, tourViewModel.validateTransportationType(transportTypeBox.getValue()));

        if (!hasError) {
            TourModel tour = new TourModel(
                    nameField.getText(),
                    descriptionArea.getText(),
                    fromField.getText(),
                    toField.getText(),
                    transportTypeBox.getSelectionModel().getSelectedItem()
            );
            tourViewModel.addTour(tour);
            closeStage();
        }
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



}

