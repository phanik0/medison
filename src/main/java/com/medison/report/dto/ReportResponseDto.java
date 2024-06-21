package com.medison.report.dto;

import com.medison.pacs.study.domain.Study;
import com.medison.report.domain.Report;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class ReportResponseDto {

    private long studykey;
    private String pre_doctor;
    private String doctor;
    private String final_doctor;
    private String patient_code;
    private String existing_status;
    private String status;
    private String comments;
    private String finding;
    private String future_comment;
    private Timestamp reg_date;
    private Timestamp mod_date;

    public ReportResponseDto() {}

    public ReportResponseDto(Report report, Study study) {
        this.studykey = study.getStudykey();
        this.pre_doctor = report.getPre_doctor();
        this.doctor = report.getDoctor();
        this.final_doctor = report.getFinal_doctor();
        this.patient_code = report.getPatient_code();
        this.existing_status = report.getExisting_status();
        this.status = report.getStatus();
        this.comments = report.getComments();
        this.finding = report.getFinding();
        this.future_comment = report.getFuture_comment();
        this.reg_date = report.getReg_date();
        this.mod_date = report.getMod_date();
    }
}
