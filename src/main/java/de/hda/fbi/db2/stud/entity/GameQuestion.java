package de.hda.fbi.db2.stud.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "hamwil")
public class GameQuestion {

  @Id
  @GeneratedValue
  private int id;

  @ManyToOne
  Question question;

  @ManyToOne
  Game game;

  //boolean givenAnswer;
  int givenAnswer;

  public GameQuestion() {}


  /**
   * constructor for GameQuestion.
   * @param game is the game to set
   * @param question is the question to set
   */
  public GameQuestion(Game game, Question question) {
    this.game = game;
    this.question = question;
    givenAnswer = 0;
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

  public int isGiven_answer() {
    return givenAnswer;
  }

  public void setGiven_answer(int givenAnswer) {
    this.givenAnswer = givenAnswer;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    GameQuestion gameQuestion = (GameQuestion) o;
    return id == gameQuestion.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
