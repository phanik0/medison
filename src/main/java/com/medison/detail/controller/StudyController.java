package com.medison.detail.controller;

import com.medison.detail.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class StudyController {
    private final StudyService studyService;
    @GetMapping("/detail")
    public String test(){
        return"detail";
    }

}
