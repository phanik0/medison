package com.medison.mysql.report.service;

import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public Report getReportByStudyKey(int studykey) {
        return reportRepository.findById(studykey).orElse(null);
    }

    public void saveReport(Report report) {
        reportRepository.save(report);
    }
}
