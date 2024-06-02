package technikum.at.tourplanner_swen2_team5.BL.models;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "difficulties")
public class DifficultyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "difficultyid")
    private int id;

    @Column(name = "difficulty", nullable = false)
    private String difficulty;

    // Standardkonstruktor
    public DifficultyModel() {
    }

    // Konstruktor mit allen Parametern
    public DifficultyModel(int id, String difficulty) {
        this.id = id;
        this.difficulty = difficulty;
    }
}
