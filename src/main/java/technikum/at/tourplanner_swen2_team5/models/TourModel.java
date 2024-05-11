package technikum.at.tourplanner_swen2_team5.models;

import javafx.beans.property.*;
import lombok.Getter;

public class TourModel {
    @Getter
    private String id;
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty start = new SimpleStringProperty();
    private final StringProperty destination = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final IntegerProperty time = new SimpleIntegerProperty();

    // Standardkonstruktor
    public TourModel() {
    }

    // Konstruktor mit allen Parametern
    public TourModel(String name, String description, String start, String destination, String transportType, double distance, int time) {
        this.name.set(name);
        this.description.set(description);
        this.start.set(start);
        this.destination.set(destination);
        this.transportType.set(transportType);
        this.distance.set(distance);
        this.time.set(time);
    }

    // Getter und Setter
    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty startProperty() {
        return start;
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    public DoubleProperty distanceProperty() {
        return distance;
    }

    public IntegerProperty timeProperty() {
        return time;
    }

    // Getter & Setter
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public final void setName(String name) {
        nameProperty().set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public final void setDescription(String description) {
        descriptionProperty().set(description);
    }

    public String getStart() {
        return start.get();
    }

    public final void setStart(String start) {
        startProperty().set(start);
    }

    public String getDestination() {
        return destination.get();
    }

    public final void setDestination(String destination) {
        destinationProperty().set(destination);
    }

    public String getTransportationType() {
        return transportType.get();
    }

    public final void setTransportType(String transportType) {
        transportTypeProperty().set(transportType);
    }

    public double getDistance() {
        return distance.get();
    }

    public void setDistance(double distance) {
        this.distance.set(distance);
    }

    public int getTime() {
        return time.get();
    }

    public void setTime(int time) {
        this.time.set(time);
    }
}

