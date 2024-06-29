package technikum.at.tourplanner_swen2_team5;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import technikum.at.tourplanner_swen2_team5.util.ScreenManager;

import java.io.File;

public class MainTourPlaner extends Application {
    private static Stage stg;
    private static HostServices hostServices;
    private ConfigurableApplicationContext springContext;

    @Override
    public void init() throws Exception {
        springContext = new SpringApplicationBuilder(TourPlannerSpringBootApplication.class).run();
        hostServices = getHostServices();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stg = stage;
        ScreenManager screenManager = new ScreenManager(stage, springContext);
        screenManager.loadHomeScreen();

        stage.setTitle("Tours By Helena");
        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
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
