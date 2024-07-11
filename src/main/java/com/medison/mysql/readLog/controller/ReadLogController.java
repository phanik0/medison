package com.medison.mysql.readLog.controller;

import com.medison.mysql.readLog.service.ReadLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
