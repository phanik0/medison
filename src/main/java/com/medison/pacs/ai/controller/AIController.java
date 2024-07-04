package com.medison.pacs.ai.controller;

import com.medison.pacs.ai.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
@RequiredArgsConstructor
@Controller
public class AIController {
    private final AIService service;
}
