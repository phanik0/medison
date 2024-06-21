package com.medison.report.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Table(name = "REPORT", schema = "newuser2")
@Entity
public class Report {

    @Id
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
}
