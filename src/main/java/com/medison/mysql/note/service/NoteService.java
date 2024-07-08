package com.medison.mysql.note.service;

import com.medison.mysql.note.domain.Note;
import com.medison.mysql.note.domain.NoteRepository;
import com.medison.mysql.note.dto.NoteRequestDto;
import com.medison.mysql.patient.domain.Patient;
import com.medison.mysql.patient.service.PatientService;
import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.service.ReportService;
import com.medison.mysql.user.domain.User;
import com.medison.mysql.user.service.UserService;
import com.medison.pacs.study.domain.Study;
import com.medison.pacs.study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NoteService {
    private final PatientService patientService;
    private final StudyService studyService;
    private final ReportService reportService;
    private final NoteRepository noteRepository;
    private final UserService userService;

    public Map<String, String> createDemoNote(int studykey) {
        Note existingNote = noteRepository.findByStudykey(studykey);

        if (existingNote != null) {
            return convertNoteToMap(existingNote);
        }

        Report report = reportService.getReportByStudyKey(studykey);
        String finalDoctor = report.getFinalDoctor();

        User user = userService.getUserById(finalDoctor);
        String finalDoctorName = user.getName();

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
        String remark = null;

        Map<String, String> demoNote = new HashMap<>();
        demoNote.put("studykey", String.valueOf(studykey));
        demoNote.put("finalDoctor", finalDoctor);
        demoNote.put("finalDoctorName", finalDoctorName);
        demoNote.put("pName", pName);
        demoNote.put("pSex", pSex);
        demoNote.put("pBirth", pBirth);
        demoNote.put("diseaseHistory", diseaseHistory);
        demoNote.put("finding", finding);
        demoNote.put("doctorComment", doctorComment);
        demoNote.put("futureComment", futureComment);
        demoNote.put("firstDate", firstDate);
        demoNote.put("lastDate", lastDate);
        demoNote.put("treatmentPeriod", treatmentPeriod);
        demoNote.put("patientCode", pCode);
        demoNote.put("remark", remark);
        return demoNote;
    }

    public Map<String, String> convertNoteToMap(Note note) {
        Study study = studyService.getStudyByStudyKey(note.getStudykey());
        Report report = reportService.getReportByStudyKey(note.getStudykey());

        Patient patient = patientService.findPatientById(note.getPatientCode());
        String diseaseHistory = createDiseaseContent(patient);

        String tempFirstDate = study.getStudydate();
        String firstDate = tempFirstDate.substring(0, 4) + "-" + tempFirstDate.substring(4, 6) + "-" + tempFirstDate.substring(6, 8);

        String lastDateTime = report.getModDate().toString();
        String lastDate = lastDateTime.substring(0, 10);

        String treatmentPeriod = calculateTreatmentPeriod(firstDate, lastDate) + "";

        Map<String, String> noteMap = new HashMap<>();
        noteMap.put("studykey", String.valueOf(note.getStudykey()));
        noteMap.put("finalDoctor", note.getFinalDoctor());
        noteMap.put("finalDoctorName", userService.getUserById(note.getFinalDoctor()).getName());
        noteMap.put("pName", patientService.findPatientById(note.getPatientCode()).getName());
        noteMap.put("pSex", patientService.findPatientById(note.getPatientCode()).getSex());
        noteMap.put("pBirth", patientService.findPatientById(note.getPatientCode()).getBirth());
        noteMap.put("disease", note.getDisease());
        noteMap.put("diseaseHistory", diseaseHistory);
        noteMap.put("finding", note.getFinding());
        noteMap.put("doctorComment", note.getComments());
        noteMap.put("futureComment", note.getFutureComment());
        noteMap.put("firstDate", firstDate);
        noteMap.put("lastDate", lastDate);
        noteMap.put("treatmentPeriod", treatmentPeriod);
        noteMap.put("patientCode", note.getPatientCode());
        noteMap.put("status", String.valueOf(note.getStatus()));
        noteMap.put("remark", note.getRemark());
        return noteMap;
    }

    private String createDiseaseContent(Patient patient) {
        StringBuilder diseaseHistory = new StringBuilder();
        diseaseHistory.append("병력 : ").append(patient.getHistory()).append("\n");
        diseaseHistory.append("신체검사소견 : ").append(patient.getCaution()).append("\n");
        diseaseHistory.append("흡연유무 : ");
        if (patient.isSmoking()) {
            diseaseHistory.append("유");
        } else {
            diseaseHistory.append("무");
        }
        diseaseHistory.append("\n음주유무 : ");
        if (patient.isDrinking()) {
            diseaseHistory.append("유");
        } else {
            diseaseHistory.append("무");
        }
        return diseaseHistory.toString();
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

    @Transactional
    public void saveNote(NoteRequestDto noteRequest) {
        Note existingNote = noteRepository.findByStudykey(noteRequest.getStudykey());

        if (existingNote != null) {
            Report report = reportService.getReportByStudyKey(noteRequest.getStudykey());
            existingNote.setPatientCode(report.getPatientCode());
            existingNote.setFinalDoctor(report.getFinalDoctor());
            existingNote.setStudykey(report.getStudykey());
            // 기존 노트가 있을 경우 업데이트
            existingNote.setStatus(noteRequest.getStatus());
            existingNote.setDisease(noteRequest.getDisease());
            existingNote.setTreatmentPeriod(noteRequest.getTreatmentPeriod());
            existingNote.setFinding(noteRequest.getFinding());
            existingNote.setComments(noteRequest.getComments());
            existingNote.setFutureComment(noteRequest.getFutureComment());
            existingNote.setRemark(noteRequest.getRemark());
            noteRepository.save(existingNote);
        } else {
            // 새로운 노트를 저장
            Note newNote = new Note();
            Report report = reportService.getReportByStudyKey(noteRequest.getStudykey());
            newNote.setPatientCode(report.getPatientCode());
            newNote.setFinalDoctor(report.getFinalDoctor());
            newNote.setStudykey(report.getStudykey());
            newNote.setStatus(noteRequest.getStatus());
            newNote.setDisease(noteRequest.getDisease());
            newNote.setTreatmentPeriod(noteRequest.getTreatmentPeriod());
            newNote.setFinding(noteRequest.getFinding());
            newNote.setComments(noteRequest.getComments());
            newNote.setFutureComment(noteRequest.getFutureComment());
            newNote.setRemark(noteRequest.getRemark());
            noteRepository.save(newNote);
        }
    }

    public Note getNoteByStudykey(int studykey) {
        return noteRepository.findByStudykey(studykey);
    }
}
