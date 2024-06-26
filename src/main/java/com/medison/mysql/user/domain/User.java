package com.medison.mysql.user.domain;

import com.medison.mysql.user.dto.UserRequestDto;
import com.medison.mysql.utill.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "user")
@Entity
public class User extends Timestamped {
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

    public void create(UserRequestDto userRequestDto) {
        this.id = userRequestDto.getId();
        this.departmentCode = userRequestDto.getDepartmentCode();
        this.password = userRequestDto.getPassword();
        this.name = userRequestDto.getName();
        this.position = userRequestDto.getPosition();
        this.admin = userRequestDto.isAdmin();
        this.phone = userRequestDto.getPhone();
        this.birth = userRequestDto.getBirth();
        this.address = userRequestDto.getAddress();
    }

    public void updateByTheUser(UserRequestDto userRequestDto) {
        this.password = userRequestDto.getPassword();
    }
}
