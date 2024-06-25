package com.medison.search.controller;

import com.medison.detail.domain.Study;
import com.medison.search.service.SearchService;
import com.medison.mysql.patient.domain.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/main")
    public String getAllStudies(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(required = false) String patientCode,
                                @RequestParam(required = false) String patientName,
                                @RequestParam(required = false) String modality,
                                @RequestParam(required = false) Long reportStatus,
                                @RequestParam(required = false) Long examStatus) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Study> studyPage = searchService.searchStudies(patientCode, patientName, modality, reportStatus, examStatus, pageable);

        model.addAttribute("studies", studyPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studyPage.getTotalPages());

        if (patientCode != null && !patientCode.isEmpty()) {
            Patient patient = searchService.getPatientByCode(patientCode);
            if (patient != null) {
                model.addAttribute("patient", patient);
            }
        }

        return "main"; // src/main/webapp/WEB-INF/jsp/main.jsp 파일을 렌더링
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
}
