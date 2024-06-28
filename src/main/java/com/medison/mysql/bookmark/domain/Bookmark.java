package com.medison.mysql.bookmark.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Setter
@Getter
@Entity
@Table(name = "bookmark")
public class Bookmark {
    @Id
    private int code;
    private int studykey;
    private String userId;
    private String comments;
    private Timestamp regDate;
}
