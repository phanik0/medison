package com.medison.pacs.study.controller;

import com.medison.pacs.image.controller.ImageController;
import com.medison.pacs.image.domain.Image;
import com.medison.pacs.image.domain.ImageRepository;
import com.medison.pacs.image.service.ImageService;
import com.medison.pacs.series.controller.SeriesController;
import com.medison.pacs.series.domain.Series;
import com.medison.pacs.series.service.SeriesService;
import com.medison.pacs.study.domain.StudyResponseDto;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class StudyController {
    private final StudyService studyService;
    private final SeriesService seriesService;
    private final ImageService imageService;


    @GetMapping("")
    public String index(){
        return "index";
    }
    @GetMapping("/detail")
    public String test(){
        return"detail";
    }

    public StudyResponseDto getStudyByKey(long studyKey){
        return new StudyResponseDto(studyService.getStudyByStudyKey(studyKey));
    }


    public ModelAndView renderStudy(@PathVariable long studyKey){
        List<Image> imageList =imageService.findImagesByStudyKey(studyKey);

        ModelAndView modelAndView = new ModelAndView("study");
        modelAndView.addObject("study", studyService.getStudyByStudyKey(studyKey));
        return modelAndView;
    }
}
