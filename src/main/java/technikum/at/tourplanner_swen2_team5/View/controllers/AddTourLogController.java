package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import technikum.at.tourplanner_swen2_team5.BL.models.DifficultyModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;
import technikum.at.tourplanner_swen2_team5.BL.services.DifficultyService;
import technikum.at.tourplanner_swen2_team5.BL.services.TransportTypeService;
import technikum.at.tourplanner_swen2_team5.BL.validation.TourLogValidationService;
import technikum.at.tourplanner_swen2_team5.View.controllers.ConfirmationWindow;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourLogViewModel;
import technikum.at.tourplanner_swen2_team5.util.Formatter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddTourLogController {
    @FXML
    private DatePicker dateField;
    @FXML
    private ComboBox<Integer> timeFieldHours;
    @FXML
    private ComboBox<Integer> timeFieldMinutes;
    @FXML
    private TextArea commentArea;
    @FXML
    private ComboBox<DifficultyModel> difficultyBox;
    @FXML
    private TextField distanceField;
    @FXML
    private TextField totalTimeField;
    @FXML
    private Slider ratingSlider;
    @FXML
    private ComboBox<TransportTypeModel> transportTypeBox;

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

    private DifficultyService difficultyService;
    private Formatter formatter;
    private TourLogValidationService validationService;
    private TransportTypeService transportTypeService;

    public void initialize() {
        tourLogViewModel = TourLogViewModel.getInstance();
        difficultyService = new DifficultyService();
        transportTypeService = new TransportTypeService();
        validationService = new TourLogValidationService();
        formatter = new Formatter();
        loadTimeHours();
        loadTimeMinutes();
        loadDifficultyTypes();
        loadTransportTypes();
        dateField.setDayCellFactory(getDayCellFactory());

        ratingSlider.valueProperty().addListener((obs, oldval, newVal) -> updateSliderFill(newVal));
        Platform.runLater(() -> updateSliderFill(ratingSlider.getValue()));
    }

    public void setTourLogTour(TourModel tour) {
        this.currentTourLog.setTour(tour);
    }

    private void updateSliderFill(Number value) {
        double percentage = (value.doubleValue() - ratingSlider.getMin()) / (ratingSlider.getMax() - ratingSlider.getMin());
        String style = String.format("-fx-background-color: linear-gradient(to right, #A4D65E %.0f%%, white %.0f%%);", percentage * 100, percentage * 100);
        ratingSlider.lookup(".track").setStyle(style);
    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #EEEEEE;");
                }
            }
        };
    }

    private void loadTimeHours() {
        List<Integer> hours = IntStream.rangeClosed(0, 23).boxed().collect(Collectors.toList());
        timeFieldHours.setItems(FXCollections.observableArrayList(hours));
    }

    private void loadTimeMinutes() {
        List<Integer> minutes = IntStream.rangeClosed(0, 59).boxed().collect(Collectors.toList());
        timeFieldMinutes.setItems(FXCollections.observableArrayList(minutes));
    }

    private void loadDifficultyTypes() {
        List<DifficultyModel> difficulties = difficultyService.getAllDifficulties();
        difficultyBox.setItems(FXCollections.observableArrayList(difficulties));
        difficultyBox.setConverter(new StringConverter<DifficultyModel>() {
            @Override
            public String toString(DifficultyModel object) {
                return object != null ? object.getDifficulty() : "";
            }

            @Override
            public DifficultyModel fromString(String string) {
                return difficulties.stream().filter(item -> item.getDifficulty().equals(string)).findFirst().orElse(null);
            }
        });
    }

    private void loadTransportTypes() {
        List<TransportTypeModel> transportTypes = transportTypeService.getAllTransportTypes();
        transportTypeBox.setItems(FXCollections.observableArrayList(transportTypes));
        transportTypeBox.setConverter(new StringConverter<TransportTypeModel>() {
            @Override
            public String toString(TransportTypeModel object) {
                return object != null ? object.getName() : "";
            }

            @Override
            public TransportTypeModel fromString(String string) {
                return transportTypes.stream().filter(item -> item.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    @FXML
    private void onSaveButtonClicked() {
        updateTourLogModelFromFields();
        if (validateInputs()) {
            currentTourLog.setTotalTime(formatter.formatTime_hm(currentTourLog.getTotalTime()));
            tourLogViewModel.addTourLog(currentTourLog);
            closeStage();
        }
    }

    private void updateTourLogModelFromFields() {
        Integer timeHours = (timeFieldHours.getValue() != null) ? timeFieldHours.getValue() : null;
        Integer timeMinutes = (timeFieldMinutes.getValue() != null) ? timeFieldMinutes.getValue() : null;

        currentTourLog.setDate(formatter.formatDate(String.valueOf(dateField.getValue())));
        currentTourLog.setTimeHours(timeHours != null ? timeHours : 0);
        currentTourLog.setTimeMinutes(timeMinutes != null ? timeMinutes : 0);
        currentTourLog.setComment(commentArea.getText());
        currentTourLog.setDifficulty(difficultyBox.getValue());
        currentTourLog.setDistance(distanceField.getText());
        currentTourLog.setTotalTime(totalTimeField.getText());
        currentTourLog.setRating((int) ratingSlider.getValue());
        currentTourLog.setTransportType(transportTypeBox.getValue());
    }

    private boolean validateInputs() {
        Map<String, String> errors = validationService.validateTourLog(currentTourLog);
        boolean hasError = false;

        hasError |= setFieldError(dateField, warningLabelDate, errors.get("date"));
        hasError |= setFieldError(timeFieldHours, warningLabelTime, errors.get("time"));
        hasError |= setFieldError(timeFieldMinutes, warningLabelTime, errors.get("time"));
        hasError |= setFieldError(commentArea, warningLabelComment, errors.get("comment"));
        hasError |= setFieldError(difficultyBox, warningLabelDifficulty, errors.get("difficulty"));
        hasError |= setFieldError(distanceField, warningLabelDistance, errors.get("distance"));
        hasError |= setFieldError(totalTimeField, warningLabelTotalTime, errors.get("totalTime"));
        hasError |= setFieldError(ratingSlider, warningLabelRating, errors.get("rating"));
        hasError |= setFieldError(transportTypeBox, warningLabelTransportationType, errors.get("transportType"));

        return !hasError;
    }

    private void closeStage() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private boolean setFieldError(Control field, Label warningLabel, String errorMessage) {
        if (errorMessage != null) {
            warningLabel.setText(errorMessage);
            field.setStyle("-fx-border-color: red;");
            return true;
        } else {
            warningLabel.setText("");
            field.setStyle("");
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

        if (dialog.showAndWait()) {
            closeStage();
        }
    }
}
