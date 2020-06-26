package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.controller.Controller;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.GameQuestion;
import de.hda.fbi.db2.stud.entity.Player;
import de.hda.fbi.db2.stud.entity.Question;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Query;


public class Lab03GameImp extends Lab03Game {

  private HashMap<String, Player> hashPlayers = new HashMap<String, Player>();

  @Override
  public Object getOrCreatePlayer(String playerName) {

    EntityManager emM = this.lab02EntityManager.getEntityManager();
    emM.getTransaction().begin();

    if(hashPlayers.size() == 0) {

      List<Player> players = emM.createQuery("SELECT p FROM Player p").getResultList();

      //emM.close();

      for (Iterator i = players.iterator(); i.hasNext(); ) {
        Player cm = (Player) i.next();
        hashPlayers.put(cm.getName(), cm);
      }
    }

    if (hashPlayers.containsKey(playerName)) {

      //emM.close();
      return hashPlayers.get(playerName);
    } else {

      Player p = new Player(playerName);
      hashPlayers.put(p.getName(), p);
      emM.persist(p);
      emM.getTransaction().commit();

      //emM.close();
      return p;
    }
  }

  @Override
  public Object interactiveGetOrCreatePlayer() {

    Scanner input = new Scanner(System.in, "UTF-8");
    System.out.println("Hier können Sie den Namen auswählen : ");
    String playerName = input.nextLine();

    return this.getOrCreatePlayer(playerName);

  }

  @Override
  public List<?> getQuestions(List<?> categories, int amountOfQuestionsForCategory) {
    List<Question> gameQuestions = new ArrayList<Question>();
    for (Category elem: (List<Category>)categories) {
      //System.out.println(elem.get_name());

      if (elem.get_questions().size() <= amountOfQuestionsForCategory) {
        gameQuestions.addAll(elem.get_questions());
      } else {
        List<Integer> randomNums = new ArrayList<>();
        Random ran = new Random();
        for (int i = 0; i < amountOfQuestionsForCategory; i++) {
          int newRandom = ran.nextInt(elem.get_questions().size());
          while (randomNums.contains(newRandom)) {
            newRandom = ran.nextInt(elem.get_questions().size());
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
      System.out.println(counter + ": " + elem.get_name());
      counter++;
    }


    System.out.println("query begins:");

    EntityManager em = this.lab02EntityManager.getEntityManager();
    em.getTransaction().begin();



    Query countp = em.createQuery("SELECT COUNT(p) FROM Player p");
    Query countg = em.createQuery("SELECT COUNT(g) FROM Game g");
    Query countgq = em.createQuery("SELECT COUNT(gq) FROM GameQuestion gq");

    String p = countp.getSingleResult().toString();
    String g = countg.getSingleResult().toString();
    String gq = countgq.getSingleResult().toString();

    System.out.println("Count Player:" +  p);
    System.out.println("Count Games:" +  g);
    System.out.println("Count GameQuestion:" +  gq);

    List<Category> parsedCats = new ArrayList<Category>();
    boolean accurateCat = false;
    do {
      parsedCats.clear();
      System.out.println("Wählen Sie mindestens 2 Kategorien aus,"
              + " geben sie diese durch Komma's getrennt an: ");
      String questions = input.nextLine();
      String[] splitStrings = questions.split(",");

      for (String elem : splitStrings) {
        elem = elem.replaceAll("[^0-9]", "");
        parsedCats.add(allCategorys.get(Integer.parseInt(elem)));
      }
      if (parsedCats.size() >= 2) {
        accurateCat = true;
      } else {
        System.out.println("Sie haben keine 2 Kategorien angegeben\n");
      }
    } while (!accurateCat);

    System.out.println("Wie viele Fragen möchten sie pro Category haben? :");
    String inputAmoutofQuestions = input.nextLine();
    int amountOfQuestions = Integer.parseInt(inputAmoutofQuestions);

    return this.getQuestions(parsedCats, amountOfQuestions);

  }

  @Override
  public Object createGame(Object player, List<?> questions) {
    Game g = new Game(player);
    g.setGame_questions(questions);
    return g;
  }

  @Override
  public void playGame(Object game) {

    Game cgame = (Game) game;
    cgame.setStarttime(new Date());
    for (GameQuestion elem: cgame.getGameQuestions()) {

      Random ran = new Random();
      int givenAnswer = ran.nextInt(4) + 1;

      if (givenAnswer == elem.getQuestion().get_correct_answer()) {
        elem.setGiven_answer(true);
      } else {
        elem.setGiven_answer(false);
      }
    }

    cgame.setEndtime(new Date());
  }

  @Override
  public void interactivePlayGame(Object game) {
    Game cgame = (Game) game;
    cgame.setStarttime(new Date());
    for (GameQuestion elem: cgame.getGameQuestions()) {

      Scanner input = new Scanner(System.in, "UTF-8");

      System.out.println("\n\n\nQ: " + elem.getQuestion().get_question() + "\n");



      int co = 1;
      for (String answerElem: elem.getQuestion().get_answer_list()) {
        System.out.println("A" + co + ": " + answerElem);
        co++;
      }

      int givenAnswer = Integer.parseInt(input.nextLine());

      if (givenAnswer == elem.getQuestion().get_correct_answer()) {
        System.out.println("Hurra! Ihre Antwort ist korrekt");
        elem.setGiven_answer(true);
      } else {
        System.out.println("Schade! Ihre Antwort ist leider falsch");
        elem.setGiven_answer(false);
      }
    }
    cgame.setEndtime(new Date());

  }

  @Override
  public void persistGame(Object game) {
    EntityManager em = this.lab02EntityManager.getEntityManager();
    em.getTransaction().begin();


    List<GameQuestion> gaQue;
    if (!((Game) game).getGameQuestions().isEmpty()
            && ((Game) game).getGameQuestions().get(0) instanceof GameQuestion) {

      gaQue = (List<GameQuestion>) ((Game) game).getGameQuestions();
    } else {
      throw new IllegalArgumentException("Liste ist leer oder enthält falschen typen");
    }

    for (GameQuestion elem: gaQue) {
      em.persist(elem);
    }

    if (game instanceof Game) {
      em.persist(game);
    } else {
      throw new IllegalArgumentException("Game is null or is no instanceof Game");
    }

    em.getTransaction().commit();
    em.clear();
    em.close();
  }

}
