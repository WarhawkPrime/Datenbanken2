package de.hda.fbi.db2.stud.impl;

import de.hda.fbi.db2.api.Lab01Data;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    try {
      BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(
              System.getProperty("user.dir") + "/src/main/resources/Wissenstest_sample200.csv"), "UTF-8"
      ));

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
