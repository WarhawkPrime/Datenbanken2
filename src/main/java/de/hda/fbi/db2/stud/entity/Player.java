package de.hda.fbi.db2.stud.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "hamwil")
public class Player {

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "player")
    private List<Game> games;

    public Player(String given_name) {
        this.name = given_name;
    }

}
