package com.medison.mysql.note.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.medison.mysql.note.domain.Note;
import com.medison.mysql.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    @ResponseBody
    @PostMapping("/{studykey}")
    public ResponseEntity<Note> createNote(@PathVariable Long studykey, @RequestBody Note note) {
        // 소견서 존재하는지 확인
        Note existingNote = noteService.getNoteByStudykey(studykey);

        // 소견서가 존재하면 업데이트
        if (existingNote != null) {
            note.setStudykey(studykey);
            Note updatedNote = noteService.updateNote(note);
            return new ResponseEntity<>(updatedNote, HttpStatus.OK);
        } else {
            // 새로 생성된 소견서인 경우
            try {
                note.setStudykey(studykey);
                Note createdNote = noteService.createNote(note);
                return new ResponseEntity<>(createdNote, HttpStatus.OK);

                // 소견서 생성 실패한 경우
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
//            try {
//                Note newNote = new Note();
//                note.setStudykey(studykey);
//                // 의사 ID를 설정
//                note.setFinalDoctor(note.getFinalDoctor());
//                note.setPatientCode(note.getPatientCode());
//                note.setDisease(note.getDisease());
//                note.setTreatmentPeriod(note.getTreatmentPeriod());
//                note.setFinding(note.getFinding());
//                note.setComments(note.getComments());
//                note.setFutureComment(note.getFutureComment());
//                note.setUsage(note.getUsage());
//
//                Note createdNote = noteService.createNote(note);
//                return new ResponseEntity<>(createdNote, HttpStatus.OK);
//            } catch (Exception e) {
//                return ResponseEntity.notFound().build();
//            }
        }
    }

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
