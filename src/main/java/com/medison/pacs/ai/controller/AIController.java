package com.medison.pacs.ai.controller;

import com.medison.pacs.ai.domain.AI;
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
    @GetMapping("/ai/{studyKey}")
    public ResponseEntity<List<AI>>findPrContentByStudyKey(@PathVariable long studyKey){
        List<AI> ai= service.findAIByStudyKey(studyKey);
        return ResponseEntity.ok(ai);
    }

    public ProcessedPrData processPrContentData(long studyKey){
        ProcessedPrData processdPrData = new ProcessedPrData();
        List<AI>listByStudyKey = service.findAIByStudyKey(studyKey);




        return  processdPrData;
    }
}
