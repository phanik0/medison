package com.medison.pacs.image.controller;


import com.medison.pacs.image.domain.Image;
import com.medison.pacs.image.service.ImageService;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {
    private final ImageService imageService;
    public Image getImageByStudyKeyAndSeriesKey(long studyKey, long seriesKey) {
        return imageService.findImageByStudyKeyAndSeriesKey(studyKey, seriesKey);
    }
}
