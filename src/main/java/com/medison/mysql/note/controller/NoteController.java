package com.medison.mysql.note.controller;


import com.medison.mysql.note.service.NoteService;
import com.medison.mysql.report.domain.Report;
import com.medison.mysql.report.service.ReportService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/note")
public class NoteController {


    private final NoteService noteService;
    private final ReportService reportService;


    @GetMapping("/{studykey}")
    public ModelAndView redirectToNote(@PathVariable int studykey) {

        Report report = reportService.getReportByStudyKey(studykey);
        System.out.println("status : "+report.getStatus());
        System.out.println("study : " + report.getStudykey());
        if (report.getStatus() != 6) {
            return new ModelAndView("redirect:/main");
        }
        ModelAndView mv = new ModelAndView("note/note");
        Map<String, String> demoNote = noteService.createDemoNote(studykey);
        mv.addObject("demoNote", demoNote);
        return mv;
    }
}
