package com.ilyasov.text_analysis_boot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilyasov.text_analysis_boot.entity.DataRecord;
import com.ilyasov.text_analysis_boot.responses.SentimentResponse;
import com.ilyasov.text_analysis_boot.service.DataRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class MainController {

    RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper mapper = new ObjectMapper();
    private final String APIKEY = "7b2d9d2f8316dd9495ef8c239db8ce31a55b7ab2";
    @Autowired
    DataRecordService dataRecordService;

    @RequestMapping(value = "/sentiment/send", method = RequestMethod.POST)
    public void getTextSentiment(String text) throws IOException {

        MultiValueMap<String, String> parametersMap = new LinkedMultiValueMap<String, String>();
        parametersMap.add("text", text);
        parametersMap.add("lang", "ru");
        String response = restTemplate.postForObject("https://api.repustate.com/v3/" + APIKEY + "/score.json", parametersMap, String.class);
        SentimentResponse sentimentResponse = mapper.readValue(response, SentimentResponse.class);
        DataRecord dataRecord = new DataRecord();
        dataRecord.setDate(new Date());
        dataRecord.setValue(sentimentResponse.getScore());
        dataRecordService.save(dataRecord);
    }

    @RequestMapping(value = "/records", method = RequestMethod.GET)
    public List<DataRecord> getRecords() {
        return dataRecordService.getAll();
    }
}
