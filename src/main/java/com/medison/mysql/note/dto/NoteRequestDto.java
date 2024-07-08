package com.medison.mysql.note.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteRequestDto {
    private int studykey;
    private int status;
    private String finalDoctor;
    private String patientCode;
    private String disease;
    private String treatmentPeriod;
    private String finding;
    private String comments;
    private String futureComment;
    private String remark;
}
