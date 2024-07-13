package com.medison.mysql.readLog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadLogRepository extends JpaRepository<ReadLog, Integer> {
    public boolean existsByStudykeyAndUserNameAndUserPhoneAndUserBirth(int studykey, String userName, String userPhone, String userBirth);
    public List<ReadLog> findByLogCodeGreaterThanOrderByRegDateDesc(int logCode);
}
