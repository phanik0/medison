package com.medison.mysql.readLog.controller;

import com.medison.mysql.readLog.service.ReadLogService;
import com.medison.mysql.user.domain.User;
import com.medison.mysql.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class ReadLogController {
    private final ReadLogService readLogService;
    private final UserService userService;

    @PostMapping("/log")
    @ResponseBody
    public boolean saveLog(@RequestParam String userId, @RequestParam int studykey) {
        return readLogService.save(userId, studykey);
    }

}
