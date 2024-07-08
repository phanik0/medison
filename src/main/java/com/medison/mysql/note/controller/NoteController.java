package com.medison.mysql.note.controller;

import com.medison.mysql.note.domain.Note;
import com.medison.mysql.note.domain.NoteRepository;
import com.medison.mysql.note.service.NoteService;
import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final ReportService reportService;
    private final NoteRepository noteRepository;

    @GetMapping("/{studykey}")
    public ModelAndView redirectToNote(@PathVariable int studykey) {

        Report report = reportService.getReportByStudyKey(studykey);
        if (report.getStatus() != 6) {
            return new ModelAndView("redirect:/main");
        }

        Note note = noteService.getNoteByStudyKey(studykey);

        ModelAndView mv = new ModelAndView("note/note");
        Map<String, String> demoNote = noteService.createDemoNote(studykey);
        mv.addObject("demoNote", demoNote);
        return mv;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveNote(@RequestBody Note note, HttpSession session) {
        String userId = (String) session.getAttribute("userId"); // 세션에서 사용자 ID를 가져옴
        if (userId == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        Report report = reportService.getReportByStudyKey(note.getStudykey());
        if (!userId.equals(report.getFinalDoctor())) {
            return ResponseEntity.status(403).body("최종 판독 의사만 노트를 저장할 수 있습니다.");
        }

        noteService.saveNote(note);
        return ResponseEntity.ok("노트가 저장되었습니다.");
    }

    @RequestMapping("/printNote/{studykey}")
    public ModelAndView printNotePage(@PathVariable int studykey) {

        if  (noteService.getNoteByStudykey(studykey) == null) {
            return new ModelAndView("redirect:/main");
        }

        Map<String, String> demoNote = noteService.createDemoNote(studykey);
        ModelAndView mv = new ModelAndView("note/printNote"); // JSP 파일명이 printNote.jsp 라면 여기서는 "note/printNote"로
        mv.addObject("demoNote", demoNote);
        return mv;
    }
}
