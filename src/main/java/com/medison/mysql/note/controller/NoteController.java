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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class NoteController {

    private final NoteService noteService;

    @ResponseBody
    @PostMapping("note/create")
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note createdNote = noteService.createNote(note);
        return ResponseEntity.ok(createdNote);
    }

    @ResponseBody
    @GetMapping("note/download/{studykey}")
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
