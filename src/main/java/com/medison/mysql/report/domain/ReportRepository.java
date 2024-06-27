package com.medison.mysql.report.domain;

import com.medison.mysql.patient.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    Report findByStudykey(int studykey);
}
