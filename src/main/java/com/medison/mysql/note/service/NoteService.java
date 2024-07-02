package com.medison.mysql.note.service;

import com.medison.mysql.note.domain.Note;
import com.medison.mysql.note.domain.NoteRepository;
import com.medison.mysql.patient.domain.Patient;
import com.medison.mysql.patient.domain.PatientRepository;
import com.medison.mysql.patient.service.PatientService;
import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.domain.ReportRepository;
import com.medison.mysql.report.service.ReportService;
import com.medison.mysql.user.domain.User;
import com.medison.mysql.user.domain.UserRepository;
import com.medison.pacs.study.domain.Study;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
@Service
public class NoteService {
    private final PatientService patientService;
    private final StudyService studyService;
    private final ReportService reportService;

    public Map<String,String> createDemoNote(int studykey) {
        Report report = reportService.getReportByStudyKey(studykey);

        String pCode = report.getPatientCode();
        Patient patient = patientService.findPatientById(pCode);

        Study study = studyService.getStudyByStudyKey(studykey);

        String pName = patient.getName();
        String pSex = patient.getSex();

        String pBirth = patient.getBirth();

        String diseaseHistory = createDiseaseContent(patient);

        String finding = report.getFinding();

        String doctorComment = report.getComments();

        String futureComment = report.getFutureComment();

        String tempFirstDate = study.getStudydate();
        String firstDate = tempFirstDate.substring(0, 4) + "-" + tempFirstDate.substring(4, 6) + "-" + tempFirstDate.substring(6, 8);

        String lastDateTime = report.getModDate().toString();
        String lastDate = lastDateTime.substring(0, 10);

        String treatmentPeriod = calculateTreatmentPeriod(firstDate, lastDate) + "";

        Map<String,String> demoNote = new HashMap<>();
        demoNote.put("pName", pName);
        demoNote.put("pSex", pSex);
        demoNote.put("pBirth", pBirth);
        demoNote.put("diseaseHistory",diseaseHistory);
        demoNote.put("finding", finding);
        demoNote.put("doctorComment", doctorComment);
        demoNote.put("futureComment", futureComment);
        demoNote.put("firstDate", firstDate);
        demoNote.put("lastDate", lastDate);
        demoNote.put("treatmentPeriod", treatmentPeriod);
        return demoNote;
    }

    private String createDiseaseContent(Patient patient) {
        String diseaseHistory = patient.getHistory();
        diseaseHistory += "/신체검사소견 : " + patient.getCaution();
        diseaseHistory += "/" + "흡연유무 : ";
        if (patient.isSmoking())
            diseaseHistory += "유";
        else
            diseaseHistory += "무";
        diseaseHistory += "/" + "음주유무 : ";
        if (patient.isDrinking())
            diseaseHistory += "유";
        else
            diseaseHistory += "무";
        return diseaseHistory;
    }

    private long calculateTreatmentPeriod(String first, String last) {
        Date firstDate = parseDate(first);
        Date lastDate = parseDate(last);
        long diff = lastDate.getTime() - firstDate.getTime();
        long days = diff / (24 * 60 * 60 * 1000);
        return days;
    }

    private Date parseDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
}
