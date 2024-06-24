package com.medison.detail.controller;

import com.medison.detail.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ImageController {
    private final StudyService studyService;
}
