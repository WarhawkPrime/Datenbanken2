package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(schema = "hamwil")
public class Question {
  private
      @Id
      int id;
  int correctAnswer;
  String question;
  @ManyToOne
  Category category;
  @OrderBy
  @ElementCollection
  List<String> answers;
  //@OneToMany(mappedBy = "question")
  //private List<Game_Question> game_questions;


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

  public List<String> get_answer_list() {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Question question = (Question) o;
    return id == question.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}