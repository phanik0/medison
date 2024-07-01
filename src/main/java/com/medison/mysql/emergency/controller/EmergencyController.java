package com.medison.mysql.emergency.controller;

import com.medison.mysql.emergency.domain.Emergency;
import com.medison.mysql.emergency.domain.EmergencyRepository;
import com.medison.mysql.emergency.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class EmergencyController {
    private final EmergencyService emergencyService;
    private final EmergencyRepository emergencyRepository;

    @GetMapping("/emergency")
    @ResponseBody
    public ModelAndView getEmergency() {
        ModelAndView mv = new ModelAndView("emergency");
        List<Map<String,Object>> emergencies = emergencyService.getEmergencyStudies();
        System.out.println(emergencies.size());
        mv.addObject("emergencies", emergencies);
        return mv;
    }
}
