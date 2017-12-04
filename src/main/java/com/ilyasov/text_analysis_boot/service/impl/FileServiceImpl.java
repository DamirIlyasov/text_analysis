package com.ilyasov.text_analysis_boot.service.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ilyasov.text_analysis_boot.service.FileService;
import org.springframework.stereotype.Service;

/**
 * @author damir
 */
@Service
public class FileServiceImpl implements FileService {
  private BufferedReader positive = new BufferedReader(new FileReader("/home/damir/ideaProjects/text-analysis111/src/main/resources/positive.txt"));
  private BufferedReader negative = new BufferedReader(new FileReader("/home/damir/ideaProjects/text-analysis111/src/main/resources/negative.txt"));

  public FileServiceImpl() throws FileNotFoundException {
  }

  @Override
  public ArrayList<String> getPositiveWords() {
    ArrayList<String> positiveWords = new ArrayList<>();
    try {
      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = positive.readLine()) != null) {
        stringBuilder.append(line);
      }
      String positiveString = stringBuilder.toString();
      positiveWords = new ArrayList<>(Arrays.asList(positiveString.split(" ")));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return positiveWords;
  }

  @Override
  public ArrayList<String> getNegativeWords() {
    ArrayList<String> negativeWords = new ArrayList<>();
    try {


      StringBuilder stringBuilder = new StringBuilder();
      String line;
      while ((line = negative.readLine()) != null) {
        stringBuilder.append(line);
      }
      String negativeString = stringBuilder.toString();
      negativeWords = new ArrayList<>(Arrays.asList(negativeString.split(" ")));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return negativeWords;
  }
}
