package de.hda.fbi.db2.stud.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



@Entity
@Table(schema = "hamwil")
public class Category {

  @Id
  @GeneratedValue
  private int id;
  @Column( unique=true )
  private String name;
  @OneToMany( mappedBy = "category" )
  private List<Question> questions;

  public Category(){};

  public Category(String name) {
    this.questions = new ArrayList<>();
    this.name = name;
  }

  /**
   * returns the pk.
   * @return id
   */
  public int getId() {
    return id;
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
