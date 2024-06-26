package com.medison.pacs.series.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SeriesId implements Serializable {
    private long studykey;	//number(38,0)
    private long serieskey;//	number(38,0)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeriesId seriesId = (SeriesId) o;
        return studykey == seriesId.studykey && serieskey == seriesId.serieskey;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studykey, serieskey);
    }
}
