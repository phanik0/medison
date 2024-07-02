package com.medison.mysql.emergency.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Table(name = "emergency_management")
@Entity
public class Emergency {
    @Id
    private int studykey;
    private int status;
    private String studydate;
    private String studytime;
    private boolean emergency;

    public Emergency(int studykey, int status, String studydate, String studytime, boolean emergency) {
        this.studykey = studykey;
        this.status = status;
        this.studydate = studydate;
        this.studytime = studytime;
        this.emergency = emergency;
    }
}
