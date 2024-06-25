package com.medison.patient.domain;

import lombok.Getter;
import org.apache.tomcat.jni.Time;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Table(name = "PATIENT")
@Entity
public class Patient {
    @Id
    private int code;
    private String name;
    private String sex;
    private String birth;
    private boolean smoking;
    private boolean drinking;
    private String medicalHistory;
    private String coaution;
    private Timestamp regDate;
    private Timestamp modDate;
}
