package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "hamwil")
public class Game {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    Player player;

    @Temporal(TemporalType.DATE)
    Date start;
    @Temporal(TemporalType.DATE)
    Date end;

    //liste der Kategorien, Liste der Fragen und ausgewählten Antworten
    @OneToMany(mappedBy = "game")
    private List<Game_Question> game_questions;
}
