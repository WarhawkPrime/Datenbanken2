package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import de.hda.fbi.db2.stud.entity.Category;
import de.hda.fbi.db2.stud.entity.Question;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Lab01Imp extends Lab01Data {

  //needed to store the categories
  private ArrayList<Category> categories;

  @Override
  public List<Question> getQuestions() {
    List<Question> allQuest = new ArrayList<Question>();
    for (Category elem: categories) {
      allQuest.addAll(elem.get_questions());
    }
    return allQuest;
  }

  @Override
  public List<Category> getCategories() {

    return categories;
  }

  @Override
  public void loadCsvFile(List<String[]> additionalCsvLines) {
    String row;
    this.categories = new ArrayList<Category>();
    try {
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(
          System.getProperty("user.dir") + "/src/main/resources/Wissenstest_sample200.csv"), "UTF-8"
      ));
      csvReader.readLine(); // consume first line with legend
      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(";");
        //ID;     _frage;_antwort_1;_antwort_2;_antwort_3;_antwort_4;_loesung;    _kategorie
        //data[0],data[1],data[2],    data[3],  data[4],    data[5],    data[6],  data[7],

        //create question
        Question question = new Question();

        //set the values for the question
        int id = Integer.parseInt(data[0]);
        question.set_id(id);
        question.set_question(data[1]);

        question.fill_answers(data[2]);
        question.fill_answers(data[3]);
        question.fill_answers(data[4]);
        question.fill_answers(data[5]);
        question.set_correct_answer(Integer.parseInt(data[6]));

        //create category if it doesnt exist
        if (!search_for_categories(data[7])) {
          Category category = new Category(data[7]);
          System.out.println(data[7]);
          this.categories.add(category);
        }
        add_question(question, data[7]);
      }

      csvReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * searches for an existing category.
   * @param categoryName is the search parameter
   * @return returns a boolean value if a category is found
   */
  public boolean search_for_categories(String categoryName) {
    for (Category elem: categories) {
      if (elem.get_name().equals(categoryName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * adds a queston to the category.
   * @param question Question to be added
   * @param categoryName Category to add the queston to
   */
  public void add_question(Question question, String categoryName) {
    for (Category elem: categories) {
      if (elem.get_name().equals(categoryName)) {
        elem.add_question(question);
      }
    }
  }


}
