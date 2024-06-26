package com.medison.pacs.study.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StudyResponseDto {
    private long studykey;
    private String pid;
    private String pname;
    private String modality;
    private String studydesc;
    private String studydate;
    private long reportstatus;
    private long seriescnt;
    private long imagecnt;
    private long examstatus;

    public StudyResponseDto(Study study) {
        this.studykey = study.getStudykey();
        this.pid = study.getPid();
        this.pname = study.getPname();
        this.modality = study.getModality();
        this.studydesc = study.getStudydesc();
        this.studydate = study.getStudydate();
        this.reportstatus = study.getReportstatus();
        this.seriescnt = study.getSeriescnt();
        this.imagecnt = study.getImagecnt();
        this.examstatus = study.getExamstatus();
    }
}
