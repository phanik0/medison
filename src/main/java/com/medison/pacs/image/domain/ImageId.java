package com.medison.pacs.image.domain;

import java.io.Serializable;
import java.util.Objects;

public class ImageId implements Serializable {
    private long studykey;
    private long serieskey;
    private long imagekey;

    // 기본 생성자
    public ImageId() {}

    // 필드 초기화하는 생성자
    public ImageId(long studykey, long serieskey, long imagekey) {
        this.studykey = studykey;
        this.serieskey = serieskey;
        this.imagekey = imagekey;
    }

    // equals 및 hashCode 메서드 재정의
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageId imageId = (ImageId) o;
        return studykey == imageId.studykey &&
                serieskey == imageId.serieskey &&
                imagekey == imageId.imagekey;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studykey, serieskey, imagekey);
    }
}
