package com.medison.mysql.patient.domain;

import lombok.Getter;
import org.apache.tomcat.jni.Time;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Table(name = "patient")
@Entity
public class Patient {
    @Id
    private String code;
    private String name;
    private String sex;
    private String birth;
    private boolean smoking;
    private boolean drinking;
    private String history;
    private String caution;
    private Timestamp regDate;
    private Timestamp modDate;
}