module technikum.at.tourplanner_swen2_team5 {
    // JavaFX Modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;

    // Java Modules
    requires java.desktop;
    requires java.sql;
    requires java.naming;
    requires jdk.compiler;

    // Logging Modules
    requires org.slf4j;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j.slf4j2.impl;

    // Hibernate Modules
    requires org.hibernate.orm.core;
    requires org.hibernate.search.mapper.orm;
    requires org.hibernate.search.mapper.pojo;
    requires org.hibernate.search.backend.lucene;
    requires org.hibernate.search.engine;

    // Spring Boot Modules
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.aop;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.data.jpa;

    // Other Modules
    requires lombok;
    requires okhttp3;
    requires org.json;
    requires kernel;
    requires layout;
    requires io;

    requires com.fasterxml.jackson.databind;

    requires spring.tx;
    requires jakarta.persistence;
    requires jakarta.cdi;
    requires spring.orm;


    // Exports
    exports technikum.at.tourplanner_swen2_team5;
    exports technikum.at.tourplanner_swen2_team5.util;
    exports technikum.at.tourplanner_swen2_team5.View.viewmodels;
    exports technikum.at.tourplanner_swen2_team5.BL.models;
    exports technikum.at.tourplanner_swen2_team5.BL.services;
    exports technikum.at.tourplanner_swen2_team5.BL.validation;
    exports technikum.at.tourplanner_swen2_team5.DAL.repositories;
    exports technikum.at.tourplanner_swen2_team5.View.controllers;

    // Opens for reflection
    opens technikum.at.tourplanner_swen2_team5 to javafx.base, javafx.fxml, javafx.graphics, org.hibernate.orm.core, spring.core;
    opens technikum.at.tourplanner_swen2_team5.util to javafx.base, javafx.fxml, javafx.graphics, org.hibernate.orm.core, spring.core;
    opens technikum.at.tourplanner_swen2_team5.View.viewmodels to javafx.base, javafx.fxml, javafx.graphics, org.hibernate.orm.core, spring.core;
    opens technikum.at.tourplanner_swen2_team5.View.controllers to javafx.base, javafx.fxml, javafx.graphics, org.hibernate.orm.core, spring.core;
    opens technikum.at.tourplanner_swen2_team5.BL.models to org.hibernate.orm.core, org.hibernate.search.mapper.orm, javafx.base, javafx.fxml, javafx.graphics, spring.core;
    opens technikum.at.tourplanner_swen2_team5.BL.services to javafx.base, javafx.fxml, javafx.graphics, org.hibernate.orm.core, spring.core;
    opens technikum.at.tourplanner_swen2_team5.BL.validation to javafx.base, javafx.fxml, javafx.graphics, org.hibernate.orm.core, spring.core;
    opens technikum.at.tourplanner_swen2_team5.DAL.repositories to org.hibernate.orm.core, javafx.base, javafx.fxml, javafx.graphics, spring.core;
    opens technikum.at.tourplanner_swen2_team5.config to spring.core, spring.beans, spring.context, spring.boot, spring.boot.autoconfigure, spring.data.jpa;
}