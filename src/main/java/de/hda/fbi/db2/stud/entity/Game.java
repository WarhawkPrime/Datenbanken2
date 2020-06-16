package de.hda.fbi.db2.stud.entity;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public Game(){};

    public Game(Object player){
        this.player=(Player) player;
        game_questions = new ArrayList<Game_Question>();
    }

    public List<Game_Question> getGame_questions() {
        return game_questions;
    }

    public void setGame_questions(List<?> questions) {
        for (Question elem: (List<Question>) questions
        ) {
            Game_Question g = new Game_Question(this, elem);
            game_questions.add(g);
        }
    }
}
