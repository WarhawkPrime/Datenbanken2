package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.api.Lab04MassData;
import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.GameQuestion;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lab04MassDataImp extends Lab04MassData {

  @Override
  public void createMassData() {

    EntityManager emm = this.lab02EntityManager.getEntityManager();
    EntityTransaction tr = emm.getTransaction();


    for(int playerloop=0 ; playerloop<10000 ; playerloop++) {


      //player persist and commit
      Object player = lab03Game.getOrCreatePlayer(String.valueOf(playerloop));

      //emm.persist(player);
      //tr.commit();

      //emm.persist(player);
      //emm.getTransaction().commit();

      //emm.clear();


      tr.begin();

      for(int gameloop=0 ; gameloop<100 ; gameloop++) {


        List<Object> categories = new ArrayList<>();

        Random ran = new Random();
        for(int catloop = 0 ; catloop <= ( 2 + ran.nextInt(2 )) ; catloop++)
          categories.add(lab01Data.getCategories().get( ran.nextInt( lab01Data.getCategories().size() )));

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


    emm.close();
  }




}
