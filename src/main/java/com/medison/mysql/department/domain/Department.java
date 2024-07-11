package com.medison.mysql.department.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "department")
public class Department {
    @Id
    private int code;
    private String name;
}
