package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.api.Lab04MassData;
import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.GameQuestion;
import de.hda.fbi.db2.stud.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.*;

public class Lab04MassDataImp extends Lab04MassData {

  @Override
  public void createMassData() {

    EntityManager emm = this.lab02EntityManager.getEntityManager();
    EntityTransaction tr = emm.getTransaction();


    for(int playerloop=0 ; playerloop<10000 ; playerloop++) {

        tr.begin();
        Player player = new Player( Integer.toString(playerloop) );
        emm.persist(player);

      for(int gameloop=0 ; gameloop<100 ; gameloop++) {


        List<Object> categories = new ArrayList<>();

        Random ran = new Random();
        for(int catloop = 0 ; catloop <= ( 4 + ran.nextInt(2 )) ; catloop++)            //5-7
          categories.add(lab01Data.getCategories().get( ran.nextInt( lab01Data.getCategories().size() )));

        List<?> questions = lab03Game.getQuestions(categories, (2 + ran.nextInt(2)));
        Object game = lab03Game.createGame(player, questions);
        lab03Game.playGame(game);


        List<GameQuestion> gaQue;

        gaQue = (List<GameQuestion>) ((Game) game).getGameQuestions();

        /* commented for performance
        if (!((Game) game).getGameQuestions().isEmpty()
                && ((Game) game).getGameQuestions().get(0) instanceof GameQuestion) {

          gaQue = (List<GameQuestion>) ((Game) game).getGameQuestions();
        } else {
          throw new IllegalArgumentException("Liste ist leer oder enthält falschen typen");
        }
        */


        for (GameQuestion elem: gaQue) {
          emm.persist(elem);
        }

        emm.persist(game);

        /*commented for performance
        if (game instanceof Game) {
          emm.persist(game);
        } else {
          throw new IllegalArgumentException("Game is null or is no instanceof Game");
        }
         */


      }
      tr.commit();
      emm.clear();
    }
    emm.close();
  }
}
