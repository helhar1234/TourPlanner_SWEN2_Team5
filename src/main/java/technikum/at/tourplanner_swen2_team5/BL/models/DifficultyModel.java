package technikum.at.tourplanner_swen2_team5.BL.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@Data
@Entity
@Indexed
@Table(name = "difficulties")
public class DifficultyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "difficultyid")
    private int id;

    @FullTextField
    @Column(name = "difficulty", nullable = false)
    private String difficulty;

    public DifficultyModel() {
    }

    public DifficultyModel(String difficulty) {
        this.difficulty = difficulty;
    }
}
