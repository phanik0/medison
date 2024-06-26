package com.medison.mysql.user.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Table(name = "user")
@Entity
public class User {
    @Id
    private String id;
    private int departmentCode;
    private String password;
    private String name;
    private String position;
    private boolean admin;
    private String phone;
    private String birth;
    private String address;
    private Timestamp regDate;
    private Timestamp modDate;
}
