package com.medison.pacs.study.controller;

import com.medison.pacs.image.domain.Image;
import com.medison.pacs.image.service.ImageService;
import com.medison.pacs.series.service.SeriesService;
import com.medison.pacs.study.domain.Study;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class StudyController {
    private final StudyService studyService;
    private final SeriesService seriesService;
    private final ImageService imageService;

    public Study getStudyByKey(long studyKey){
        return studyService.getStudyByStudyKey(studyKey);
    }




//API로 만든다
@GetMapping("/study/{studykey}")
public ModelAndView renderStudy(@PathVariable long studykey) {
        ModelAndView modelAndView = new ModelAndView("study/study");
    Study study = getStudyByKey(studykey);
    modelAndView.addObject("study", study);
    return modelAndView;
}

    @ResponseBody
    @GetMapping("/statistics/monthly")
    public ResponseEntity<Map<YearMonth, Long>> getMonthlyPatient() {
        List<Study> studies = studyService.getAllStudies();
        Map<YearMonth, Long> monthlyStatistics = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // Calculate monthly statistics
        for (Study study : studies) {
            String studyDateStr = study.getStudydate();
            LocalDate studyDate = LocalDate.parse(studyDateStr, formatter);
            YearMonth studyYearMonth = YearMonth.from(studyDate);
            monthlyStatistics.put(studyYearMonth, monthlyStatistics.getOrDefault(studyYearMonth, 0L) + 1);
        }

        return ResponseEntity.ok(monthlyStatistics);
    }

    @ResponseBody
    @GetMapping("/statistics/modality")
    public ResponseEntity<Map<String, Long>> getModalityCount() {
        List<Study> studies = studyService.getAllStudies();
        Map<String, Long> modalityCount = new HashMap<>();

        // Calculate modality statistics
        for (Study study : studies) {
            String modality = study.getModality();
            modalityCount.put(modality, modalityCount.getOrDefault(modality, 0L) + 1);
        }

        return ResponseEntity.ok(modalityCount);
    }
}
