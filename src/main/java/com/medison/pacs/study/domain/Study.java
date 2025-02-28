package com.medison.pacs.study.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Table(name = "STUDYTAB", schema = "PACSPLUS")
@Entity
public class Study {
    @Id
    private long studykey;
    private String pid;
    private String pname;
    private String modality;
    private String studydesc;
    private String studydate;
    private String studytime;
    private long reportstatus;
    private long seriescnt;
    private long imagecnt;
    private long examstatus;
    private Float aiScore;
}
