package technikum.at.tourplanner_swen2_team5;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.Objects;

public class MainTourPlaner extends Application {

    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainTourPlaner.class.getResource("home_screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Tours By Helena");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
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
