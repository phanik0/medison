package com.medison.report.service;

import com.medison.report.domain.Report;
import com.medison.report.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public List<Report> findAll() {
        return reportRepository.findAll();
    }
}
