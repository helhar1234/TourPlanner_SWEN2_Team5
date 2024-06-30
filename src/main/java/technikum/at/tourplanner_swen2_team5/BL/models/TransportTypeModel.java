package technikum.at.tourplanner_swen2_team5.BL.models;

import lombok.Data;
import lombok.Getter;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import jakarta.persistence.*;

@Data
@Entity
@Indexed
@Table(name = "transporttypes")
public class TransportTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transporttypeid")
    private int id;

    @Getter
    @FullTextField
    @Column(name = "name", nullable = false)
    private String name;

    // Standardkonstruktor
    public TransportTypeModel() {
    }

    // Konstruktor mit allen Parametern
    public TransportTypeModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Konstruktor ohne ID, da ID automatisch generiert wird
    public TransportTypeModel(String name) {
        this.name = name;
    }
}
