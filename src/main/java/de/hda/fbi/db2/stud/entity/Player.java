package de.hda.fbi.db2.stud.entity;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "hamwil")
public class Player {

    @Id
    private String name;

    @OneToMany(mappedBy = "player")
    private List<Game> games;

    public Player() {}

    public Player(String given_name) {
        this.name = given_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
