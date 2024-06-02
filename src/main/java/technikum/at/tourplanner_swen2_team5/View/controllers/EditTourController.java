package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import technikum.at.tourplanner_swen2_team5.BL.validation.TourValidationService;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;
import technikum.at.tourplanner_swen2_team5.BL.services.TransportTypeService;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;

import java.util.List;
import java.util.Map;

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
    private ComboBox<TransportTypeModel> transportTypeBox;
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
    private TourValidationService validationService;
    private TransportTypeService transportTypeService;
    private TourModel currentTour;

    public void initialize() {
        tourViewModel = TourViewModel.getInstance();
        validationService = new TourValidationService();
        transportTypeService = new TransportTypeService();
        loadTransportTypes();
    }

    public void setTour(TourModel tour) {
        this.currentTour = tour;
        if (currentTour != null) {
            loadTourDetails();
        }
    }

    private void loadTransportTypes() {
        List<TransportTypeModel> transportTypes = transportTypeService.getAllTransportTypes();
        transportTypeBox.getItems().setAll(transportTypes);
        transportTypeBox.setConverter(new StringConverter<TransportTypeModel>() {
            @Override
            public String toString(TransportTypeModel object) {
                return object.getName();
            }

            @Override
            public TransportTypeModel fromString(String string) {
                return transportTypes.stream().filter(item -> item.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }


    private void loadTourDetails() {
        nameField.setText(currentTour.getName());
        descriptionArea.setText(currentTour.getDescription());
        startField.setText(currentTour.getStart());
        destinationField.setText(currentTour.getDestination());
        transportTypeBox.setValue(currentTour.getTransportType());
    }

    @FXML
    private void onSaveButtonClicked() {
        if (validateInputs()) {
            updateTourModelFromFields();
            tourViewModel.updateTour(currentTour);
            closeStage();
        }
    }

    private boolean validateInputs() {
        updateTourModelFromFields();
        Map<String, String> errors = validationService.validateTour(currentTour);
        Map<String, String> nameError = validationService.validateNameExists(currentTour.getName(), tourViewModel.getTourById(currentTour.getId()).getName());

        boolean hasError = false;
        if (!errors.isEmpty() || !nameError.isEmpty()) {
            hasError = true;
            setFieldError(nameField, warningLabelName, errors.get("name"));
            setFieldError(nameField, warningLabelName, nameError.get("name"));
            setFieldError(descriptionArea, warningLabelDescription, errors.get("description"));
            setFieldError(startField, warningLabelStart, errors.get("start"));
            setFieldError(destinationField, warningLabelDestination, errors.get("destination"));
            setFieldError(transportTypeBox, warningLabelTransportationType, errors.get("transportType"));
        }

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

    private void updateTourModelFromFields() {
        currentTour.setName(nameField.getText());
        currentTour.setDescription(descriptionArea.getText());
        currentTour.setStart(startField.getText());
        currentTour.setDestination(destinationField.getText());
        currentTour.setTransportType(transportTypeBox.getValue());
    }

    @FXML
    private void onDeleteButtonClicked(ActionEvent actionEvent) {
        ConfirmationWindow dialog = new ConfirmationWindow(
                (Stage) deleteButton.getScene().getWindow(),
                "Delete Tour",
                "Deletion Confirmation",
                "Do you want to delete this tour?"
        );

        if (dialog.showAndWait()) {
            tourViewModel.deleteTour(currentTour);
            closeStage();
        }
    }

    @FXML
    private void onBackButtonClicked(ActionEvent actionEvent) {
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

    private void closeStage() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
