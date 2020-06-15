package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lab03GameImp extends Lab03Game {

  @Override
  public Object getOrCreatePlayer(String playerName) {

    Player player = new Player(playerName);

    return player;
  }

  @Override
  public Object interactiveGetOrCreatePlayer() {

    Scanner input = new Scanner(System.in);
    System.out.println("Hier können Sie den Namen auswählen : ");
    String playerName = input.nextLine();
    input.close();

    Player player = new Player(playerName);

    return player;
  }

  @Override
  public List<?> getQuestions(List<?> categories, int amountOfQuestionsForCategory) {
    return null;
  }

  @Override
  public List<?> interactiveGetQuestions() {

    Scanner input = new Scanner(System.in);
    List<Category> allCategorys = (List<Category>) lab01Data.getCategories();

    for (Category elem: allCategorys) {
      System.out.println( elem.get_name() );
    }

    System.out.println("Wählen Sie mindestens 2 Kategorien aus geben sie diese durch Komma's getrennt an: ");
    String Questions = input.nextLine();
    String[] splitStrings = Questions.split(",");
    List<Category> parsedCats = new ArrayList<Category>();

    for (String elem: splitStrings
         ) {
     parsedCats.add(allCategorys.get(Integer.parseInt(elem)));
    }

    System.out.println("Wie viele Fragen möchten sie pro Category haben? :");
    String inputAmoutofQuestions = input.nextLine();
    int AmountOfQuestions = Integer.parseInt(inputAmoutofQuestions);

    input.close();

    return this.getQuestions(parsedCats, AmountOfQuestions);

  }

  @Override
  public Object createGame(Object player, List<?> questions) {
    return null;
  }

  @Override
  public void playGame(Object game) {

  }

  @Override
  public void interactivePlayGame(Object game) {

  }

  @Override
  public void persistGame(Object game) {

  }
}
