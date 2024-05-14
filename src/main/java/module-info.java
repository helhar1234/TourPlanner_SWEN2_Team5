module technikum.at.tourplanner_swen2_team5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires lombok;

    exports technikum.at.tourplanner_swen2_team5;
    //opens technikum.at.tourplanner_swen2_team5.controller to javafx.fxml;

    //opens technikum.at.tourplanner_swen2_team5.models to javafx.base, javafx.fxml;
    opens technikum.at.tourplanner_swen2_team5 to javafx.base, javafx.fxml, javafx.graphics;
    exports technikum.at.tourplanner_swen2_team5.util;
    opens technikum.at.tourplanner_swen2_team5.util to javafx.base, javafx.fxml, javafx.graphics;
    exports technikum.at.tourplanner_swen2_team5.controller;
    opens technikum.at.tourplanner_swen2_team5.controller to javafx.base, javafx.fxml, javafx.graphics;
    exports technikum.at.tourplanner_swen2_team5.models;
    opens technikum.at.tourplanner_swen2_team5.models to javafx.base, javafx.fxml, javafx.graphics;
    exports technikum.at.tourplanner_swen2_team5.viewmodels;
    opens technikum.at.tourplanner_swen2_team5.viewmodels to javafx.base, javafx.fxml, javafx.graphics;
}
