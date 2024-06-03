package technikum.at.tourplanner_swen2_team5.View.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.MainTourPlaner;

import java.net.URL;

public class HomeScreenController {
    @FXML
    private VBox mainContentArea;

    public void changeMainContent(Node content) {
        if (mainContentArea != null) {
            mainContentArea.getChildren().clear();
            mainContentArea.getChildren().add(content);
        }
    }

    public void gotoHomeScreen() {
        // Erzeuge die ImageView
        ImageView imageView = new ImageView();
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        String filename = "BikerLogoMave.png";
        String imageName = "img/logos/" + filename;
        URL resource = MainTourPlaner.class.getResource(imageName);

        Image image = new Image(resource.toString());
        imageView.setImage(image);

        // Erzeuge das Label
        Label label = new Label("TOURS BY HELENA");
        label.getStyleClass().add("title-label");

        // Erzeuge die VBox und füge ImageView und Label hinzu
        VBox content = new VBox(20);
        content.setAlignment(javafx.geometry.Pos.CENTER);
        content.getChildren().addAll(imageView, label);

        // Setze den neuen Inhalt im Hauptinhaltbereich
        changeMainContent(content);
    }
}
