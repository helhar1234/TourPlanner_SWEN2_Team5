package technikum.at.tourplanner_swen2_team5;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import technikum.at.tourplanner_swen2_team5.controller.HomeScreenController;
import technikum.at.tourplanner_swen2_team5.controller.NavbarController;

import java.io.IOException;
import java.util.Objects;

public class MainTourPlaner extends Application {

    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader homeLoader = new FXMLLoader(MainTourPlaner.class.getResource("home_screen.fxml"));
        Parent homeRoot = homeLoader.load(); // Laden des Home Screen Root-Elements
        HomeScreenController homeController = homeLoader.getController(); // Zugriff auf den HomeScreenController

        FXMLLoader navbarLoader = new FXMLLoader(MainTourPlaner.class.getResource("navbar.fxml"));
        Parent navbar = navbarLoader.load(); // Laden der Navbar
        NavbarController navbarController = navbarLoader.getController(); // Zugriff auf den NavbarController
        navbarController.setHomeScreenController(homeController); // Setzen der Referenz zum HomeScreenController

        BorderPane borderPane = (BorderPane) homeRoot; // Annahme, dass homeRoot ein BorderPane ist
        borderPane.setTop(navbar); // Setzen der Navbar im BorderPane oben

        Scene scene = new Scene(homeRoot);
        stage.setScene(scene);
        stage.setTitle("Tours By Helena");
        stage.show();
    }


    public void changeScene(String fxml) throws IOException{
        stg.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml))));
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stg;
    }
}
