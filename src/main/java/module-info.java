module technikum.at.tourplanner_swen2_team5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;


    opens technikum.at.tourplanner_swen2_team5 to javafx.fxml;
    exports technikum.at.tourplanner_swen2_team5;
    exports technikum.at.tourplanner_swen2_team5.controller;
    opens technikum.at.tourplanner_swen2_team5.controller to javafx.fxml;
}