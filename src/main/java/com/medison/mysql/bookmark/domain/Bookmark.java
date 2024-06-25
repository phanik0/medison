package com.medison.mysql.bookmark.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Getter
@Entity
public class Bookmark {
    @Id
    private int code;
    private int studykey;
    private String userId;
    private String commnets;
    private Timestamp regDate;
}
