package com.medison.mysql.report.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Setter
@Getter
@Table(name="report")
@Entity
public class Report {
    @Id
    private int studykey;
    private String preDoctor;
    private String doctor;
    private String finalDoctor;
    private String patientCode;
    private int existingStatus;
    private int status;
    private String comments;
    private String finding;
    private String futureComment;
    private Timestamp regDate;
    private Timestamp modDate;
}
