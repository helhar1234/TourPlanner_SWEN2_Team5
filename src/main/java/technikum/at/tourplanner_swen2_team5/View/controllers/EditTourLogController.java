package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import technikum.at.tourplanner_swen2_team5.BL.models.DifficultyModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TourLogModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;
import technikum.at.tourplanner_swen2_team5.BL.services.DifficultyService;
import technikum.at.tourplanner_swen2_team5.BL.services.TransportTypeService;
import technikum.at.tourplanner_swen2_team5.BL.validation.TourLogValidationService;
import technikum.at.tourplanner_swen2_team5.View.controllers.ConfirmationWindow;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourLogViewModel;
import technikum.at.tourplanner_swen2_team5.util.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class EditTourLogController {

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
    private Label warningLabelDistance;
    @FXML
    private Label warningLabelTotalTime;
    @FXML
    private Label warningLabelRating;
    @FXML
    private Label warningLabelDifficulty;
    @FXML
    private Label warningLabelTransportationType;

    private TourLogViewModel tourLogViewModel;
    private TourLogModel currentTourLog;
    private TourLogValidationService validationService;
    private DifficultyService difficultyService;
    private Formatter formatter;
    private TransportTypeService transportTypeService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void initialize() {
        tourLogViewModel = TourLogViewModel.getInstance();
        validationService = new TourLogValidationService();
        difficultyService = new DifficultyService();
        transportTypeService = new TransportTypeService();
        formatter = new Formatter();

        loadDifficultyTypes();
        loadTransportTypes();
        ratingSlider.valueProperty().addListener((obs, oldval, newVal) -> updateSliderFill(newVal));
    }

    public void setTourLog(TourLogModel tourLog) {
        this.currentTourLog = tourLog;
        if (currentTourLog != null) {
            loadTourLogDetails();
        }
    }

    private void loadTourLogDetails() {
        dateField.setValue(LocalDate.parse(formatter.formatDateReverse(currentTourLog.getDate())));
        currentTourLog.setTimeHours(currentTourLog.getTimeHours());
        currentTourLog.setTimeMinutes(currentTourLog.getTimeMinutes());
        commentArea.setText(currentTourLog.getComment());
        difficultyBox.setValue(currentTourLog.getDifficulty());
        distanceField.setText(currentTourLog.getDistance());
        totalTimeField.setText(currentTourLog.getTotalTime());
        ratingSlider.setValue(currentTourLog.getRating());
        updateSliderFill(ratingSlider.getValue());
        transportTypeBox.setValue(currentTourLog.getTransportType());
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
            tourLogViewModel.updateTourLog(currentTourLog);
            closeStage();
        }
    }

    @FXML
    private void onDeleteButtonClicked() {
        ConfirmationWindow dialog = new ConfirmationWindow(
                (Stage) deleteButton.getScene().getWindow(),
                "Delete Tour",
                "Deletion Confirmation",
                "Do you want to delete this tour?"
        );

        if (dialog.showAndWait()) {
            tourLogViewModel.deleteTourLogById(currentTourLog.getId());
            closeStage();
        }
    }

    private boolean validateInputs() {
        Map<String, String> errors = validationService.validateTourLog(currentTourLog);
        boolean hasError = !errors.isEmpty();

        setFieldError(dateField, warningLabelDate, errors.get("date"));
        setFieldError(timeFieldHours, warningLabelTime, errors.get("time"));
        setFieldError(timeFieldMinutes, warningLabelTime, errors.get("time"));
        setFieldError(commentArea, warningLabelComment, errors.get("comment"));
        setFieldError(distanceField, warningLabelDistance, errors.get("distance"));
        setFieldError(totalTimeField, warningLabelTotalTime, errors.get("totalTime"));
        setFieldError(ratingSlider, warningLabelRating, errors.get("rating"));
        setFieldError(difficultyBox, warningLabelDifficulty, errors.get("difficulty"));
        setFieldError(transportTypeBox, warningLabelTransportationType, errors.get("transportType"));

        return !hasError;
    }

    private void setFieldError(Control field, Label warningLabel, String errorMessage) {
        if (errorMessage != null) {
            warningLabel.setText(errorMessage);
            field.setStyle("-fx-border-color: red;");
        } else {
            warningLabel.setText("");
            field.setStyle("");
        }
    }

    private void updateTourLogModelFromFields() {
        currentTourLog.setDate(formatter.formatDate(String.valueOf(dateField.getValue())));
        currentTourLog.setTimeHours(timeFieldHours.getValue());
        currentTourLog.setTimeMinutes(timeFieldMinutes.getValue());
        currentTourLog.setComment(commentArea.getText());
        currentTourLog.setDifficulty(difficultyBox.getValue());
        currentTourLog.setDistance(distanceField.getText());
        currentTourLog.setTotalTime(totalTimeField.getText());
        currentTourLog.setRating((int) ratingSlider.getValue());
        currentTourLog.setTransportType(transportTypeBox.getValue());
    }

    private void closeStage() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public void onBackButtonClicked() {
        ConfirmationWindow dialog = new ConfirmationWindow(
                (Stage) backButton.getScene().getWindow(),
                "Return to Tour Planner",
                "Return to Tour Planner",
                "If you go back now, your Changes will not be saved!"
        );

        if (dialog.showAndWait()) {
            closeStage();
        }
    }

    private void updateSliderFill(Number value) {
        double percentage = (value.doubleValue() - ratingSlider.getMin()) / (ratingSlider.getMax() - ratingSlider.getMin());
        String style = String.format("-fx-background-color: linear-gradient(to right, #A4D65E %.0f%%, white %.0f%%);", percentage * 100, percentage * 100);
        Platform.runLater(() -> ratingSlider.lookup(".track").setStyle(style));
    }
}
