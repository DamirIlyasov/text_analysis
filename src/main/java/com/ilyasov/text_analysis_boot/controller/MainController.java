package com.ilyasov.text_analysis_boot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilyasov.text_analysis_boot.entity.DataRecord;
import com.ilyasov.text_analysis_boot.entity.Result;
import com.ilyasov.text_analysis_boot.responses.SentimentResponse;
import com.ilyasov.text_analysis_boot.service.DataRecordService;
import com.ilyasov.text_analysis_boot.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class MainController {

  private RestTemplate restTemplate = new RestTemplate();
  private ObjectMapper mapper = new ObjectMapper();
  private final String APIKEY = "7b2d9d2f8316dd9495ef8c239db8ce31a55b7ab2";
  @Autowired
  FileService fileService;
  private ArrayList<String> positiveWords;
  private ArrayList<String> negativeWords;

  @Autowired
  DataRecordService dataRecordService;


  @RequestMapping(value = "/sentiment/send", method = RequestMethod.POST)
  public void getTextSentiment(@RequestParam("text") String text,
                               @RequestParam("date") Date date) throws IOException {

    positiveWords = fileService.getPositiveWords();
    negativeWords = fileService.getNegativeWords();
    int positiveCount = 0;
    int negativeCount = 0;
    String inputString[] = text.toLowerCase().split(" ");
    for (String anInputString : inputString) {
      if (positiveWords.contains(anInputString)) {
        positiveCount++;
      }
      if (negativeWords.contains(anInputString)) {
        negativeCount++;
      }
    }
    double mood = (double) (positiveCount - negativeCount) / (double) inputString.length;

//    MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
//    parametersMap.add("text", text);
//    parametersMap.add("lang", "ru");
//    String response = restTemplate.postForObject("https://api.repustate.com/v3/" + APIKEY + "/score.json", parametersMap, String.class);
//    SentimentResponse sentimentResponse = mapper.readValue(response, SentimentResponse.class);
    DataRecord dataRecord = new DataRecord();
    dataRecord.setDate(date);
//    dataRecord.setValue(sentimentResponse.getScore());
    dataRecord.setValue(mood);
    dataRecordService.save(dataRecord);
  }

  @RequestMapping(value = "/records", method = RequestMethod.GET)
  public Result getRecords() {
    return new Result(dataRecordService.getAll());
  }
}
