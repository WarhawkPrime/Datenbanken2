package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.stud.entity.*;

import javax.persistence.EntityManager;
import java.util.*;

public class Lab03GameImp extends Lab03Game {

  @Override
  public Object getOrCreatePlayer(String playerName) {

    Player player = new Player(playerName);

    return player;
  }

  @Override
  public Object interactiveGetOrCreatePlayer() {

    Scanner input = new Scanner(System.in, "UTF-8");
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
        Random ran = new Random();
        for(int i=0 ; i < amountOfQuestionsForCategory; i++){
          int newRandom = ran.nextInt(elem.get_questions().size());
          while(randomNums.contains(newRandom)) {
            newRandom = ran.nextInt(elem.get_questions().size());        // make sure there are no numbers that are already chosen
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

    Scanner input = new Scanner(System.in, "UTF-8");
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

    Game cGame = (Game) game;
    cGame.setStarttime(new Date());
    for (GameQuestion elem: cGame.getGameQuestions()) {

      Random ran = new Random();
      int givenAnswer = ran.nextInt(4)+1;

      if (givenAnswer == elem.getQuestion().get_correct_answer()) {
        elem.setGiven_answer(true);
      }
      else {
        elem.setGiven_answer(false);
      }
    }

    cGame.setEndtime(new Date());
  }

  @Override
  public void interactivePlayGame(Object game) {
    Game cGame = (Game) game;
    cGame.setStarttime(new Date());
    for (GameQuestion elem: cGame.getGameQuestions()) {

      Scanner input = new Scanner(System.in, "UTF-8");

      System.out.println("\n\n\nQ: " + elem.getQuestion().get_question() + "\n");

      int co=1;
      for (String answerElem: elem.getQuestion().get_answer_list()) {
        System.out.println("A" + co + ": " + answerElem);
        co++;
      }

      int givenAnswer = Integer.parseInt(input.nextLine());

      if (givenAnswer == elem.getQuestion().get_correct_answer()) {
        System.out.println("Hurra! Ihre Antwort ist korrekt");
        elem.setGiven_answer(true);
      }
      else {
        System.out.println("Schade! Ihre Antwort ist leider falsch");
        elem.setGiven_answer(false);
      }
    }
    cGame.setEndtime(new Date());

  }

  @Override
  public void persistGame(Object game) {
    EntityManager em = this.lab02EntityManager.getEntityManager();
    em.getTransaction().begin();


    List<GameQuestion> ga_que;
    if (!((Game) game).getGameQuestions().isEmpty()
            && ((Game) game).getGameQuestions().get(0) instanceof GameQuestion) {

      ga_que = (List<GameQuestion>) ((Game) game).getGameQuestions();
    } else {
      throw new IllegalArgumentException("Liste ist leer oder enthält falschen typen");
    }

    for (GameQuestion elem: ga_que) {
      em.persist(elem);
    }

    if (((Game) game).getPlayer() != null) {
      em.persist(((Game) game).getPlayer());
    }
    else {
      throw new IllegalArgumentException("Player is null or is no instanceof Player");
    }


    if (game instanceof Game ) {
      em.persist(game);
    }
    else {
      throw new IllegalArgumentException("Game is null or is no instanceof Game");
    }

    em.getTransaction().commit();
    em.close();
  }

}
