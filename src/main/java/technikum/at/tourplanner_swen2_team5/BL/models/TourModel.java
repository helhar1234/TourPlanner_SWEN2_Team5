package technikum.at.tourplanner_swen2_team5.BL.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tours")
public class TourModel {
    // Getters and Setters
    @Setter
    @Getter
    @Id
    @Column(name = "tourid", updatable = false, nullable = false)
    private String id;

    @Setter
    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @Setter
    @Getter
    @Column(name = "description")
    private String description;

    @Setter
    @Getter
    @Column(name = "start", nullable = false)
    private String start;

    @Setter
    @Getter
    @Column(name = "destination", nullable = false)
    private String destination;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "transporttypeid_fk", referencedColumnName = "transporttypeid", nullable = false)
    private TransportTypeModel transportType;

    @Setter
    @Getter
    @Column(name = "distance", nullable = false)
    private double distance;

    @Setter
    @Getter
    @Column(name = "time", nullable = false)
    private int time;

    @Setter
    @Getter
    @Transient
    private int popularity;

    @Setter
    @Getter
    @Transient
    private float childFriendliness;

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
}

