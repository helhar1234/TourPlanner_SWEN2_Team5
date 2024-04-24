package technikum.at.tourplanner_swen2_team5.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;

import java.io.IOException;

public class mainWindowController {
    MainTourPlaner mainTourPlaner = new MainTourPlaner();
    public mainWindowController() {

    }

    @FXML
    private Button logInButton;

    @FXML
    private PasswordField pwd;

    @FXML
    private TextField username;

    @FXML
    private Label warningLabel;

    @FXML
    void userLogIn(ActionEvent event) throws IOException {
        checkLogin();
    }

    private void checkLogin() throws IOException{
        if(username.getText().equals("helena") && pwd.getText().equals("tours")){
            warningLabel.setText("Success!");
            mainTourPlaner.changeScene("afterLogin.fxml");
        }
        else if(username.getText().isEmpty() || pwd.getText().isEmpty()){
            warningLabel.setText("Please enter your data!");
        }
        else{
            warningLabel.setText("Wrong username or password!");
        }
    }

}
