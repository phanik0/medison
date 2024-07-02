package com.medison.search.controller;

import com.medison.mysql.emergency.service.EmergencyService;
import com.medison.mysql.patient.service.PatientService;
import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.service.ReportService;
import com.medison.mysql.user.domain.User;
import com.medison.mysql.user.service.UserService;
import com.medison.pacs.study.domain.Study;
import com.medison.search.service.SearchService;
import com.medison.mysql.patient.domain.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class SearchController {
    private final SearchService searchService;
    private final ReportService reportService;
    private final PatientService patientService;
    private final UserService userService;
    private final EmergencyService emergencyService;

    @GetMapping("/main")
    public String getAllStudies(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false) String patientCode,
                                @RequestParam(required = false) String patientName,
                                @RequestParam(required = false) String modality,
                                @RequestParam(required = false) Long reportStatus,
                                @RequestParam(required = false) Long examStatus,
                                @RequestParam(required = false) String startDate,
                                @RequestParam(required = false) String endDate) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Map<String, Object>> studyPage = searchService.searchStudies(patientCode, patientName, modality, reportStatus, examStatus, startDate, endDate, pageable);

        model.addAttribute("studies", studyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studyPage.getTotalPages());

        // 추가된 검색 파라미터 모델에 추가
        model.addAttribute("patientCode", patientCode);
        model.addAttribute("patientName", patientName);
        model.addAttribute("modality", modality);
        model.addAttribute("reportStatus", reportStatus);
        model.addAttribute("examStatus", examStatus);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        if (patientCode != null && !patientCode.isEmpty()) {
            Patient patient = searchService.getPatientByCode(patientCode);
            if (patient != null) {
                model.addAttribute("patient", patient);
            }
        }
        return "main";
    }

    @GetMapping("/patient")
    @ResponseBody
    public ResponseEntity<Patient> getPatient(@RequestParam(required = false) String pid) {
        if (pid == null || pid.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Patient patient = searchService.getPatientByCode(pid);
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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

    @PostMapping("/patient/edit")
    @ResponseBody
    public ResponseEntity<String> savePatientDetails(@RequestBody Patient patient) {
        try {
            patientService.savePatient(patient);
            return ResponseEntity.ok("환자 정보가 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}