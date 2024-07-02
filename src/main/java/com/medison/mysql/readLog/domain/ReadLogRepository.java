package com.medison.mysql.readLog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadLogRepository extends JpaRepository<ReadLog, Integer> {
}
