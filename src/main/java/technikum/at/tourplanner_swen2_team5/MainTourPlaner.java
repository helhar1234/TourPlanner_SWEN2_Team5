package technikum.at.tourplanner_swen2_team5;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.controller.HomeScreenController;
import technikum.at.tourplanner_swen2_team5.controller.NavbarController;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;

import java.io.IOException;
import java.util.Objects;

public class MainTourPlaner extends Application {

    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("home_screen.fxml"));
        Parent homeRoot = homeLoader.load();
        HomeScreenController homeController = homeLoader.getController();

        ApplicationContext.setHomeScreenController(homeController);

        // Übergabe des HomeController an den NavbarController
        FXMLLoader navbarLoader = new FXMLLoader(getClass().getResource("navbar.fxml"));
        Parent navbar = navbarLoader.load();
        NavbarController navbarController = navbarLoader.getController();
        navbarController.setHomeScreenController(homeController);

        // Zusammenbau des UIs
        GridPane gridPane = (GridPane) homeRoot;
        gridPane.add(navbar, 0, 0);

        Scene scene = new Scene(homeRoot);
        stage.setScene(scene);
        stage.setTitle("Tours By Helena");
        stage.show();
        stage.setMinWidth(280);
        stage.setMinHeight(180);
    }




    public void changeScene(String fxml) throws IOException {
        stg.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml))));
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stg;
    }
}
