package com.medison.pacs.series.service;

import com.medison.pacs.series.domain.Series;
import com.medison.pacs.series.domain.SeriesId;
import com.medison.pacs.series.domain.SeriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SeriesService {
    private final SeriesRepository seriesRepository;

    public Series findSeriesByStudyKeyAndSeriesKey(long studyKey, long seriesKey){
        SeriesId seriesId = new SeriesId(studyKey, seriesKey);
        Optional<Series> series = seriesRepository.findById(seriesId);

        if(series.isEmpty()) {
            System.err.println("Not Found");
        }
        return series.get();
    }
    public List<Series> findSeriesByStudyKey(long studyKey){
        List<Series>list = new ArrayList<Series>();
        for(Series series : seriesRepository.findAll()){
            if(series.getStudykey().equals(studyKey)){
                list.add(series);
            }
        }

        return list;
    }
}
