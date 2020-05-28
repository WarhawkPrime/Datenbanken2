package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;

public class Question {
  private
      int id;
  int correctAnswer;
  String question;
  Category category;
  ArrayList<String> answers;


  public Question() {
    answers = new ArrayList<>();
  }


  //========== getter and setter ==========
  public int get_id() {
    return this.id;
  }

  public int get_correct_answer() {
    return correctAnswer;
  }

  public String get_question() {
    return question;
  }

  public ArrayList<String> get_answer_list() {
    return this.answers;
  }

  public Category get_category() {
    return category;
  }

  /**
   * id is set.
   * @param id is set
   */
  public void set_id(int id) {
    this.id = id;
  }

  /**
   * answer is set.
   * @param answer is set
   */
  public void set_correct_answer(int answer) {
    this.correctAnswer = answer;
  }

  public void set_question(String question) {
    this.question = question;
  }

  /**
   * set category.
   * @param category is set
   */
  public void setCategory(Category category) {
    this.category = category;
  }



  /**
   * fills the list with answers.
   * @param answer List adds a new answer
   */
  public void fill_answers(String answer) {
    try {
      this.answers.add(answer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
