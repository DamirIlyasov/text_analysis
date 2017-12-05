package com.ilyasov.text_analysis_boot.service.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.ilyasov.text_analysis_boot.service.FileService;
import com.ilyasov.text_analysis_boot.service.TextService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author damir
 */
@Service
public class FileServiceImpl implements FileService {
  String filePath = new File("").getAbsolutePath();

  private BufferedReader positive = new BufferedReader(new FileReader(filePath + "/src/main/resources/positive.txt"));
  private BufferedReader negative = new BufferedReader(new FileReader(filePath + "/src/main/resources/negative.txt"));
  private Analyzer analyzer = new RussianAnalyzer();

  @Autowired
  TextService textService;


  public FileServiceImpl() throws FileNotFoundException {
  }

  @Override
  public List<String> getPositiveWords() {
    List<String> positiveWords = new ArrayList<>();
    StringBuilder stringText = new StringBuilder();
    try {
      String line;
      while ((line = positive.readLine()) != null) {
        stringText.append(line.toLowerCase() + " ");
      }
      positiveWords = textService.convert(stringText);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return positiveWords;
  }

  @Override
  public List<String> getNegativeWords() {
    List<String> negativeWords = new ArrayList<>();
    StringBuilder stringText = new StringBuilder();
    try {
      String line;
      while ((line = negative.readLine()) != null) {
        stringText.append(line.toLowerCase() + " ");
      }
      negativeWords = textService.convert(stringText);
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return negativeWords;
  }
}