package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Lab02EntityManagerImp extends Lab02EntityManager {

  @Override @SuppressWarnings("unchecked cast")
  public void persistData() {
    EntityManager eManager = getEntityManager();

    eManager.getTransaction().begin();

    //durch alle Kategorien gehen, für jede die Questions setzen und dann commiten falls nicht existent
    List<Category> cat;
    if( !lab01Data.getCategories().isEmpty() && lab01Data.getCategories().get(0) instanceof Category) {
      cat = (List<Category>) lab01Data.getCategories();
    }
    else {
      throw new IllegalArgumentException( "Liste ist leer oder enthält falschen typen") ;
    }

    List<Question> que;
    if( !lab01Data.getQuestions().isEmpty() && lab01Data.getQuestions().get(0) instanceof Question) {
      que = (List<Question>) lab01Data.getQuestions();
    }
    else {
      throw new IllegalArgumentException( "Liste ist leer oder enthält falschen typen") ;
    }

    for (Category elem: cat) {
      eManager.persist(elem);
    }
    for (Question elem: que) {
      eManager.persist(elem);
    }
    eManager.getTransaction().commit();
    eManager.close();
  }

  @Override
  public EntityManager getEntityManager() {
    EntityManagerFactory eManagerFactory = Persistence.createEntityManagerFactory("fbi-postgresPU");
    return eManagerFactory.createEntityManager();
  }
}
