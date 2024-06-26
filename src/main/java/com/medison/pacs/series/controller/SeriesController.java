package com.medison.pacs.series.controller;

import com.medison.pacs.series.domain.Series;
import com.medison.pacs.series.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class SeriesController {
    private final SeriesService seriesService;


    public List<Series> getSeriesByStudyKey(long studyKey) {
        return seriesService.findSeriesByStudyKey(studyKey);
    }
}
