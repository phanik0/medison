package com.medison.mysql.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {
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
