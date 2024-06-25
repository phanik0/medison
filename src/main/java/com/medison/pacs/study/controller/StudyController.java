package com.medison.pacs.study.controller;

import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class StudyController {
    private final StudyService studyService;

    @GetMapping("")
    public String index(){
        return "index";
    }
    @GetMapping("/detail")
    public String test(){
        return"detail";
    }

//    public ModelAndView getStudyById(){
//        ModelAndView modelAndView = new ModelAndView();
//
//    }
}
