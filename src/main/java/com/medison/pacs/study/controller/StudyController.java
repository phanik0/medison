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

import java.util.List;

@RequiredArgsConstructor
@Controller
public class StudyController {
    private final StudyService studyService;
    private final SeriesService seriesService;
    private final ImageService imageService;

    public Study getStudyByKey(long studyKey){
        return studyService.getStudyByStudyKey(studyKey);
    }



    @GetMapping("/study/{studyKey}")
    public String renderStudy(@PathVariable long studyKey, Model model) {
        Study study = getStudyByKey(studyKey);
        model.addAttribute("study", study);
        List<Image> images = imageService.findImagesByStudyKey(studyKey);
        model.addAttribute("images", images); // 이미지 리스트를 모델에 추가

        if (!images.isEmpty()) {
            String dicomPath = images.get(0).getPath();
            model.addAttribute("dicomPath", dicomPath.replace("\\", "/")); // 경로를 모델에 추가
            System.out.println("dicomPath: " + dicomPath);
        }
        return "study/study";
    }

}
