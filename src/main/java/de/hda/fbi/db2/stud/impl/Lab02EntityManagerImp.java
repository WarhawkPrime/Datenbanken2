package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab02EntityManager;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Lab02EntityManagerImp extends Lab02EntityManager {

  @SuppressWarnings("unchecked cast")
  @Override
  public void persistData() {
    EntityManager enManager = getEntityManager();
    enManager.getTransaction().begin();

    List<Category> cat;
    if (!lab01Data.getCategories().isEmpty()
        && lab01Data.getCategories().get(0) instanceof Category) {

      cat = (List<Category>) lab01Data.getCategories();
    } else {
      throw new IllegalArgumentException("Liste ist leer oder enthält falschen typen");
    }

    List<Question> que;
    if (!lab01Data.getQuestions().isEmpty()
        && lab01Data.getQuestions().get(0) instanceof Question) {

      que = (List<Question>) lab01Data.getQuestions();
    } else {
      throw new IllegalArgumentException("Liste ist leer oder enthält falschen typen");
    }

    for (Category elem: cat) {
      enManager.persist(elem);
    }
    for (Question elem: que) {
      enManager.persist(elem);
    }
    enManager.getTransaction().commit();
    enManager.close();
  }

  @Override
  public EntityManager getEntityManager() {
    EntityManagerFactory enManagerFactory =
        Persistence.createEntityManagerFactory("default-postgresPU");
    return enManagerFactory.createEntityManager();
  }
}
