package com.medison.pacs.ai.controller;

import com.medison.pacs.ai.domain.AI;
import com.medison.pacs.ai.domain.PrContent;
import com.medison.pacs.ai.domain.ProcessedPrData;
import com.medison.pacs.ai.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AIController {
    private final AIService service;

    @ResponseBody
    @GetMapping("/ai1/{studyKey}")
    public ResponseEntity<List<ProcessedPrData>>getProcessedPrContentByStudyKey(@PathVariable long studyKey){
        List<ProcessedPrData> processedPrData = processPrContentData(studyKey);
        return ResponseEntity.ok(processedPrData);
    }
    @ResponseBody
    @GetMapping("/ai/{studyKey}")
    public ResponseEntity<List<PrContent>>getPrContentByStudyKey(@PathVariable long studyKey){
        List<AI>AIList = service.findAIByStudyKey(studyKey);
        List<PrContent>prContentList = new ArrayList<>();
        for(AI ai : AIList){
            PrContent prContent = ai.getPrContentData();
            prContentList.add(prContent);

        }
        return ResponseEntity.ok(prContentList);
    }



    public List<ProcessedPrData> processPrContentData(long studyKey){
        List<ProcessedPrData> processedPrDataList = new ArrayList<>();
        List<AI> listByStudyKey = service.findAIByStudyKey(studyKey);

        for(AI ai : listByStudyKey){
            PrContent prContent = ai.getPrContentData();
            if(prContent != null){
                ProcessedPrData processedPrData = new ProcessedPrData();
                processedPrDataList.add(processedPrData.convertToProcessedPrData(prContent));
            }
        }
        return processedPrDataList;
    }

}
