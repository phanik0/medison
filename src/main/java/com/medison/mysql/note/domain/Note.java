package com.medison.mysql.note.domain;

import com.medison.mysql.user.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "note")
@Entity
public class Note {

    @Id
    private int code;
    private Long studykey;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "final_doctor", referencedColumnName = "id")
//    private User finalDoctor;
    @Column(name = "final_doctor")
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

//    private String patientName;
//    private String birth;
//    private String history;
//    private String caution;

//    public void create(NoteRequestDto noteDto) {
//        this.code = noteDto.getCode();
//        this.studykey = noteDto.getStudykey();
//        this.finalDoctor = noteDto.getFinalDoctor();
//        this.patientCode = noteDto.getPatientCode();
//        this.disease = noteDto.getDisease();
//        this.treatmentPeriod = noteDto.getTreatmentPeriod();
//        this.finding = noteDto.getFinding();
//        this.comments = noteDto.getComments();
//        this.futureComment = noteDto.getFutureComment();
//        this.usage = noteDto.getUsage();
//        this.regDate = noteDto.getRegDate();
//        this.modDate = noteDto.getModDate();
//    }

}
