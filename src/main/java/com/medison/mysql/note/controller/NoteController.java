package com.medison.mysql.note.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.medison.mysql.note.domain.Note;
import com.medison.mysql.note.service.NoteService;
import com.medison.mysql.patient.domain.Patient;
import com.medison.mysql.patient.service.PatientService;
import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@RequiredArgsConstructor
@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final ReportService reportService;
    private final PatientService patientService;

    // 노트를 불러오는 메서드
    public Note getNoteByStudykey(@PathVariable Long studykey) {
        return noteService.findByStudykey(studykey);
    }

    @ResponseBody
    @PostMapping("/{studykey}")
    public String createOrUpdateNote(@PathVariable Long studykey, @RequestBody Note note, Model model) {
        Note existingNote = noteService.getNoteByStudykey(studykey);

        if (existingNote == null) {
            existingNote = createNote(studykey);
        } else {
//            updateNote(existingNote, studykey);
            existingNote = updateNote(existingNote, studykey);
        }

        // 수정할 내용 업데이트
        existingNote.setFinalDoctor(note.getFinalDoctor());
        existingNote.setDisease(note.getDisease());
        existingNote.setTreatmentPeriod(note.getTreatmentPeriod());
        existingNote.setUsage(note.getUsage());
        existingNote.setModDate(new Timestamp(new Date().getTime()));

        // 저장
        noteService.saveNote(existingNote);

        // 모델에 노트 추가
        model.addAttribute("note", existingNote);

        return "note/note";
    }

    private Note createNote(Long studykey) {
        Note newNote = new Note();
        int studykeyInt = studykey.intValue();

        // Report 정보 가져오기
        Report report = reportService.getReportByStudyKey(studykeyInt);

        if (report != null) {
            newNote.setStudykey(studykey);
//            newNote.setFinalDoctor(report.getFinalDoctor());
            newNote.setFinding(report.getFinding());
            newNote.setComments(report.getComments());
            newNote.setFutureComment(report.getFutureComment());

            newNote.setPatientCode(String.valueOf(report.getPatientCode()));
            newNote.setRegDate(new Timestamp(System.currentTimeMillis()));

//            // 환자 정보 가져오기
//            Patient patient = patientService.getPatientByStudykey(String.valueOf(studykey));
//
//            if (patient != null) {
//                newNote.setPatientCode(patient.getCode());
//            } else {
//                // 환자 정보가 없을 경우 예외 처리 또는 기본값 설정
//                throw new IllegalArgumentException("Patient not found for studykey: " + studykey);
//            }
        } else {
            // Report 정보가 없을 경우 예외 처리 또는 기본값 설정
            throw new IllegalArgumentException("Report not found for studykey: " + studykey);
        }

        return newNote;
    }

    private Note updateNote(Note existingNote, Long studykey) {
        int studykeyInt = studykey.intValue();
        existingNote.setFinding(reportService.getReportByStudyKey(studykeyInt).getFinding());
        existingNote.setComments(reportService.getReportByStudyKey(studykeyInt).getComments());
        existingNote.setFutureComment(reportService.getReportByStudyKey(studykeyInt).getFutureComment());

        return existingNote;
    }

//    private Note createNote(Long studykey) {
//        int studykeyInt = studykey.intValue();
//        String finalDoctor = reportService.getReportByStudyKey(studykeyInt).getFinalDoctor();
//        String findings = reportService.getReportByStudyKey(studykeyInt).getFinding();
//        String comments = reportService.getReportByStudyKey(studykeyInt).getComments();
//        String futureComment = reportService.getReportByStudyKey(studykeyInt).getFutureComment();
//        Patient patient = patientService.getPatientByStudykey(String.valueOf(studykey));
//
//        Note newNote = new Note();
//        newNote.setStudykey(studykey);
//        newNote.setFinalDoctor(finalDoctor);
//        newNote.setFinding(findings);
//        newNote.setComments(comments);
//        newNote.setFutureComment(futureComment);
//        newNote.setPatientCode(patient.getCode());
//
//        return newNote;
//    }




//    @ResponseBody
//    @PostMapping("/{studykey}")
//    public String createOrUpdateNote(@PathVariable Long studykey, @RequestBody Note note, Model model) {
//        Note existingNote = noteService.getNoteByStudykey(studykey);
//
//        if (existingNote == null) {
//            // 노트가 존재하지 않을 경우, 새로운 노트 객체 생성
//            int studykeyInt = studykey.intValue();
//            String finalDoctor = reportService.getReportByStudyKey(studykeyInt).getFinalDoctor();
//            String findings = reportService.getReportByStudyKey(studykeyInt).getFinding();
//            String comments = reportService.getReportByStudyKey(studykeyInt).getComments();
//            String futureComment = reportService.getReportByStudyKey(studykeyInt).getFutureComment();
//            Patient patient = patientService.getPatientByStudykey(String.valueOf(studykey));
//
//            existingNote = new Note();
//            existingNote.setStudykey(studykey);
//            existingNote.setFinalDoctor(finalDoctor);
//            existingNote.setFinding(findings);
//            existingNote.setComments(comments);
//            existingNote.setFutureComment(futureComment);
//            existingNote.setPatientCode(patient.getCode());
//        } else {
//            // 기존 노트가 존재할 경우, 강제로 필드 업데이트
//            int studykeyInt = studykey.intValue();
//            existingNote.setFinding(reportService.getReportByStudyKey(studykeyInt).getFinding());
//            existingNote.setComments(reportService.getReportByStudyKey(studykeyInt).getComments());
//            existingNote.setFutureComment(reportService.getReportByStudyKey(studykeyInt).getFutureComment());
//        }
//
//        // 수정할 내용 업데이트
//        existingNote.setDisease(note.getDisease());
//        existingNote.setTreatmentPeriod(note.getTreatmentPeriod());
//        existingNote.setUsage(note.getUsage());
//
//        existingNote.setModDate(new Timestamp(new Date().getTime()));
//
//        // 노트 저장
//        noteService.saveNote(existingNote);
//
//        // 모델에 노트 추가
//        model.addAttribute("note", existingNote);
//
//        // note/note.jsp로 이동
//        return "note/note";
//    }

    @ResponseBody
    @GetMapping("/{studykey}")
    public ModelAndView createOrUpdateNote(@PathVariable Long studykey) {
        ModelAndView modelAndView = new ModelAndView("note/note");
        Note existingNote = noteService.getNoteByStudykey(studykey);
        modelAndView.addObject("note", existingNote);

        return modelAndView;
    }

//    @ResponseBody
//    @PostMapping("/{studykey}")
//    public String getNoteDetails(@PathVariable Long studykey, Model model) {
//        Note note = noteService.getNoteByStudykey(studykey);
//        if (note == null) {
//            // note가 없을 경우, report 및 patient 정보 가져오기
//            int studykeyInt = studykey.intValue();
//            String studykeyString = String.valueOf(studykey);
//            String finalDoctor = reportService.getReportByStudyKey(studykeyInt).getFinalDoctor();
//            String comments = reportService.getReportByStudyKey(studykeyInt).getComments();
//            String futureComment = reportService.getReportByStudyKey(studykeyInt).getFutureComment();
//            Patient patient = patientService.getPatientByStudykey(studykeyString);
//
//            // 새로운 Note 객체 생성
//            note = new Note();
//            note.setStudykey(studykey);
//            note.setFinalDoctor(finalDoctor);
//            note.setComments(comments);
//            note.setFutureComment(futureComment);
//            note.setPatientCode(patient.getCode());
//        }
//
//        model.addAttribute("note", note);
//        return "note/note";
//    }



//    public ModelAndView createOrUpdateNote(@PathVariable Long studykey, Note note) {
//        ModelAndView modelAndView = new ModelAndView("note");
//
//        // 소견서 존재하는지 확인
//        Note existingNote = noteService.getNoteByStudykey(studykey);
//
//        // 소견서가 존재하면 업데이트
//        if (existingNote != null) {
//            note.setStudykey(studykey);
//            Note updatedNote = noteService.updateNote(note);
//            modelAndView.addObject("note", updatedNote);
//        } else {
//            // 새로 생성된 소견서인 경우
//            try {
//                note.setStudykey(studykey);
//                Note createdNote = noteService.createNote(note);
//                modelAndView.addObject("note", createdNote);
//
//            } catch (Exception e) {
//                // 소견서 생성 실패한 경우
//                modelAndView.setViewName("main");
//                modelAndView.addObject("error", "Failed to create note.");
//            }
//        }
//
//        return modelAndView;
//    }

//    @ResponseBody
//    @PostMapping("/{studykey}")
//    public ResponseEntity<Note> createNote(@PathVariable Long studykey, @RequestBody Note note) {
//        // 소견서 존재하는지 확인
//        Note existingNote = noteService.getNoteByStudykey(studykey);
//
//        // 소견서가 존재하면 업데이트
//        if (existingNote != null) {
//            note.setStudykey(studykey);
//            Note updatedNote = noteService.updateNote(note);
//            return new ResponseEntity<>(updatedNote, HttpStatus.OK);
//        } else {
//            // 새로 생성된 소견서인 경우
//            try {
//                note.setStudykey(studykey);
//                Note createdNote = noteService.createNote(note);
//                return new ResponseEntity<>(createdNote, HttpStatus.OK);
//
//                // 소견서 생성 실패한 경우
//            } catch (Exception e) {
//                return ResponseEntity.notFound().build();
//            }
//        }
//    }

    @ResponseBody
    @GetMapping("/download/{studykey}")
    public ResponseEntity<InputStreamResource> getNote(@PathVariable Long studykey) {
        Note note = noteService.getNoteByStudykey(studykey);
        if (note == null) {
            return ResponseEntity.notFound().build();
        }

        // Generate PDF
        ByteArrayOutputStream baos = generatePdf(note);

        // Prepare response for PDF download
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=note_" + studykey + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(baos.size())
                .body(resource);
    }

    private ByteArrayOutputStream generatePdf(Note note) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Add content to the PDF
            document.add(new Paragraph("Final Doctor: " + note.getFinalDoctor()));
            document.add(new Paragraph("Patient Code: " + note.getPatientCode()));
            document.add(new Paragraph("Disease: " + note.getDisease()));
            document.add(new Paragraph("Treatment Period: " + note.getTreatmentPeriod()));
            document.add(new Paragraph("Finding: " + note.getFinding()));
            document.add(new Paragraph("Comments: " + note.getComments()));
            document.add(new Paragraph("Future Comment: " + note.getFutureComment()));
            document.add(new Paragraph("Usage: " + note.getUsage()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos;
    }
}
