package de.hda.fbi.db2.stud.entity;


import javax.persistence.*;

@Entity
@Table(schema = "hamwil")
public class Game_Question {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    Question question;

    @ManyToOne
    Game game;

    boolean given_answer;

}
