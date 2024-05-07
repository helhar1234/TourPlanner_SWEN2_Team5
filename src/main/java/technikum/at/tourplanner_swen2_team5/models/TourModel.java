package technikum.at.tourplanner_swen2_team5.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourModel {
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
    public StringProperty fromProperty() { return start; }
    public StringProperty toProperty() { return destination; }
    public StringProperty transportTypeProperty() { return transportType; }

    public String getName() {
        return name.get();
    }

    public String getDescription() {
        return description.get();
    }

    public String getStart() {
        return start.get();
    }

    public String getDestination() {
        return destination.get();
    }

    public String getTransportationType() {
        return transportType.get();
    }


}
