package com.medison.pacs.ai.service;

import com.medison.pacs.ai.domain.AI;
import com.medison.pacs.ai.domain.AIRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AIService {
    private final AIRepository aiRepository;
    public List<AI> findAIByStudyKey(long studyKey){
        return aiRepository.findByStudyKey(studyKey);
    }

}
