package com.medison.pacs.image.domain;


import com.medison.pacs.series.domain.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long> {
    public List<Image> findByStudykey(long studykey);
    public List<Image> findByStudykey(String title);
    public Image findByStudykeyAndSerieskey(long studykey, long serieskey);
}
