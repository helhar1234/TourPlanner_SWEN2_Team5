package technikum.at.tourplanner_swen2_team5;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.util.ApplicationContext;
import technikum.at.tourplanner_swen2_team5.util.ScreenManager;
import technikum.at.tourplanner_swen2_team5.util.ConfigHandler;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.InputStream;
import java.util.Properties;
import java.util.Arrays;


public class MainTourPlaner extends Application {
    private static Stage stg;
    private static HostServices hostServices;

    @Override
    public void start(Stage stage) throws Exception {
        ConfigHandler configHandler = new ConfigHandler();
        // Angenommen, die Konfigurationsdatei liegt im Ressourcenverzeichnis und heißt 'database.properties'
        if (!configHandler.checkConfig("/database.properties", Arrays.asList("DB_URL", "DB_USERNAME", "DB_PASSWORD"))) {
            System.out.println("Konfigurationsfehler: Erforderliche Daten nicht gefunden.");
            return;
        }

        hostServices = getHostServices();
        stg = stage;
        ScreenManager screenManager = new ScreenManager(stage);
        screenManager.loadHomeScreen();

        stage.setTitle("Tours By Helena");
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stg;
    }

    public static void openMapInBrowser() {
        String userDir = System.getProperty("user.home") + "/TourPlanner";
        File leafletHtmlFile = new File(userDir, "leaflet.html");
        hostServices.showDocument(leafletHtmlFile.toURI().toString());
    }
}
