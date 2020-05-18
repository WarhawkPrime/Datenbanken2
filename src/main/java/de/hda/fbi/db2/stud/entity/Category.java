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
   * Adds a question.
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
   * Get question at given index.
   * @param index Position of the question to be returned
   * @return Question at given index
   */
  public Question get_question(int index) {
    return questions.get(index);
  }

  //========== getter and setter ==========
  public String get_name() {
    return this.name;
  }

  public List<Question> get_questions() {
    return this.questions;
  }

  public void set_name(String name) {
    this.name = name;
  }



}
