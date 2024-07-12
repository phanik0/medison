package com.medison.mysql.readLog.domain;

import com.medison.mysql.utill.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@NoArgsConstructor
@Getter
@Setter
public class ReadLogResponseDto{
    private int logCode;
    private String userName;
    private String userPhone;
    private String userBirth;
    private String userAddress;
    private String department;
    private String position;
    private int studykey;
    private Timestamp regDate;
    private Timestamp modDate;

    public ReadLogResponseDto(ReadLog readLog) {
        this.logCode = readLog.getLogCode();
        this.userName = readLog.getUserName();
        this.userPhone = readLog.getUserPhone();
        this.userBirth = readLog.getUserBirth();
        this.userAddress = readLog.getUserAddress();
        this.department = readLog.getDepartment();
        this.position = readLog.getPosition();
        this.studykey = readLog.getStudykey();
        this.regDate = readLog.getRegDate();
        this.modDate = readLog.getModDate();
    }
}
