package com.medison.mysql.readLog.controller;

import com.medison.mysql.readLog.domain.ReadLogResponseDto;
import com.medison.mysql.readLog.service.ReadLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReadLogController {
    private final ReadLogService readLogService;

    @PostMapping("/log")
    @ResponseBody
    public boolean saveLog(@RequestParam String userId, @RequestParam int studykey) {
        return readLogService.save(userId, studykey);
    }

    @PostMapping("/log/{studykey}/{userId}")
    @ResponseBody
    public boolean existLogByStudykeyAndUserId(@PathVariable int studykey, @PathVariable String userId) {
        return readLogService.exist(userId,studykey);
    }

    @GetMapping("/log")
    public ModelAndView getAllLog() {
        ModelAndView mv = new ModelAndView("user/adminLogCheckPage");
        List<ReadLogResponseDto> response = readLogService.findAll();
        mv.addObject("readLog", response);
        return mv;
    }
}
