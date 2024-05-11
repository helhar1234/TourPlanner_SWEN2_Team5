package technikum.at.tourplanner_swen2_team5.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class ConfirmationWindow {
    private Alert alert;

    public ConfirmationWindow(Stage parentStage, String title, String header, String content) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(parentStage);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    }

    public boolean showAndWait() {
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}

