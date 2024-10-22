package com.medison.mysql.report.service;

import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public Report getReportByStudyKey(int studykey) {
       return reportRepository.findById(studykey).orElseThrow(
               () -> new IllegalArgumentException("존재하지 않는 리포트입니다.")
       );
    }

    public void saveReport(Report report) {
        reportRepository.save(report);
    }

    public Report getReportByFinalDoctor(String finalDoctor) {
        Optional<Report> reportOptional = reportRepository.findByFinalDoctor(finalDoctor);
        return reportOptional.orElse(null);
    }
}
