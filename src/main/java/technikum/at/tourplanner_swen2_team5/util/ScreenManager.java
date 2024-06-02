package technikum.at.tourplanner_swen2_team5.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.View.controllers.HomeScreenController;
import technikum.at.tourplanner_swen2_team5.View.controllers.NavbarController;

public class ScreenManager {
    private Stage stage;

    public ScreenManager(Stage stage) {
        this.stage = stage;
    }

    public void loadHomeScreen() throws Exception {
        // Laden des Home Screens
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource(ApplicationContext.FXML_BASE_PATH + "home_screen.fxml"));
        Parent homeRoot = homeLoader.load();
        HomeScreenController homeController = homeLoader.getController();
        ApplicationContext.setHomeScreenController(homeController);

        // Laden der Navbar
        FXMLLoader navbarLoader = new FXMLLoader(getClass().getResource(ApplicationContext.FXML_BASE_PATH + "navbar.fxml"));
        Parent navbar = navbarLoader.load();
        NavbarController navbarController = navbarLoader.getController();
        navbarController.setHomeScreenController(homeController);

        // Anfügen der Navbar am oberen Rand des Home Screens
        GridPane gridPane = (GridPane) homeRoot;
        gridPane.add(navbar, 0, 0, 1, 1); // Stellen Sie sicher, dass die Navbar auf gesamte Breite gestreckt wird
        GridPane.setHgrow(navbar, Priority.ALWAYS); // Stellen Sie sicher, dass die Navbar horizontal wächst

        Scene scene = new Scene(homeRoot);
        stage.setScene(scene);
    }
}



