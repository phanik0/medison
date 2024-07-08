package com.medison.pacs.ai.service;

import com.medison.pacs.ai.domain.AI;
import com.medison.pacs.ai.domain.AIRepository;
import com.medison.pacs.ai.domain.PrContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AIService {
    private final AIRepository aiRepository;
    public List<AI> findAIByStudyKey(long studyKey){
        return aiRepository.findByStudyKey(studyKey);
    }

    public List<PrContent>findPrContentByStudyKey(long studyKey){
        List<PrContent>prContentList = new ArrayList<>();
        for(AI ai : findAIByStudyKey(studyKey)){
            prContentList.add(ai.getPrContentData());
        }
        return prContentList;
    }
}
