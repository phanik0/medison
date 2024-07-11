package com.medison.mysql.readLog.domain;

import com.medison.mysql.user.domain.User;
import com.medison.mysql.utill.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Table(name = "read_log")
@Entity
public class ReadLog extends Timestamped {
    @Id
    private int logCode;
    private String userName;
    private String userPhone;
    private String userBirth;
    private String userAddress;
    private String department;
    private String position;
    private int studykey;

    public void save(String userName,String userPhone,String userBirth,String userAddress,String department,String position,int studykey) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userBirth = userBirth;
        this.userAddress = userAddress;
        this.department = department;
        this.position = position;
        this.studykey = studykey;
    }
}
