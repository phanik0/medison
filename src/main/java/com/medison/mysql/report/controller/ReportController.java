package com.medison.mysql.report.controller;

import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.service.ReportService;
import com.medison.mysql.user.domain.User;
import com.medison.mysql.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ReportController {
    private final ReportService reportService;
    private final UserService userService;

    @GetMapping("/report")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getReport(@RequestParam(defaultValue = "0") int studykey) {
        if (studykey == 0) {
            return ResponseEntity.badRequest().build();
        }
        Report report = reportService.getReportByStudyKey(studykey);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("report", report);

        // preDoctor와 finalDoctor의 사용자 정보를 가져와 추가합니다.
        if (report.getPreDoctor() != null) {
            User preDoctor = userService.getUserById(report.getPreDoctor());
            if (preDoctor != null) {
                response.put("preDoctorName", preDoctor.getName());
                response.put("preDoctorPosition", preDoctor.getPosition());
                response.put("preDoctorDepartmentCode", preDoctor.getDepartmentCode());
            }
        }

        if (report.getFinalDoctor() != null) {
            User finalDoctor = userService.getUserById(report.getFinalDoctor());
            if (finalDoctor != null) {
                response.put("finalDoctorName", finalDoctor.getName());
                response.put("finalDoctorPosition", finalDoctor.getPosition());
                response.put("finalDoctorDepartmentCode", finalDoctor.getDepartmentCode());
            }
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/report/preliminary")
    @ResponseBody
    public ResponseEntity<String> savePreliminaryReport(@RequestBody Report report) {
        try {
            Report existingReport = reportService.getReportByStudyKey(report.getStudykey());
            if (existingReport != null) {
                existingReport.setStatus(report.getStatus());
                existingReport.setComments(report.getComments());
                existingReport.setFinding(report.getFinding());
                existingReport.setFutureComment(report.getFutureComment());
                existingReport.setPreDoctor(report.getPreDoctor());
                existingReport.setRegDate(report.getRegDate());
                existingReport.setModDate(report.getModDate());
                reportService.saveReport(existingReport);
            } else {
                reportService.saveReport(report);
            }
            return ResponseEntity.ok("예비판독이 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @PostMapping("/report/final")
    @ResponseBody
    public ResponseEntity<String> saveFinalReport(@RequestBody Report report) {
        try {
            // 백엔드 검증 추가
            if (report.getFinding() == null || report.getFinding().isEmpty() ||
                    report.getFutureComment() == null || report.getFutureComment().isEmpty()) {
                return ResponseEntity.badRequest().body(" 검사 소견 및 향후 치료 의견을 모두 입력해 주세요.");
            }

            Report existingReport = reportService.getReportByStudyKey(report.getStudykey());
            if (existingReport != null) {
                existingReport.setStatus(report.getStatus());
                existingReport.setComments(report.getComments());
                existingReport.setFinding(report.getFinding());
                existingReport.setFutureComment(report.getFutureComment());
                existingReport.setPreDoctor(report.getPreDoctor());
                existingReport.setFinalDoctor(report.getFinalDoctor());
                existingReport.setRegDate(report.getRegDate());
                existingReport.setModDate(report.getModDate());
                reportService.saveReport(existingReport);
            } else {
                reportService.saveReport(report);
            }
            return ResponseEntity.ok("최종판독이 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
