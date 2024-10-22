package com.medison.pacs.image.controller;


import com.medison.pacs.image.domain.Image;
import com.medison.pacs.image.service.ImageService;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;
    public List<Image> getImageByStudyKeyAndSeriesKey(long studyKey, long seriesKey) {
        return imageService.findImageByStudyKeyAndSeriesKey(studyKey, seriesKey);
    }
    @ResponseBody
    @GetMapping("/images/{studykey}")
    public List<ArrayList<Image>> renderImage(@PathVariable long studykey){
        return imageService.findImagesByStudyKey(studykey);
    }
//
//
//    @ResponseBody
//    @GetMapping("/images/{studykey}")
//    public List<Image> getImagesByStudykey(@PathVariable("studykey") String studykey) {
//        long skey = Long.parseLong(studykey);
//
//        return imageService.findByStudykey(skey);
//    }
}
