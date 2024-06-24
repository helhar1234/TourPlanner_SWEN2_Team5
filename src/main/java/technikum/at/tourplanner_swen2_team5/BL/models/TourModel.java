package technikum.at.tourplanner_swen2_team5.BL.models;

import javax.persistence.*;

@Entity
@Table(name = "tours")
public class TourModel {
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
    }

    // Getters and Setters
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
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public TransportTypeModel getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportTypeModel transportType) {
        this.transportType = transportType;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}

