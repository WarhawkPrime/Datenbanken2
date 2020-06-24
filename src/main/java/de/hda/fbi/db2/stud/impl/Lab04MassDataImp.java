package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.api.Lab04MassData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lab04MassDataImp extends Lab04MassData {

  @Override
  public void createMassData() {



    for(int playerloop=0 ; playerloop<10000 ; playerloop++) {

      Object player = lab03Game.getOrCreatePlayer(String.valueOf(playerloop));

      for(int gameloop=0 ; gameloop<100 ; gameloop++) {

        List<Object> categories = new ArrayList<>();

        Random ran = new Random();
        for(int catloop = 0 ; catloop <= ( 2 + ran.nextInt(2 )) ; catloop++)
          categories.add(lab01Data.getCategories().get( ran.nextInt( lab01Data.getCategories().size() )));

        List<?> questions = lab03Game.getQuestions(categories, (2 + ran.nextInt(2)));
        Object game = lab03Game.createGame(player, questions);
        lab03Game.playGame(game);
        lab03Game.persistGame(game);

      }
    }
  }

}
