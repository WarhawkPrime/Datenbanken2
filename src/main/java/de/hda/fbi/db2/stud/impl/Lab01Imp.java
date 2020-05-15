package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Lab01Imp extends Lab01Data {
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
    System.out.println(System.getProperty("user.dir"));
    try {
      BufferedReader csvReader = new BufferedReader(new FileReader(
              System.getProperty("user.dir") + "/src/main/resources/Wissenstest_sample200.csv"));

      while ((row = csvReader.readLine()) != null) {
        String[] data = row.split(";");
        // do something with the data
      }

      csvReader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
