package com.medison.mysql.note.domain;

import com.medison.mysql.user.domain.User;
import com.medison.mysql.utill.Timestamped;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Table(name = "note")
@Entity
public class Note extends Timestamped {
    @Id
    private int code;
    private int studykey;
    private int status;
    private String finalDoctor;
    private String patientCode;
    private String disease;
    private String treatmentPeriod;
    private String finding;
    private String comment;
    private String futureComment;
    private String usage;
}
