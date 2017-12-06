package com.ilyasov.text_analysis_boot.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilyasov.text_analysis_boot.entity.DataRecord;
import com.ilyasov.text_analysis_boot.entity.Result;
import com.ilyasov.text_analysis_boot.service.DataRecordService;
import com.ilyasov.text_analysis_boot.service.FileService;
import com.ilyasov.text_analysis_boot.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {

  private RestTemplate restTemplate = new RestTemplate();
  private ObjectMapper mapper = new ObjectMapper();
  private final String APIKEY = "7b2d9d2f8316dd9495ef8c239db8ce31a55b7ab2";
  private List<String> positiveWords;
  private List<String> negativeWords;

  private FileService fileService;

  private TextService textService;

  private DataRecordService dataRecordService;

  @Autowired
  public MainController(FileService fileService, TextService textService, DataRecordService dataRecordService) {
    this.fileService = fileService;
    this.textService = textService;
    this.dataRecordService = dataRecordService;
    positiveWords = fileService.getPositiveWords();
    negativeWords = fileService.getNegativeWords();
  }

  @RequestMapping(value = "/sentiment/send", method = RequestMethod.POST)
  public void getTextSentiment(@RequestParam("text") String text) throws IOException {
    int positiveCount = 0;
    int negativeCount = 0;
    List<String> inputedStrings = textService.convert(text.toLowerCase());
    for (String anInputString : inputedStrings) {
      if (positiveWords.contains(anInputString)) {
        positiveCount++;
      }
      if (negativeWords.contains(anInputString)) {
        negativeCount++;
      }
    }
    double mood = (double) (positiveCount - negativeCount) / (double) inputedStrings.size();

//    MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
//    parametersMap.add("text", text);
//    parametersMap.add("lang", "ru");
//    String response = restTemplate.postForObject("https://api.repustate.com/v3/" + APIKEY + "/score.json", parametersMap, String.class);
//    SentimentResponse sentimentResponse = mapper.readValue(response, SentimentResponse.class);
    DataRecord dataRecord = new DataRecord();
    Date curerntDate = new Date();
    StringBuilder dateString = new StringBuilder();
    if (curerntDate.getDate() < 10) {
      dateString.append("0" + curerntDate.getDate());
    }
    else {
      dateString.append(curerntDate.getDate());
    }
    dateString.append(".");
    if (curerntDate.getMonth() < 10) {
      dateString.append("0" + curerntDate.getMonth());
    }
    else {
      dateString.append(curerntDate.getMonth());
    }
    dateString.append(" ");
    if (curerntDate.getHours() < 10) {
      dateString.append("0" + curerntDate.getHours());
    }
    else {
      dateString.append(curerntDate.getHours());
    }
    dateString.append(":");
    if (curerntDate.getMinutes() < 10) {
      dateString.append("0" + curerntDate.getMinutes());
    }
    else {
      dateString.append(curerntDate.getMinutes());
    }
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    simpleDateFormat.format(new Date());
    dataRecord.setDate(dateString.toString());
//    dataRecord.setValue(sentimentResponse.getScore());
    dataRecord.setValue(mood);
    dataRecordService.save(dataRecord);
  }

  @RequestMapping(value = "/records", method = RequestMethod.GET)
  public Result getRecords() {
    return new Result(dataRecordService.getAll());
  }
}
