package technikum.at.tourplanner_swen2_team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.models.TourModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

public class EditTourController {
    @FXML
    private TextField nameField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField startField;
    @FXML
    private TextField destinationField;
    @FXML
    private ComboBox<String> transportTypeBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;
    @FXML
    private Button backButton;

    @FXML
    private Label warningLabelName;
    @FXML
    private Label warningLabelDescription;
    @FXML
    private Label warningLabelStart;
    @FXML
    private Label warningLabelDestination;
    @FXML
    private Label warningLabelTransportationType;

    private TourViewModel tourViewModel;
    private TourModel currentTour;

    public void initialize() {
        tourViewModel = TourViewModel.getInstance();
        transportTypeBox.getItems().setAll("Hike", "Bike", "Running", "Vacation");
    }

    public void setTour(TourModel tour) {
        //System.out.println(tour.getId());
        this.currentTour = tour;
        if (currentTour != null) {
            unbindFieldsToModel(currentTour);
            loadTourDetails();
        }
    }

    private void bindFieldsToModel(TourModel tour) {
        nameField.textProperty().bindBidirectional(tour.nameProperty());
        descriptionArea.textProperty().bindBidirectional(tour.descriptionProperty());
        startField.textProperty().bindBidirectional(tour.startProperty());
        destinationField.textProperty().bindBidirectional(tour.destinationProperty());
        transportTypeBox.valueProperty().bindBidirectional(tour.transportTypeProperty());
    }

    private void unbindFieldsToModel(TourModel tour) {
        nameField.textProperty().unbind();
        descriptionArea.textProperty().unbind();
        startField.textProperty().unbind();
        destinationField.textProperty().unbind();
        transportTypeBox.valueProperty().unbind();
    }

    private void loadTourDetails() {
        nameField.setText(currentTour.getName());
        descriptionArea.setText(currentTour.getDescription());
        startField.setText(currentTour.getStart());
        destinationField.setText(currentTour.getDestination());
        transportTypeBox.setValue(currentTour.getTransportationType());
    }

    @FXML
    private void onSaveButtonClicked() {
        if (validateInputs()) {
            updateTourModelFromFields();
            tourViewModel.updateTour(currentTour);
            bindFieldsToModel(currentTour);
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

    @FXML
    private void onDeleteButtonClicked(ActionEvent actionEvent) {
        // Erstelle und zeige den Bestätigungsdialog
        ConfirmationWindow dialog = new ConfirmationWindow(
                (Stage) deleteButton.getScene().getWindow(),
                "Delete Tour",
                "Deletion Confirmation",
                "Do you want to delete this tour?"
        );

        // Überprüfe die Benutzerantwort
        if (dialog.showAndWait()) {
            tourViewModel.deleteTourById(currentTour.getId());
            closeStage();
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        ConfirmationWindow dialog = new ConfirmationWindow(
                (Stage) backButton.getScene().getWindow(),
                "Return to Tour Planner",
                "Return to Tour Planner",
                "If you go back now, your Changes will not be saved!"
        );

        // Überprüfe die Benutzerantwort
        if (dialog.showAndWait()) {
            closeStage();
        }
    }


}

