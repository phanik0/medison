package com.medison.mysql.note.service;

import com.medison.mysql.note.domain.Note;
import com.medison.mysql.note.domain.NoteRepository;
import com.medison.mysql.patient.domain.PatientRepository;
import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.domain.ReportRepository;
import com.medison.mysql.user.domain.User;
import com.medison.mysql.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

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
//        Optional<User> user = userRepository.findById(String.valueOf(studykeyInt));

        if (report != null) {
            note.setStudykey(studykey);
            note.setFinalDoctor(note.getFinalDoctor());
//            User doctor = userRepository.findById(note.getFinalDoctor().getId())
//                    .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 의사가 없습니다"));
//            note.setFinalDoctor(doctor);
//            note.setFinalDoctor(userRepository.findById(note.getFinalDoctor().getId())
//                    .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 의사가 없습니다")));
//            note.setFinalDoctor(String.valueOf(note.getFinalDoctor()));
//            note.setFinalDoctor(userRepository.findById(String.valueOf(note.getFinalDoctor()))
//                    .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 의사가 없습니다")));
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
        // comments, finding, futurecomment, modDate
        return noteRepository.save(note);
    }

    public Note getNoteByStudykey(Long studykey) {
//        return noteRepository.findById(studykey).orElse(null);
        return noteRepository.findByStudykey(studykey);
    }

}
