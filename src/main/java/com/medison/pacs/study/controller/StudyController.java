package com.medison.pacs.study.controller;

import com.medison.pacs.image.domain.Image;
import com.medison.pacs.image.service.ImageService;
import com.medison.pacs.series.service.SeriesService;
import com.medison.pacs.study.domain.Study;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class StudyController {
    private final StudyService studyService;
    private final SeriesService seriesService;
    private final ImageService imageService;

    public Study getStudyByKey(long studyKey){
        return studyService.getStudyByStudyKey(studyKey);
    }



//    @GetMapping("/study/{studyKey}")
//    public ModelAndView renderStudy(@PathVariable long studyKey, Model model) {
//        ModelAndView modelAndView = new ModelAndView("study/study");
//        Study study = getStudyByKey(studyKey);
//        modelAndView.addObject("study", study);
//        List<Image> images = imageService.findImagesByStudyKey(studyKey);
//        modelAndView.addObject("images", images);
//        modelAndView.addObject("series", seriesService.findSeriesByStudyKey(studyKey));
//
//
//        return modelAndView;
//    }
@GetMapping("/study/{studyKey}")
public String renderStudy(@PathVariable long studyKey, Model model) {
    Study study = getStudyByKey(studyKey);
    model.addAttribute("study", study);
    List<Image> images = imageService.findImagesByStudyKey(studyKey);
    model.addAttribute("images", images);

    if (!images.isEmpty()) {
        // 백슬래시를 슬래시로 변경
        String dicomPath = images.get(0).getPath().replace("\\", "/");
        model.addAttribute("dicomPath", dicomPath);
    }

    return "study/study";
}
}
