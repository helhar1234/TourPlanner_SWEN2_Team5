package technikum.at.tourplanner_swen2_team5.BL.models;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import jakarta.persistence.*;

@Entity
@Indexed
@Table(name = "tours")
public class TourModel {
    // JPA attributes
    @Setter
    @Getter
    @Id
    @Column(name = "tourid", updatable = false, nullable = false)
    private String id;

    @FullTextField
    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @FullTextField
    @Getter
    @Column(name = "description")
    private String description;

    @FullTextField
    @Getter
    @Column(name = "start", nullable = false)
    private String start;

    @FullTextField
    @Getter
    @Column(name = "destination", nullable = false)
    private String destination;

    @Getter
    @ManyToOne
    @JoinColumn(name = "transporttypeid_fk", referencedColumnName = "transporttypeid", nullable = false)
    private TransportTypeModel transportType;

    @Getter
    @Column(name = "distance", nullable = false)
    private double distance;

    @Getter
    @Column(name = "time", nullable = false)
    private int time;

    @Getter
    @Transient
    private int popularity;

    @Getter
    @Transient
    private float childFriendliness;

    // JavaFX Properties
    @Transient
    private transient StringProperty nameProperty = new SimpleStringProperty();
    @Transient
    private transient StringProperty descriptionProperty = new SimpleStringProperty();
    @Transient
    private transient StringProperty startProperty = new SimpleStringProperty();
    @Transient
    private transient StringProperty destinationProperty = new SimpleStringProperty();
    @Transient
    private transient ObjectProperty<TransportTypeModel> transportTypeProperty = new SimpleObjectProperty<>();
    @Transient
    private transient DoubleProperty distanceProperty = new SimpleDoubleProperty();
    @Transient
    private transient IntegerProperty timeProperty = new SimpleIntegerProperty();
    @Transient
    private transient IntegerProperty popularityProperty = new SimpleIntegerProperty();
    @Transient
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
        initializeProperties();
    }

    public void setName(String name) {
        this.name = name;
        this.nameProperty.set(name);
    }

    public void setDescription(String description) {
        this.description = description;
        this.descriptionProperty.set(description);
    }

    public void setStart(String start) {
        this.start = start;
        this.startProperty.set(start);
    }

    public void setDestination(String destination) {
        this.destination = destination;
        this.destinationProperty.set(destination);
    }

    public void setTransportType(TransportTypeModel transportType) {
        this.transportType = transportType;
        this.transportTypeProperty.set(transportType);
    }

    public void setDistance(double distance) {
        this.distance = distance;
        this.distanceProperty.set(distance);
    }

    public void setTime(int time) {
        this.time = time;
        this.timeProperty.set(time);
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
        this.popularityProperty.set(popularity);
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
        this.popularity = popularityProperty.get();
        this.childFriendliness = childFriendlinessProperty.get();
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
