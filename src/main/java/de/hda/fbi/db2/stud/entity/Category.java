package de.hda.fbi.db2.stud.entity;

import java.util.*;

public class Category {
private
  String name;
  ArrayList<Question> questions;



  public Category() {

  }

  public void add_question(Question question) {

    try {
      questions.add(question);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Question get_question(int index) {
    return questions.get(index);
  }

  //========== getter and setter ==========
  public String get_name() {return this.name;}
  public void set_name(String name) {this.name = name;}



}
