package de.hda.fbi.db2.stud.entity;


import java.util.ArrayList;
import java.util.List;

public class Category {
  private String name;
  private ArrayList<Question> questions;

  public Category(String name) {
    questions = new ArrayList<Question>();
    this.name = name;
  }

  /**
   * Adds Question.
   * @param question Question to be added
   */
  public void add_question(Question question) {
    try {
      questions.add(question);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * gets a question at the given position.
   * @param index Position for the Question you want to get
   * @return returns  question at position
   */
  public Question get_question(int index) {
    return questions.get(index);
  }

  //========== getter and setter ==========
  public String get_name() {
    return this.name;
  }

  public List<Question> get_questions()  {
    return this.questions;
  }

  public void set_name(String name) {
    this.name = name;
  }



}
