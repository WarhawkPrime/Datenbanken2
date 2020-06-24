package de.hda.fbi.db2.stud.entity;


import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "hamwil")
public class Player {


  @Id
  private String name;

  @OneToMany(mappedBy = "player")
  private List<Game> games;

  public Player() {}

  public Player(String givenName) {
    this.name = givenName;
  }

  public String getName() {
    return name;
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
