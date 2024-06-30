package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import technikum.at.tourplanner_swen2_team5.MainTourPlanner;

import java.net.URL;

@Slf4j
@Controller
public class HomeScreenController {

    @FXML
    private VBox mainContentArea;

    @FXML
    private StackPane stackpane;

    @Value("${logo.image.path}")
    private String logoImagePath;

    public void changeMainContent(Node content) {
        if (mainContentArea != null) {
            mainContentArea.getChildren().clear();
            mainContentArea.getChildren().add(content);
        }
    }

    public void gotoHomeScreen() {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        String filename = logoImagePath;
        String imageName = "img/logos/" + filename;
        URL resource = MainTourPlanner.class.getResource(imageName);

        if (resource != null) {
            Image image = new Image(resource.toString());
            imageView.setImage(image);
        } else {
            log.error("Image not found: {}", imageName);
        }

        Label label = new Label("TOURS BY HELENA");
        label.getStyleClass().add("title-label");

        VBox content = new VBox(20);
        content.setAlignment(javafx.geometry.Pos.CENTER);
        content.getChildren().addAll(imageView, label);

        changeMainContent(content);
    }

    public StackPane getContentPane() {
        return stackpane;
    }
}
