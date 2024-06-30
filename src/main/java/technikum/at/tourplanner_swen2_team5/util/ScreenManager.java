package technikum.at.tourplanner_swen2_team5.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import technikum.at.tourplanner_swen2_team5.View.controllers.HomeScreenController;
import technikum.at.tourplanner_swen2_team5.View.controllers.NavbarController;

public class ScreenManager {
    private Stage stage;
    private ConfigurableApplicationContext springContext;

    public ScreenManager(Stage stage, ConfigurableApplicationContext springContext) {
        this.stage = stage;
        this.springContext = springContext;
    }

    public void loadHomeScreen() throws Exception {
        // Laden des Home Screens
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource(ApplicationContext.FXML_BASE_PATH + "home_screen.fxml"));
        homeLoader.setControllerFactory(springContext::getBean);
        Parent homeRoot = homeLoader.load();
        HomeScreenController homeController = homeLoader.getController();
        ApplicationContext.setHomeScreenController(homeController);

        // Laden der Navbar
        FXMLLoader navbarLoader = new FXMLLoader(getClass().getResource(ApplicationContext.FXML_BASE_PATH + "navbar.fxml"));
        navbarLoader.setControllerFactory(springContext::getBean);
        Parent navbar = navbarLoader.load();
        NavbarController navbarController = navbarLoader.getController();

        // Anfügen der Navbar am oberen Rand des Home Screens
        GridPane gridPane = (GridPane) homeRoot;
        gridPane.add(navbar, 0, 0, 1, 1);
        GridPane.setHgrow(navbar, Priority.ALWAYS);

        Scene scene = new Scene(homeRoot);
        stage.setScene(scene);
    }
}
