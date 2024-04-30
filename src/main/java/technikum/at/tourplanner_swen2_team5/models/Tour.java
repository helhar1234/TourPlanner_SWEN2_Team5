package technikum.at.tourplanner_swen2_team5.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tour {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty from = new SimpleStringProperty();
    private final StringProperty to = new SimpleStringProperty();
    private final StringProperty transportType = new SimpleStringProperty();

    // Standardkonstruktor
    public Tour() {}

    // Konstruktor mit allen Parametern
    public Tour(String name, String description, String from, String to, String transportType) {
        this.name.set(name);
        this.description.set(description);
        this.from.set(from);
        this.to.set(to);
        this.transportType.set(transportType);
    }

    // Getter und Setter
    // Methode `getProperty` für alle Felder zur Nutzung im Binding
    public StringProperty nameProperty() { return name; }
    public StringProperty descriptionProperty() { return description; }
    public StringProperty fromProperty() { return from; }
    public StringProperty toProperty() { return to; }
    public StringProperty transportTypeProperty() { return transportType; }
}
