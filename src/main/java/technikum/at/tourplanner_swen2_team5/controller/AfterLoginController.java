package technikum.at.tourplanner_swen2_team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;

import java.io.IOException;

public class AfterLoginController {

    MainTourPlaner m = new MainTourPlaner();

    @FXML
    private Button logOutButton;

    @FXML
    private Button closeButton;

    @FXML
    void logOut(ActionEvent event) throws IOException {
        m.changeScene("mainWindow.fxml");
    }

    @FXML
    void closeApp(ActionEvent event) throws IOException {
        MainTourPlaner.getStg().close();
    }

}
