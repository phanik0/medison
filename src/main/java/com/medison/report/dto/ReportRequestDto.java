package com.medison.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
public class ReportRequestDto {

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
