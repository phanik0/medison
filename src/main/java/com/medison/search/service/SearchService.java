package com.medison.search.service;

import com.medison.detail.domain.Study;
import com.medison.detail.domain.StudyRepository;
import com.medison.mysql.patient.domain.Patient;
import com.medison.mysql.patient.domain.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchService {
    private final StudyRepository studyRepository;
    private final PatientRepository patientRepository;

    public Page<Study> searchStudies(String patientCode, String patientName, String modality, Long reportStatus, Long examStatus, Pageable pageable) {
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

        Specification<Study> finalSpec = Specification.where(null);
        for (Specification<Study> spec : specs) {
            finalSpec = finalSpec.and(spec);
        }

        return studyRepository.findAll(finalSpec, pageable);
    }

    public Patient getPatientByCode(String code) {
        return patientRepository.findById(code).orElse(null);
    }

}
