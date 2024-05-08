package technikum.at.tourplanner_swen2_team5.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

public class TourModel {

    @Getter
    private String id;
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty start = new SimpleStringProperty();
    private final StringProperty destination = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();

    // Standardkonstruktor
    public TourModel() {}

    // Konstruktor mit allen Parametern
    public TourModel(String name, String description, String start, String destination, String transportType) {
        this.name.set(name);
        this.description.set(description);
        this.start.set(start);
        this.destination.set(destination);
        this.transportType.set(transportType);
    }

    // Getter und Setter
    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty startProperty() { return start; }
    public StringProperty destinationProperty() { return destination; }
    public StringProperty transportTypeProperty() { return transportType; }


    public void setId(String id) {
        this.id = id;
    };
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

}
