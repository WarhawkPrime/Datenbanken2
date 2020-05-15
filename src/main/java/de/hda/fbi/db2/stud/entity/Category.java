package de.hda.fbi.db2.stud.entity;

import java.util.*;

public class Category {
private
  String name;
  ArrayList<Question> questions;



  public Category() {

  }

  public void add_question(Question question) {
    questions.add(question);
  }

  public Question get_question(int index) {
    return questions.get(index);
  }

  //========== getter and setter ==========
  public String getName() {return this.name;}

}
