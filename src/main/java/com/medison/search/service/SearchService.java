package com.medison.search.service;

import com.medison.mysql.bookmark.domain.Bookmark;
import com.medison.mysql.bookmark.domain.BookmarkRepository;
import com.medison.pacs.study.domain.Study;
import com.medison.pacs.study.domain.StudyRepository;
import com.medison.mysql.patient.domain.Patient;
import com.medison.mysql.patient.domain.PatientRepository;
import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final StudyRepository studyRepository;
    private final ReportRepository reportRepository;
    private final PatientRepository patientRepository;
    private final BookmarkRepository bookmarkRepository;

    public Page<Map<String, Object>> searchStudies(String patientCode, String patientName, String modality, Long reportStatus, Long examStatus, String startDate, String endDate, Pageable pageable, String userId) {
        List<Specification<Study>> specs = new ArrayList<>();
        if (StringUtils.hasText(patientCode)) {
            specs.add(StudyRepository.hasPatientCode(patientCode));
        }
        if (StringUtils.hasText(patientName)) {
            specs.add(StudyRepository.hasPatientName(patientName));
        }
        if (StringUtils.hasText(modality)) {
            specs.add(StudyRepository.hasModality(modality));
        }
        if (reportStatus != null) {
            specs.add(StudyRepository.hasReportStatus(reportStatus));
        }
        if (examStatus != null) {
            specs.add(StudyRepository.hasExamStatus(examStatus));
        }
        if (StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
            specs.add(StudyRepository.hasStudyDateBetween(startDate, endDate));
        }

        Specification<Study> finalSpec = Specification.where(null);
        for (Specification<Study> spec : specs) {
            finalSpec = finalSpec.and(spec);
        }

        Page<Study> studyPage = studyRepository.findAll(finalSpec, pageable);

        return studyPage.map(study -> {
            Map<String, Object> map = new HashMap<>();
            map.put("study", study);

            Report report = reportRepository.findByStudykey((int) study.getStudykey());
            if (report != null) {
                map.put("status", report.getStatus());
            }

            Bookmark bookmark = bookmarkRepository.findByUserIdAndStudykey(userId, (int)study.getStudykey());
            if (bookmark != null) {
                map.put("bookmark", bookmark);
            }

            return map;
        });
    }

    public Patient getPatientByCode(String code) {
        return patientRepository.findById(code).orElse(null);
    }
}
