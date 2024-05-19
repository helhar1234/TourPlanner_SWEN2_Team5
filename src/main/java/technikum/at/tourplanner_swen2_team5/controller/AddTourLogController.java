package technikum.at.tourplanner_swen2_team5.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import technikum.at.tourplanner_swen2_team5.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.viewmodels.TourLogViewModel;

import java.time.LocalDate;

public class AddTourLogController {
    @FXML
    private DatePicker dateField;
    @FXML
    private TextField timeField;
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
    private Label warningLabelTime;
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

    private TourLogViewModel tourLogViewModel;
    private final TourLogModel currentTourLog = new TourLogModel();

    public void initialize() {
        tourLogViewModel = TourLogViewModel.getInstance();
        bindFieldsToModel(currentTourLog);
        dateField.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Deaktiviere zukünftige Daten
                        if (item.isAfter(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #EEEEEE;");
                        }
                    }
                };
            }
        });
        difficultyBox.getItems().setAll("Easy", "Moderate", "Challenging", "Difficult");
        transportTypeBox.getItems().setAll("Hike", "Bike", "Running", "Vacation");
        ratingSlider.valueProperty().addListener((obs, oldval, newVal) -> updateSliderFill(newVal));
        Platform.runLater(() -> updateSliderFill(ratingSlider.getValue()));
    }

    public void setTourLogTourId(String tourId) {
        this.currentTourLog.setTourId(tourId);
    }

    private void updateSliderFill(Number value) {
        // Umrechnung des Sliderwerts in Prozent
        double percentage = (value.doubleValue() - ratingSlider.getMin()) / (ratingSlider.getMax() - ratingSlider.getMin());

        // Erstellen des Stils für den gefüllten und den nicht-gefüllten Track
        String style = String.format("-fx-background-color: linear-gradient(to right, #A4D65E %.0f%%, white %.0f%%);", percentage * 100, percentage * 100);
        ratingSlider.lookup(".track").setStyle(style);
    }

    private void bindFieldsToModel(TourLogModel tourLog) {
        dateField.valueProperty().bindBidirectional(tourLog.dateProperty());
        timeField.textProperty().bindBidirectional(tourLog.timeProperty());
        commentArea.textProperty().bindBidirectional(tourLog.commentProperty());
        difficultyBox.valueProperty().bindBidirectional(tourLog.difficultyProperty());
        distanceField.textProperty().bindBidirectional(tourLog.distanceProperty());
        totalTimeField.textProperty().bindBidirectional(tourLog.totalTimeProperty());
        ratingSlider.valueProperty().bindBidirectional(tourLog.ratingProperty());
        transportTypeBox.valueProperty().bindBidirectional(tourLog.transportTypeProperty());
    }

    @FXML
    private void onSaveButtonClicked() {
        if (validateInputs()) {
            updateTourLogModelFromFields();
            tourLogViewModel.addTourLog(currentTourLog);
            closeStage();
        }
    }

    private void updateTourLogModelFromFields() {
        currentTourLog.setDate(dateField.getValue());
        currentTourLog.setTime(timeField.getText());
        currentTourLog.setComment(commentArea.getText());
        currentTourLog.setDifficulty(difficultyBox.getValue());
        currentTourLog.setDistance(distanceField.getText());
        currentTourLog.setTotalTime(totalTimeField.getText());
        currentTourLog.setRating((int) ratingSlider.getValue());
        currentTourLog.setTransportType(transportTypeBox.getValue());
    }

    private boolean validateInputs() {
        boolean hasError = false;
        hasError |= setFieldError(dateField, warningLabelDate, tourLogViewModel.validateDate(dateField.getValue()));
        hasError |= setFieldError(timeField, warningLabelTime, tourLogViewModel.validateTime(timeField.getText()));
        hasError |= setFieldError(commentArea, warningLabelComment, tourLogViewModel.validateComment(commentArea.getText()));
        hasError |= setFieldError(difficultyBox, warningLabelDifficulty, tourLogViewModel.validateDifficulty(difficultyBox.getValue()));
        hasError |= setFieldError(distanceField, warningLabelDistance, tourLogViewModel.validateDistance(distanceField.getText()));
        hasError |= setFieldError(totalTimeField, warningLabelTotalTime, tourLogViewModel.validateTotalTime(totalTimeField.getText()));
        hasError |= setFieldError(transportTypeBox, warningLabelTransportationType, tourLogViewModel.validateTransportationType(transportTypeBox.getValue()));
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
                "Delete TourLog",
                "Deletion Confirmation",
                "Do you want to delete this tourlog?"
        );

        // Überprüfe die Benutzerantwort
        if (dialog.showAndWait()) {
            closeStage();
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) {
        ConfirmationWindow dialog = new ConfirmationWindow(
                (Stage) backButton.getScene().getWindow(),
                "Return to Tour",
                "Return to Tour",
                "If you go back now, your TourLog will not be saved!"
        );

        // Überprüfe die Benutzerantwort
        if (dialog.showAndWait()) {
            closeStage();
        }
    }
}
