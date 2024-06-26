package com.medison.pacs.image.service;

import com.medison.pacs.image.domain.Image;
import com.medison.pacs.image.domain.ImageRepository;
import com.medison.pacs.series.domain.Series;
import com.medison.pacs.series.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public List<Image> findByStudykey(long studykey){
        return imageRepository.findByStudykey(studykey);
    };
    private final SeriesService seriesService;
    public Image findImageByStudyKeyAndSeriesKey(long studyKey, long seriesKey) {
        return imageRepository.findByStudykeyAndSerieskey(studyKey, seriesKey);
    }
    public List<Image> findImagesByStudyKey(long studyKey) {
        List<Series> seriesList = seriesService.findSeriesByStudyKey(studyKey);
        List<Image> imageList = new ArrayList<>();
        for (Image image : imageRepository.findAll()) {
            for (Series series : seriesList) {
                if (image.getStudykey() == series.getStudykey() && image.getSerieskey()==series.getSerieskey()) {
                    imageList.add(image);
                }
            }
        }
        return imageList;
    }
}
