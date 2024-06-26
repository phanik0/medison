package com.medison.mysql.note.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "NOTE")
@Entity
public class Note {

    @Id
    private int code;
    private long studykey;
    private String finalDoctor;
    private String patientCode;
    private String disease;
    private String treatmentPeriod;
    private String finding;
    private String comments;
    private String futureComment;
    @Column(name = "`usage`")
    private String usage;
    private Timestamp regDate;
    private Timestamp modDate;

//    public void create(ReportRequestDto reportDto) {
//        this.code = reportDto.getCode();
//        this.studykey = reportDto.getStudykey();
//        this.finalDoctor = reportDto.getFinalDoctor();
//        this.patientCode = reportDto.getPatientCode();
//        this.disease = reportDto.getDisease();
//        this.treatmentPeriod = reportDto.getTreatmentPeriod();
//        this.finding = reportDto.getFinding();
//        this.comments = reportDto.getComments();
//        this.futureComment = reportDto.getFutureComment();
//        this.usage = reportDto.getUsage();
//        this.regDate = reportDto.getRegDate();
//        this.modDate = reportDto.getModDate();
//    }

}
