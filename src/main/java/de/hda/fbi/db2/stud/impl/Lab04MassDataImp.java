package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.api.Lab03Game;
import de.hda.fbi.db2.api.Lab04MassData;
import de.hda.fbi.db2.stud.entity.Game;
import de.hda.fbi.db2.stud.entity.GameQuestion;
import de.hda.fbi.db2.stud.entity.Player;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


public class Lab04MassDataImp extends Lab04MassData {

  @Override
  public void createMassData() {


    var before = System.currentTimeMillis();

    EntityManager emm = this.lab02EntityManager.getEntityManager();
    EntityTransaction tr = emm.getTransaction();

    HashMap<String, Player> hashPlayers = new HashMap<String, Player>();
    List<Player> players = emm.createQuery("SELECT p FROM Player p").getResultList();

    for (Iterator i = players.iterator(); i.hasNext(); ) {
      Player cm = (Player) i.next();
      hashPlayers.put(cm.getName(), cm);
    }


    for (int playerloop = 0; playerloop < 10000; playerloop++) {

      tr.begin();

      Player player = null;
      if (hashPlayers.containsKey(Integer.toString(playerloop))) {
        player = hashPlayers.get(Integer.toString(playerloop));
      } else {

        player = new Player(Integer.toString(playerloop));
        hashPlayers.put(player.getName(), player);
        emm.persist(player);
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

        changeDate((Game) game);

        List<GameQuestion> gaQue;

        if (!((Game) game).getGameQuestions().isEmpty()
                && ((Game) game).getGameQuestions().get(0) instanceof GameQuestion) {

          gaQue = (List<GameQuestion>) ((Game) game).getGameQuestions();
        } else {
          throw new IllegalArgumentException("Liste ist leer oder enthält falschen typen");
        }


        for (GameQuestion elem : gaQue) {
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
    var elaTime = (after - before) / 1000;
    System.out.println("Time to persist: " + (int) (elaTime / 60) + "min " + elaTime % 60 + "s");
    emm.close();


  }

  void changeDate(Game game) {

    Random rand = new Random();
    int days = rand.nextInt(15);

    Calendar cal = Calendar.getInstance();

    cal.setTime(game.getStarttime());
    cal.add(Calendar.DATE, -days);

    Date start = cal.getTime();
    game.setStarttime(start);

    cal.setTime(game.getEndtime());
    cal.add(Calendar.DATE, -days);

    Date end = cal.getTime();
    game.setEndtime(end);

  }

  @Override
  public void analyzeData() {

    System.out.println("Analyzing data...");

    EntityManager emm = this.lab02EntityManager.getEntityManager();
    EntityTransaction tr = emm.getTransaction();

    //Ausgabe aller Spieler (Spielername), die in einem bestimmten Zeitraum gespielt hatten.
    firstQuery(emm);

    //Ausgabe zu einem bestimmten Spieler: Alle Spiele(ID,Datum), sowie die Anzahl der korekten Antworten pro Spiel
    // mit Angabe der Gesamtanzahl der Fragen pro Spiel bzw. alternativ den Prozentsatz der korrekt beantworteten Fragen.
    secondQuery(emm);

    //Ausgabe aller Spieler mit Anzahl der gespielten Spiele, nach Anzahl absteigend geordnet
    thirdQuery(emm);

    //Ausgabe der am meisten gefragten Kategorie, oder alternativ, die Beliebtheit der Kategorien nach Anzahl der Auswahl
    //absteigend sortiert
    fourthQuery(emm);

  }




  public void firstQuery(EntityManager em) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    //format of Date: Tue Jul 07 20:33:56 CEST 2020
    //SELECT g FROM Game g WHERE g.starttime BETWEEN :s AND :e ", Game.class
    Date start = new Date();
    Date end = new Date();

    //select player_name from hamwil.game where starttime='2020-07-07 15:35:26.162' and endtime='2020-07-07 15:37:05.247'
    TypedQuery<Game> query = em.createQuery("SELECT g FROM Game g WHERE g.starttime BETWEEN :start AND :end ", Game.class);
    query.setParameter("start", start );
    query.setParameter("end", end);
    List<Game> games = query.getResultList();

    System.out.println("Alle Spieler die in einem Zeitraum von " + start + " bis " + end + " gespielt haben:");
    for (Game elem : games) {
      System.out.println(elem.getPlayer());
    }

  }

  public void secondQuery(EntityManager em) {

    //select game.id, game.endtime, gamequestion.id, gamequestion.givenanswer from hamwil.game inner join hamwil.gamequestion on
    //hamwil.game.id = hamwil.gamequestion.game_id where game.player_name='Dennis'

    Scanner sc = new Scanner(System.in);

    String playerName = "";

    System.out.println();
    System.out.println("Liste aller verfügbaren Spieler: ");
    List<Player> players = em.createQuery("SELECT p from Player p").getResultList();

    int counter = 0;
    for (Player elem: players) {
      System.out.println(counter + " : " + elem.getName());
      counter++;
    }
    System.out.println("Bitte Spieler namen genau eingeben : ");
    playerName = sc.nextLine();

    Query secondQuery = em.createQuery("SELECT g, gq from Game g, GameQuestion gq " +
            "WHERE gq.game = g AND g.player.name= :playerName ");
    secondQuery.setParameter("playerName", playerName);

    List<Object[]> objects = secondQuery.getResultList();

    List<Game> games;
    List<GameQuestion> gameQuestions;

    for (Object elem[] : objects) {
      Game game = (Game) elem[0];
      System.out.println(game.getPlayer().getName());
      GameQuestion gq = (GameQuestion) elem[1];
      System.out.println(gq.getQuestion().get_question());
    }

    System.out.println();

    System.out.println();
    System.out.println("Alle Spiele von Spieler " + playerName + " : ");

    System.out.println("Prozentsatz der korrekt beantworteten Fragen: ");

    System.out.println();

  }

  public void thirdQuery(EntityManager em) {

    //select game.player_name, count(*) from hamwil.game group by game.player_name order by count(*) desc

    //List<Player> players = em.createQuery("").getResultList();


  }

  public void fourthQuery(EntityManager em) {

  }



}
