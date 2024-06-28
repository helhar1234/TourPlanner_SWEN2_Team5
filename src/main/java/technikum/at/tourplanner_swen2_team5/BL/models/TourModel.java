package technikum.at.tourplanner_swen2_team5.BL.models;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tours")
public class TourModel {
    // JPA attributes
    @Id
    @Column(name = "tourid", updatable = false, nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start", nullable = false)
    private String start;

    @Column(name = "destination", nullable = false)
    private String destination;

    @ManyToOne
    @JoinColumn(name = "transporttypeid_fk", referencedColumnName = "transporttypeid", nullable = false)
    private TransportTypeModel transportType;

    @Column(name = "distance", nullable = false)
    private double distance;

    @Column(name = "time", nullable = false)
    private int time;

    @Transient
    private int popularity;

    @Transient
    private float childFriendliness;

    // JavaFX Properties
    private transient StringProperty nameProperty = new SimpleStringProperty();
    private transient StringProperty descriptionProperty = new SimpleStringProperty();
    private transient StringProperty startProperty = new SimpleStringProperty();
    private transient StringProperty destinationProperty = new SimpleStringProperty();
    private transient ObjectProperty<TransportTypeModel> transportTypeProperty = new SimpleObjectProperty<>();
    private transient DoubleProperty distanceProperty = new SimpleDoubleProperty();
    private transient IntegerProperty timeProperty = new SimpleIntegerProperty();
    private transient IntegerProperty popularityProperty = new SimpleIntegerProperty();
    private transient FloatProperty childFriendlinessProperty = new SimpleFloatProperty();

    // Standard constructor
    public TourModel() {}

    // Constructor with all parameters
    public TourModel(String name, String description, String start, String destination, TransportTypeModel transportType, double distance, int time) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.destination = destination;
        this.transportType = transportType;
        this.distance = distance;
        this.time = time;

        // Initialize properties
        this.nameProperty.set(name);
        this.descriptionProperty.set(description);
        this.startProperty.set(start);
        this.destinationProperty.set(destination);
        this.transportTypeProperty.set(transportType);
        this.distanceProperty.set(distance);
        this.timeProperty.set(time);
    }

    // Getter and Setter for JPA attributes
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.nameProperty.set(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.descriptionProperty.set(description);
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
        this.startProperty.set(start);
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
        this.destinationProperty.set(destination);
    }

    public TransportTypeModel getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportTypeModel transportType) {
        this.transportType = transportType;
        this.transportTypeProperty.set(transportType);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
        this.distanceProperty.set(distance);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
        this.timeProperty.set(time);
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
        this.popularityProperty.set(popularity);
    }

    public float getChildFriendliness() {
        return childFriendliness;
    }

    public void setChildFriendliness(float childFriendliness) {
        this.childFriendliness = childFriendliness;
        this.childFriendlinessProperty.set(childFriendliness);
    }

    // JavaFX Property methods
    public StringProperty nameProperty() {
        return nameProperty;
    }

    public StringProperty descriptionProperty() {
        return descriptionProperty;
    }

    public StringProperty startProperty() {
        return startProperty;
    }

    public StringProperty destinationProperty() {
        return destinationProperty;
    }

    public ObjectProperty<TransportTypeModel> transportTypeProperty() {
        return transportTypeProperty;
    }

    public DoubleProperty distanceProperty() {
        return distanceProperty;
    }

    public IntegerProperty timeProperty() {
        return timeProperty;
    }

    public IntegerProperty popularityProperty() {
        return popularityProperty;
    }

    public FloatProperty childFriendlinessProperty() {
        return childFriendlinessProperty;
    }

    // Synchronize JPA attributes with JavaFX Properties
    public void syncWithProperties() {
        this.name = nameProperty.get();
        this.description = descriptionProperty.get();
        this.start = startProperty.get();
        this.destination = destinationProperty.get();
        this.transportType = transportTypeProperty.get();
        this.distance = distanceProperty.get();
        this.time = timeProperty.get();
    }

    public void initializeProperties() {
        this.nameProperty.set(this.name);
        this.descriptionProperty.set(this.description);
        this.startProperty.set(this.start);
        this.destinationProperty.set(this.destination);
        this.transportTypeProperty.set(this.transportType);
        this.distanceProperty.set(this.distance);
        this.timeProperty.set(this.time);
        this.popularityProperty.set(this.popularity);
        this.childFriendlinessProperty.set(this.childFriendliness);
    }

}
