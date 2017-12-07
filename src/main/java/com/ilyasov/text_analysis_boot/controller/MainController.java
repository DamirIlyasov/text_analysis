package com.ilyasov.text_analysis_boot.controller;

import java.io.IOException;
import java.util.List;

import com.ilyasov.text_analysis_boot.entity.DataRecord;
import com.ilyasov.text_analysis_boot.entity.Result;
import com.ilyasov.text_analysis_boot.requestPojos.RequestPojo;
import com.ilyasov.text_analysis_boot.service.DataRecordService;
import com.ilyasov.text_analysis_boot.service.FileService;
import com.ilyasov.text_analysis_boot.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {

  private RestTemplate restTemplate = new RestTemplate();
  private final String APIKEY = "7b2d9d2f8316dd9495ef8c239db8ce31a55b7ab2";
  private List<String> positiveWords;
  private List<String> negativeWords;
  private FileService fileService;
  private TextService textService;

  private DataRecordService dataRecordService;

  @Autowired
  public MainController(FileService fileService, TextService textService, DataRecordService dataRecordService) {
    this.textService = textService;
    this.dataRecordService = dataRecordService;
    positiveWords = fileService.getPositiveWords();
    negativeWords = fileService.getNegativeWords();
  }

  @RequestMapping(value = "/sentiment/send", method = RequestMethod.POST)
  public void getTextSentiment(@RequestBody RequestPojo requestPojo) throws IOException {
    int positiveCount = 0;
    int negativeCount = 0;
    List<String> inputedStrings = textService.convert(requestPojo.getText().toLowerCase());
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
    dataRecord.setDate(requestPojo.getDate());
//    dataRecord.setValue(sentimentResponse.getScore());
    dataRecord.setValue(mood);
    dataRecordService.save(dataRecord);
  }

  @RequestMapping(value = "/records", method = RequestMethod.GET)
  public Result getRecords() {
    return new Result(dataRecordService.getAll());
  }
}
