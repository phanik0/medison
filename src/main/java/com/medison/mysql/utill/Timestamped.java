package com.medison.mysql.utill;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;

@Getter
@MappedSuperclass   // 나(부모)를 상속받은 자식 클래스에게 컬럼을 부여하게 됨
@EntityListeners(AuditingEntityListener.class)
public class Timestamped {
    @CreatedDate
    private Timestamp regDate;
    @LastModifiedDate
    private Timestamp modDate;
}
