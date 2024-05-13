module technikum.at.tourplanner_swen2_team5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires lombok;

    opens technikum.at.tourplanner_swen2_team5 to javafx.fxml, javafx.graphics;
    exports technikum.at.tourplanner_swen2_team5;
    exports technikum.at.tourplanner_swen2_team5.controller;
    opens technikum.at.tourplanner_swen2_team5.controller to javafx.fxml, javafx.graphics;

    opens technikum.at.tourplanner_swen2_team5.models to javafx.base, javafx.fxml;
}
