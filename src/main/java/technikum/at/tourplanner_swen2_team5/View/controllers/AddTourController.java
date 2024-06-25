package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import technikum.at.tourplanner_swen2_team5.BL.models.TourModel;
import technikum.at.tourplanner_swen2_team5.BL.models.TransportTypeModel;
import technikum.at.tourplanner_swen2_team5.BL.services.TransportTypeService;
import technikum.at.tourplanner_swen2_team5.BL.validation.TourMapValidationService;
import technikum.at.tourplanner_swen2_team5.BL.validation.TourValidationService;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.MapViewModel;
import technikum.at.tourplanner_swen2_team5.View.viewmodels.TourViewModel;
import technikum.at.tourplanner_swen2_team5.util.EventHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class AddTourController {
    @FXML private TextField nameField, startField, destinationField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<TransportTypeModel> transportTypeBox;
    @FXML private Button saveButton, deleteButton, backButton;
    @FXML private Label warningLabelName, warningLabelDescription, warningLabelStart, warningLabelDestination, warningLabelTransportationType;

    private TourViewModel tourViewModel = TourViewModel.getInstance();
    private MapViewModel mapViewModel = MapViewModel.getInstance();
    private TourValidationService tourValidationService = new TourValidationService();
    private TourMapValidationService mapValidationService = new TourMapValidationService();
    private TransportTypeService transportTypeService = new TransportTypeService();
    private TourModel currentTour = new TourModel();

    public void initialize() {
        loadTransportTypes();
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
    private void onSaveButtonClicked() throws IOException {
        if (validateInputs() && validateRoute()) {
            updateTourModelFromFields();
            tourViewModel.addTour(currentTour);
            mapViewModel.addMap(currentTour);
            closeStage();
            log.info("Successfully saved tour with id {}", currentTour.getId());
        }
    }


    private boolean validateRoute() {

        boolean validStart = TourMapValidationService.isValidLocation(currentTour.getStart());
        boolean validDestination = TourMapValidationService.isValidLocation(currentTour.getDestination());

        if (!validStart) {
            setFieldError(startField, warningLabelStart, "Invalid Start Location in Europe");
        }
        if (!validDestination) {
            setFieldError(destinationField, warningLabelDestination, "Invalid Destination Location in Europe");
        }

        return validStart && validDestination;
    }

    private void updateTourModelFromFields() {
        currentTour.setName(nameField.getText());
        currentTour.setDescription(descriptionArea.getText());
        currentTour.setStart(startField.getText());
        currentTour.setDestination(destinationField.getText());
        currentTour.setTransportType(transportTypeBox.getValue());
    }

    private boolean validateInputs() {
        updateTourModelFromFields();
        Map<String, String> errors = tourValidationService.validateTour(currentTour);
        Map<String, String> nameError = tourValidationService.validateNameExists(currentTour.getName(), null);


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

    private void closeStage() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onDeleteButtonClicked() throws IOException {
        log.info("Delete tour button clicked for Tour {}", currentTour.getName());
        if (EventHandler.confirmBack((Stage) deleteButton.getScene().getWindow(), "Delete Tour", "Deletion Confirmation", "Do you want to delete this tour?")) {
            tourViewModel.deleteTour(currentTour);
            closeStage();
        }
    }

    @FXML
    private void onBackButtonClicked() {
        if (EventHandler.confirmBack((Stage) backButton.getScene().getWindow(), "Return to Tour Planner", "Return Confirmation", "If you go back now, your Tour will not be saved!")) {
            closeStage();
        }
    }
}
