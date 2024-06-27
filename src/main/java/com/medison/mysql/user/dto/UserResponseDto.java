package com.medison.mysql.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medison.mysql.user.ActionType;
import com.medison.mysql.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;



@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
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

    public UserResponseDto(User user, ActionType action){
        if(action == ActionType.SAVE){
            this.id = user.getId();
            this.password = user.getPassword();
        }else if(action == ActionType.VIEW){
            this.id = user.getId();
            this.position = user.getPosition();
            this.admin = user.isAdmin();
            this.name = user.getName();
            this.phone = user.getPhone();
        }else if(action == ActionType.LOGIN){
            this.id = user.getId();
            this.name = user.getName();
            this.position = user.getPosition();
            this.admin = user.isAdmin();
            this.departmentCode = user.getDepartmentCode();
        }
    }
}