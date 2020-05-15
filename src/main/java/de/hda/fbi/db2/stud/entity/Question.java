package de.hda.fbi.db2.stud.entity;

import java.util.ArrayList;

public class Question {
private
  int id;
  int correct_answer;
  ArrayList<String> answers;


  public Question () {

  }

  //========== getter and setter ==========
  public int get_id() {return this.id;}
  public int get_correct_answer() {return correct_answer;}
  public ArrayList<String> get_answer_list () {return this.answers;}

  public void set_id(int id) {this.id = id;}
  public void set_correct_answer(int correct_answer) {this.correct_answer = correct_answer;}
  public void fill_answers(String answer) {
    try {
      this.answers.add(answer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
