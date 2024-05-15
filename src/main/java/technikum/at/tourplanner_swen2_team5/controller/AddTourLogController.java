package technikum.at.tourplanner_swen2_team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.models.MapModel;
import technikum.at.tourplanner_swen2_team5.models.TourModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.MapViewModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourViewModel;

public class AddTourLogController {
    @FXML
    private DatePicker dateField;
    @FXML
    private TextArea commentArea;
    @FXML
    private ComboBox<String> difficultyBox;
    @FXML
    private TextField distanceField;
    @FXML
    private TextField totalTimeField;
    @FXML
    private Slider ratingSlider;
    @FXML
    private ComboBox<String> transportTypeBox;

    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button backButton;

    @FXML
    private Label warningLabelDate;
    @FXML
    private Label warningLabelComment;
    @FXML
    private Label warningLabelDifficulty;
    @FXML
    private Label warningLabelDistance;
    @FXML
    private Label warningLabelTotalTime;
    @FXML
    private Label warningLabelRating;
    @FXML
    private Label warningLabelTransportationType;

    private TourViewModel tourViewModel;
    private MapViewModel mapViewModel;
    private final TourModel currentTour = new TourModel();

    public void initialize() {
        tourViewModel = TourViewModel.getInstance();
        mapViewModel = MapViewModel.getInstance();
        //bindFieldsToModel(currentTour);
        difficultyBox.getItems().setAll("Easy", "Moderate", "Challenging", "Difficult");
    }

    /*private void bindFieldsToModel(TourModel tour) {
        nameField.textProperty().bindBidirectional(tour.nameProperty());
        descriptionArea.textProperty().bindBidirectional(tour.descriptionProperty());
        startField.textProperty().bindBidirectional(tour.startProperty());
        destinationField.textProperty().bindBidirectional(tour.destinationProperty());
        transportTypeBox.valueProperty().bindBidirectional(tour.transportTypeProperty());
    }*/

    @FXML
    private void onSaveButtonClicked() {
        /*if (validateInputs()) {
            updateTourModelFromFields();
            tourViewModel.addTour(currentTour);
            mapViewModel.addMap(new MapModel(currentTour.getId(), "map-placeholder.png"));
            closeStage();
        }*/
    }

    /*private void updateTourModelFromFields() {
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
    }*/

    public void onDeleteButtonClicked(ActionEvent actionEvent) {
        ConfirmationWindow dialog = new ConfirmationWindow(
                (Stage) deleteButton.getScene().getWindow(),
                "Delete Tour",
                "Deletion Confirmation",
                "Do you want to delete this tour?"
        );

        // Überprüfe die Benutzerantwort
        if (dialog.showAndWait()) {
            //closeStage();
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
            //closeStage();
        }
    }
}
