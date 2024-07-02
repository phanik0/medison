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
    private int studykey;

    public void save(User user,int studykey) {
        this.userName = user.getName();
        this.userPhone = user.getPhone();
        this.userBirth = user.getBirth();
        this.userAddress = user.getAddress();
        this.studykey = studykey;
    }
}
