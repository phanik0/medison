package com.medison.pacs.image.controller;


import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ImageController {
    private final StudyService studyService;
}
