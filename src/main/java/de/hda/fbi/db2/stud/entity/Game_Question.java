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

    public Game_Question(){};

    public Game_Question(Game game, Question question){
        this.game = game;
        this.question = question;
        given_answer=false;
    }


    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isGiven_answer() {
        return given_answer;
    }

    public void setGiven_answer(boolean given_answer) {
        this.given_answer = given_answer;
    }

}
