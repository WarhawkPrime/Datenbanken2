package de.hda.fbi.db2.stud.entity;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "hamwil")
public class Game_Question {

    @ManyToOne
    Question question;

    @ManyToOne
    Game game;

    boolean given_answer;

}
