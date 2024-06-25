package com.medison.search.controller;

import com.medison.detail.domain.Study;
import com.medison.search.service.SearchService;
import com.medison.patient.domain.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            Patient patient = searchService.getPatientByCode(Integer.parseInt(patientCode));
            if (patient != null) {
                model.addAttribute("patient", patient);
            }
        }

        return "main"; // src/main/webapp/WEB-INF/jsp/main.jsp 파일을 렌더링
    }

    @GetMapping("/patient")
    @ResponseBody
    public Patient getPatient(@RequestParam String pid) {
        return searchService.getPatientByCode(Integer.parseInt(pid));
    }
}
