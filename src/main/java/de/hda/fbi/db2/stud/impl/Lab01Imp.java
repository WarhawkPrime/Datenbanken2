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

import de.hda.fbi.db2.stud.*;

public class Lab01Imp extends Lab01Data {

  //needed to store the categories
  private ArrayList<Category> categories;



  @Override
  public List<?> getQuestions() {
    return null;
  }

  @Override
  public List<?> getCategories() {

    return null;
  }

  @Override
  public void loadCsvFile(List<String[]> additionalCsvLines) {
    String row;
    try {
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(
              System.getProperty("user.dir") + "/src/main/resources/Wissenstest_sample200.csv"), "UTF-8"
      ));

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(";");
        // do something with the data;
        //ID;_frage;_antwort_1;_antwort_2;_antwort_3;_antwort_4;_loesung;_kategorie

        //create question
        Question question = new Question();

        //set the values for the question

        //create category if it doesnt exist

      }

      csvReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  //method to check if a category is new


}
