package technikum.at.tourplanner_swen2_team5;


import javafx.application.Application;
import javafx.stage.Stage;
import technikum.at.tourplanner_swen2_team5.util.ScreenManager;

import java.io.IOException;

public class MainTourPlaner extends Application {
    private static Stage stg;

    @Override
    public void start(Stage stage) throws Exception {
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

}

