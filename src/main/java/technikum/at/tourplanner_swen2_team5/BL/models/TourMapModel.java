package technikum.at.tourplanner_swen2_team5.BL.models;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "maps")
public class TourMapModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tourid_fk", nullable = false)
    private String tourId;

    @Column(name = "filename", nullable = false)
    private String filename;

    public TourMapModel() {
    }

    public TourMapModel(String tourId, String filename) {
        this.tourId = tourId;
        this.filename = filename;
    }
}
