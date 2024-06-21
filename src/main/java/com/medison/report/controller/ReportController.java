package com.medison.report.controller;

import com.medison.report.domain.Report;
import com.medison.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReportController {

    private final ReportService reportService;

    public ResponseEntity<List<Report>> getReports() {
        List<Report> result = reportService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
