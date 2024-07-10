package com.medison.mysql.emergency.service;

import com.medison.mysql.emergency.domain.Emergency;
import com.medison.mysql.emergency.domain.EmergencyRepository;
import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.service.ReportService;
import com.medison.pacs.study.domain.Study;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class EmergencyService {
    private final int NON_READ = 3;

    private final EmergencyRepository emergencyRepository;
    private final StudyService studyService;
    private final ReportService reportService;

    @Transactional
    public List<Emergency> createEmergency() {
        emergencyRepository.truncateTable();
        List<Study> studies = studyService.getAllStudy();

        List<Emergency> emergencies = checkAndCreate(studies);
        return emergencies;
    }

    private List<Emergency> checkAndCreate(List<Study> studies) {
        List<Emergency> emergencies = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        for (Study study : studies) {
            try {
                int studykey = (int) study.getStudykey();
                Report report = reportService.getReportByStudyKey(studykey);
                String studyDateAndTime = study.getStudydate() + study.getStudytime();
                Date target = sdf.parse(studyDateAndTime);
                long diff = now.getTime() - target.getTime();
                long diffHours = diff / (1000 * 60 * 60);
                boolean emergency = false;
                if (diffHours >= 24 && report.getStatus() == NON_READ) {
                    emergency = true;
                    Emergency temp = new Emergency(studykey, report.getStatus(), study.getStudydate(), study.getStudytime(), emergency);
                    emergencies.add(temp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        emergencyRepository.saveAll(emergencies);
        return emergencies;
    }

    public List<Map<String, Object>> getEmergencyStudies() {
        List<Map<String, Object>> emergencyStudies = new ArrayList<>();
        List<Emergency> emergencies = emergencyRepository.findAll();

        for (Emergency emergency : emergencies) {
            int studykey = emergency.getStudykey();
            Report report = reportService.getReportByStudyKey(studykey);
            int status = report.getStatus();
            Study targetStudy = studyService.getStudyByStudyKey(studykey);
            Map<String, Object> emergencyStudy = new HashMap<>();
            emergencyStudy.put("study", targetStudy);
            emergencyStudy.put("status", status);
            emergencyStudies.add(emergencyStudy);
        }

        return emergencyStudies;
    }
}
