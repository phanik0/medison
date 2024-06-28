package com.medison.mysql.note.service;

import com.medison.mysql.note.domain.Note;
import com.medison.mysql.note.domain.NoteRepository;
import com.medison.mysql.patient.domain.Patient;
import com.medison.mysql.patient.domain.PatientRepository;
import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.domain.ReportRepository;
import com.medison.mysql.user.domain.User;
import com.medison.mysql.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Note createNote(Note note) {
        Long studykey = note.getStudykey();
        int studykeyInt = studykey.intValue();
        Report report = reportRepository.findByStudykey(studykeyInt);

        if (report != null) {
            note.setStudykey(studykey);
            note.setFinalDoctor(note.getFinalDoctor());
            note.setPatientCode(String.valueOf(report.getPatientCode()));
            note.setDisease(note.getDisease());
            note.setTreatmentPeriod(note.getTreatmentPeriod());
            note.setComments(report.getComments());
            note.setFinding(report.getFinding());
            note.setFutureComment(report.getFutureComment());
            note.setUsage(note.getUsage());
            note.setRegDate(new Timestamp(System.currentTimeMillis()));
            note.setModDate(new Timestamp(System.currentTimeMillis()));

            return noteRepository.save(note);
        } else {
            throw new IllegalArgumentException("해당 studykey에 해당하는 Report가 존재하지 않습니다: " + studykey);
        }
    }

    public Note updateNote(Note note) {
        Note existingNote  = noteRepository.findByStudykey(note.getStudykey());

        if (existingNote != null) {
            // finalDoctor, treatmentPeriod, comments, finding, futurecomment, usage, modDate
            existingNote.setFinalDoctor(note.getFinalDoctor());
            existingNote.setTreatmentPeriod(note.getTreatmentPeriod());
            existingNote.setComments(note.getComments());
            existingNote.setFinding(note.getFinding());
            existingNote.setFutureComment(note.getFutureComment());
            existingNote.setUsage(note.getUsage());
            existingNote.setModDate(new Timestamp(new Date().getTime()));
//            existingNote.setModDate((Timestamp) new Date());

            return noteRepository.save(existingNote);
        } else {
            throw new IllegalArgumentException("Note with studykey " + note.getStudykey() + " not found");
        }
    }

    public Note getNoteByStudykey(Long studykey) {
//        return noteRepository.findById(studykey).orElse(null);
        return noteRepository.findByStudykey(studykey);
    }

    public Note createOrUpdateNote(Note note) {
        return noteRepository.save(note);
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    public Note findByStudykey(Long studykey) {
        return noteRepository.findByStudykey(studykey);
    }
}
