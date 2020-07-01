package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.api.Lab04MassData;
import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.GameQuestion;
import de.hda.fbi.db2.stud.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


public class Lab04MassDataImp extends Lab04MassData {

  @Override
  public void createMassData() {

    var before = System.currentTimeMillis();

    EntityManager emm = this.lab02EntityManager.getEntityManager();
    EntityTransaction tr = emm.getTransaction();

    HashMap<String, Player> hashPlayers = new HashMap<String, Player>();
    List<Player> players = emm.createQuery("SELECT p FROM Player p").getResultList();

    for (Iterator i = players.iterator(); i.hasNext();) {
      Player cm = (Player) i.next();
      hashPlayers.put(cm.getName(), cm);
    }


    for (int playerloop = 0; playerloop < 10000; playerloop++) {

      tr.begin();

      Player player = null;
      if (hashPlayers.containsKey(Integer.toString(playerloop))) {
        player = hashPlayers.get(Integer.toString(playerloop));
      } else {

        Player p = new Player(Integer.toString(playerloop));
        hashPlayers.put(p.getName(), p);
        emm.persist(p);
      }

      for (int gameloop = 0; gameloop < 100; gameloop++) {

        List<Object> categories = new ArrayList<>();

        Random ran = new Random();
        for (int catloop = 0; catloop <= (4 + ran.nextInt(2)); catloop++) {            //5-7
          categories.add(lab01Data.getCategories().get(
                  ran.nextInt(lab01Data.getCategories().size())));
        }

        List<?> questions = lab03Game.getQuestions(categories, (2 + ran.nextInt(2)));
        Object game = lab03Game.createGame(player, questions);
        lab03Game.playGame(game);


        List<GameQuestion> gaQue;

        if (!((Game) game).getGameQuestions().isEmpty()
                && ((Game) game).getGameQuestions().get(0) instanceof GameQuestion) {

          gaQue = (List<GameQuestion>) ((Game) game).getGameQuestions();
        } else {
          throw new IllegalArgumentException("Liste ist leer oder enthält falschen typen");
        }


        for (GameQuestion elem: gaQue) {
          emm.persist(elem);
        }

        if (game instanceof Game) {
          emm.persist(game);
        } else {
          throw new IllegalArgumentException("Game is null or is no instanceof Game");
        }

      }
      tr.commit();
      emm.clear();
    }
    var after = System.currentTimeMillis();
    var elaTime = (after-before)/1000;
    System.out.println("Time to persist: " + (int) (elaTime/60) + "min " + elaTime%60 + "s");
    emm.close();
  }
}
