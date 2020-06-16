package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.stud.entity.*;

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

    Player player = new Player(playerName);

    return player;
  }

  @Override
  public List<?> getQuestions(List<?> categories, int amountOfQuestionsForCategory) {
  List<Question> gameQuestions = new ArrayList<Question>();
    for (Category elem: (List<Category>)categories) {
      System.out.println(elem.get_name());

      if(elem.get_questions().size() <= amountOfQuestionsForCategory){
        gameQuestions.addAll(elem.get_questions());
      }
      else{
        List<Integer> randomNums = new ArrayList<>();
        for(int i=0 ; i < amountOfQuestionsForCategory; i++){
          int newRandom = (int) (Math.random() * elem.get_questions().size());
          while(randomNums.contains(newRandom)) {
            newRandom = (int) (Math.random() * elem.get_questions().size());        // make sure there are no numbers that are already chosen
          }

          randomNums.add(newRandom);

          gameQuestions.add(elem.get_question(randomNums.get(i)));
        }
      }

    }
    return gameQuestions;
  }

  @Override
  public List<?> interactiveGetQuestions() {

    Scanner input = new Scanner(System.in);
    List<Category> allCategorys = (List<Category>) lab01Data.getCategories();

    int counter = 0;
    for (Category elem: allCategorys) {
      System.out.println(counter + ": " + elem.get_name() );
      counter++;
    }

    System.out.println("Wählen Sie mindestens 2 Kategorien aus geben sie diese durch Komma's getrennt an: ");
    String Questions = input.nextLine();
    String[] splitStrings = Questions.split(",");
    List<Category> parsedCats = new ArrayList<Category>();

    for (String elem: splitStrings
         ) {
      elem = elem.replaceAll("[^0-9]", "");
     parsedCats.add(allCategorys.get(Integer.parseInt(elem)));
    }

    System.out.println("Wie viele Fragen möchten sie pro Category haben? :");
    String inputAmoutofQuestions = input.nextLine();
    int AmountOfQuestions = Integer.parseInt(inputAmoutofQuestions);


    return this.getQuestions(parsedCats, AmountOfQuestions);

  }

  @Override
  public Object createGame(Object player, List<?> questions) {
    Game g = new Game(player);
    g.setGame_questions(questions);
    return g;
  }

  @Override
  public void playGame(Object game) {

  }

  @Override
  public void interactivePlayGame(Object game) {
    Game cGame = (Game) game;
    for (Game_Question elem: cGame.getGame_questions()
         ) {
      System.out.println("\n\n\nQ: " + elem.getQuestion().get_question() + "\n");
      int co=0;
      for (String answerElem: elem.getQuestion().get_answer_list()
           ) {
        System.out.println("A" + co + ": " + answerElem);
      }

    }
  }

  @Override
  public void persistGame(Object game) {

  }
}
