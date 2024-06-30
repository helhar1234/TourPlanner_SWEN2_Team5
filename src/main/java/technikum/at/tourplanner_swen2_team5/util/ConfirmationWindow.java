package technikum.at.tourplanner_swen2_team5.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class ConfirmationWindow {

    private Alert alert;

    public ConfirmationWindow(Stage parentStage, String title, String header, String content) {
        this.alert = new Alert(Alert.AlertType.CONFIRMATION);
        this.alert.initOwner(parentStage);
        this.alert.initModality(Modality.WINDOW_MODAL);
        this.alert.setTitle(title);
        this.alert.setHeaderText(header);
        this.alert.setContentText(content);
        this.alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
    }

    public boolean showAndWait() {
        Optional<ButtonType> result = alert.showAndWait();
        return result.filter(buttonType -> buttonType == ButtonType.OK).isPresent();
    }
}
