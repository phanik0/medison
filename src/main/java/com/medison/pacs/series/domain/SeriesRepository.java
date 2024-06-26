package com.medison.pacs.series.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, SeriesId> {

    public Series findByStudykeyAndSerieskey(long studykey, long serieskey);
    public Series findByStudykey(long studykey);
}
