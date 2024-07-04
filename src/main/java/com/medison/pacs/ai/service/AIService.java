package com.medison.pacs.ai.service;

import com.medison.pacs.ai.domain.AIRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AIService {
    private final AIRepository aiRepository;
}
