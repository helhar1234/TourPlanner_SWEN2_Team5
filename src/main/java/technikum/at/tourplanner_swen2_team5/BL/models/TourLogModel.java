package technikum.at.tourplanner_swen2_team5.BL.models;

import lombok.Data;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.ValueBridgeRef;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import jakarta.persistence.*;
import technikum.at.tourplanner_swen2_team5.search.bridge.DifficultyModelValueBridge;

@Data
@Entity
@Indexed // Hinzufügen, um die Klasse für die Volltextsuche zu indizieren
@Table(name = "tourlogs")
public class TourLogModel {
    @Id
    @Column(name = "tourlogid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "tourid_fk", referencedColumnName = "tourid", nullable = false)
    private TourModel tour;

    @FullTextField // Hinzufügen zur Indizierung für die Suche
    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "timeHours")
    private int timeHours;

    @Column(name = "timeMinutes")
    private int timeMinutes;

    @FullTextField // Hinzufügen zur Indizierung für die Suche
    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "difficultyid", referencedColumnName = "difficultyid")
    @FullTextField(valueBridge = @ValueBridgeRef(type = DifficultyModelValueBridge.class))
    private DifficultyModel difficulty;

    @Column(name = "distance")
    private float distance;

    @Column(name = "totaltime")
    private String totalTime;

    @Column(name = "rating")
    private int rating;

    @ManyToOne
    @JoinColumn(name = "transporttypeid_fk", referencedColumnName = "transporttypeid")
    private TransportTypeModel transportType;

    // Default constructor
    public TourLogModel() {
    }

    // Constructor with all parameters
    public TourLogModel(String id, TourModel tour, String date, int timeHours, int timeMinutes, String comment, DifficultyModel difficulty, float distance, String totalTime, int rating, TransportTypeModel transportType) {
        this.id = id;
        this.tour = tour;
        this.date = date;
        this.timeHours = timeHours;
        this.timeMinutes = timeMinutes;
        this.comment = comment;
        this.difficulty = difficulty;
        this.distance = distance;
        this.totalTime = totalTime;
        this.rating = rating;
        this.transportType = transportType;
    }

    public double getTimeInHours() {
        return timeHours + timeMinutes / 60.0;
    }
}