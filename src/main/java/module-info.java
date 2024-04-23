module technikum.at.tourplanner_swen2_team5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens technikum.at.tourplanner_swen2_team5 to javafx.fxml;
    exports technikum.at.tourplanner_swen2_team5;
}