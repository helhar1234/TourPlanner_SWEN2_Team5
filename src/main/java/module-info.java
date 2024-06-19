module technikum.at.tourplanner_swen2_team5 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires lombok;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;
    requires jdk.compiler;
    requires javafx.web;
    requires okhttp3;
    requires org.json;
    requires kernel;
    requires layout;
    requires org.apache.logging.log4j;

    exports technikum.at.tourplanner_swen2_team5;
    exports technikum.at.tourplanner_swen2_team5.util;
    exports technikum.at.tourplanner_swen2_team5.View.viewmodels;
    exports technikum.at.tourplanner_swen2_team5.BL.models;
    exports technikum.at.tourplanner_swen2_team5.BL.services;
    exports technikum.at.tourplanner_swen2_team5.BL.validation;
    exports technikum.at.tourplanner_swen2_team5.DAL.repositories;
    exports technikum.at.tourplanner_swen2_team5.DAL;

    opens technikum.at.tourplanner_swen2_team5.View.viewmodels to javafx.base, javafx.fxml, javafx.graphics;
    opens technikum.at.tourplanner_swen2_team5.BL.models to org.hibernate.orm.core, javafx.base, javafx.fxml, javafx.graphics;
    opens technikum.at.tourplanner_swen2_team5.DAL.repositories to org.hibernate.orm.core, javafx.base, javafx.fxml, javafx.graphics;
    opens technikum.at.tourplanner_swen2_team5.DAL to org.hibernate.orm.core, javafx.base, javafx.fxml, javafx.graphics;
    exports technikum.at.tourplanner_swen2_team5.View.controllers;
    opens technikum.at.tourplanner_swen2_team5 to javafx.base, javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    opens technikum.at.tourplanner_swen2_team5.BL.services to javafx.base, javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    opens technikum.at.tourplanner_swen2_team5.BL.validation to javafx.base, javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    opens technikum.at.tourplanner_swen2_team5.View.controllers to javafx.base, javafx.fxml, javafx.graphics, org.hibernate.orm.core;
    opens technikum.at.tourplanner_swen2_team5.util to javafx.base, javafx.fxml, javafx.graphics, org.hibernate.orm.core;
}
