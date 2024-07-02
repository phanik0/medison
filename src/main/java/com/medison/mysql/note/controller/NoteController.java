package com.medison.mysql.note.controller;


import com.medison.mysql.note.service.NoteService;
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

    @GetMapping("/{studykey}")
    public ModelAndView redirectToNote(@PathVariable int studykey) {
        ModelAndView mv = new ModelAndView("note/note");
        Map<String,String> demoNote = noteService.createDemoNote(studykey);
        mv.addObject("demoNote", demoNote);
        return mv;
    }
}
