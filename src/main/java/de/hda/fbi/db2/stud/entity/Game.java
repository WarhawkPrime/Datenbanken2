package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(schema = "hamwil")
public class Game {



  @Id
  @GeneratedValue
  private int id;

  @ManyToOne
  Player player;

  @Temporal(TemporalType.TIMESTAMP)
  Date starttime;

  @Temporal(TemporalType.TIMESTAMP)
  Date endtime;

  @OneToMany(mappedBy = "game")
  private List<GameQuestion> gameQuestions;

  public Game() {}

  public Game(Object player) {
    this.player = (Player) player;
    gameQuestions = new ArrayList<GameQuestion>();
  }

  public List<GameQuestion> getGameQuestions() {
    return gameQuestions;
  }

  /**
   * setzt die game_questions.
   * @param questions Liste von questions
   */
  public void setGame_questions(List<?> questions) {
    for (Question elem: (List<Question>) questions) {
      GameQuestion g = new GameQuestion(this, elem);
      gameQuestions.add(g);
    }
  }

  public void setStarttime(Date starttime) {
    this.starttime = (Date) starttime.clone();
  }

  public void setEndtime(Date endtime) {
    this.endtime = (Date) endtime.clone();
  }

  public Date getStarttime() {return starttime;}

  public Date getEndtime() {return endtime;}

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Game game = (Game) o;
    return id == game.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public Player getPlayer() {
    return player;
  }


}
